package org.jers.generator.java;

import org.jers.generator.ConvertText;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.jers.generator.Config.PROJECT_PACKAGE;
import static org.jers.generator.Config.ROUTE;
import static org.jers.generator.GenerateFile.writeFile;

public class GenerateMapper {
    private Class<?> clas;
    private String className = "";
    private String packageName = "";
    private List<String> imports = new ArrayList<>();
    private List<String> importStatics = new ArrayList<>();
    private List<Class<?>> classes;

    public GenerateMapper(Class<?> clas, List<Class<?>> classes) {
        this.clas = clas;
        this.classes = classes;
        isEntity();
    }

    private void addImports(boolean isStatic, String importText) {
        boolean result = false;
        if (isStatic) {
            for (String s : importStatics) {
                if (s.equals("import static " + importText)) {
                    result = true;
                    break;
                }
            }
            if (!result) {
                importStatics.add("import static " + importText);
            }
        } else {
            for (String s : imports) {
                if (s.equals("import " + importText)) {
                    result = true;
                    break;
                }
            }
            if (!result) {
                imports.add("import " + importText);
            }
        }
    }

    private String printList(List<String> list) {
        String text = "";
        for (String s : list) {
            text = text + s;
        }
        text = text + "\n";
        return text;
    }

    private boolean isEntity(String fieldType) {
        boolean result = false;
        for (Class<?> aClass : classes) {
            if (aClass.getSimpleName().equals(fieldType)) {
                result = true;
                break;
            }
        }
        return result;
    }

    private void isEntity() {
        Annotation[] annotations = clas.getAnnotations();
        String annotation = annotations[0].annotationType().getSimpleName();
        if (annotation.equals("Entity")) {
            className = clas.getSimpleName() + "Mapper";
            packageName = "utils.mappers";
            writeFile(ROUTE + "\\" + packageName.replace(".", "\\") + "\\" + className + ".java", generateClass());
        }
    }

    private String analysis(Field[] fields, String methodName, String into, String output) {
        String text = "\t@Override\n" +
                "\tpublic " + clas.getSimpleName();
        if (methodName.equals("create")) {
            text = text + " " + methodName + "(" + clas.getSimpleName() + "CreateDTO " + into + ") {\n" +
                    "\t\t" + clas.getSimpleName() + " " + output + " = new " + clas.getSimpleName() + "();\n";
        } else if (methodName.equals("read")) {
            text = text + "OutputDTO " + methodName + "(" + clas.getSimpleName() + " " + into + ") {\n" +
                    "\t\t" + clas.getSimpleName() + "OutputDTO " + output + " = new " + clas.getSimpleName() + "OutputDTO();\n";
        } else if (methodName.equals("update")) {
            text = text + " " + methodName + "(" + clas.getSimpleName() + "UpdateDTO " + into + ", " + clas.getSimpleName() + " " + output + ") {\n";
        }
        Annotation[] annotations;
        for (Field field : fields) {
            if (isEntity(field.getType().getSimpleName())) {
                if (methodName.equals("read")) {
                    text = text + "\t\t" + output + ".set" + ConvertText.upperFirst(field.getName()) + "(" + into + ".get" + ConvertText.upperFirst(field.getName()) + "().getId" + field.getType().getSimpleName() + "());\n";
                }
            } else {
                annotations = field.getAnnotations();
                String column = annotations[0].toString();
                if (column.contains("Id") && methodName.equals("read")) {
                    text = text + "\t\t" + output + ".set" + ConvertText.upperFirst(field.getName()) + "(" + into + ".get" + ConvertText.upperFirst(field.getName()) + "());\n";
                } else if (!column.contains("Id")) {
                    text = text + "\t\t" + output + ".set" + ConvertText.upperFirst(field.getName()) + "(" + into + ".get" + ConvertText.upperFirst(field.getName()) + "());\n";
                }
            }
        }
        text = text + "\t\treturn " + output + ";\n" +
                "\t}\n\n";
        return text;
    }

    private String generateClass() {
        addImports(false, PROJECT_PACKAGE + ".model.dto." + clas.getSimpleName().toLowerCase() + "." + clas.getSimpleName() + "CreateDTO;\n");
        addImports(false, PROJECT_PACKAGE + ".model.dto." + clas.getSimpleName().toLowerCase() + "." + clas.getSimpleName() + "OutputDTO;\n");
        addImports(false, PROJECT_PACKAGE + ".model.dto." + clas.getSimpleName().toLowerCase() + "." + clas.getSimpleName() + "UpdateDTO;\n");
        addImports(false, PROJECT_PACKAGE + ".model.entity." + clas.getSimpleName() + ";\n");
        addImports(false, "org.springframework.stereotype.Component;\n");
        String text = "@Component\n" +
                "public class " + clas.getSimpleName() + "Mapper implements IMapper<" + clas.getSimpleName() + "CreateDTO, " + clas.getSimpleName() + "UpdateDTO, " + clas.getSimpleName() + "OutputDTO, " + clas.getSimpleName() + "> {\n" +
                "\n";
        Field[] fields = clas.getDeclaredFields();
        Field[] fieldsFinal;
        Class<?> superClass = clas.getSuperclass();
        if(!superClass.getSimpleName().equals("Object")) {
            Field[] fields2 = superClass.getDeclaredFields();
            fieldsFinal = new Field[fields.length + fields2.length];
            System.arraycopy(fields, 0, fieldsFinal, 0, fields.length);
            System.arraycopy(fields2, 0, fieldsFinal, fields.length, fields2.length);
        } else {
            fieldsFinal = fields;
        }
        text = text + analysis(fieldsFinal, "create", "entityDto", "entity");
        text = text + analysis(fieldsFinal, "read", "entity", "entityDto");
        text = text + analysis(fieldsFinal, "update", "entityDto", "entity");
        text = text + "}";
        imports.sort(String::compareTo);
        importStatics.sort(String::compareTo);
        text = "package " + PROJECT_PACKAGE + "." + packageName + ";\n\n" +
                printList(imports) +
                printList(importStatics) +
                text;
        return text;
    }
}