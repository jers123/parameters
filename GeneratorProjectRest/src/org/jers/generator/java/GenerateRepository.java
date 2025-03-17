package org.jers.generator.java;

import org.jers.generator.ConvertText;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.jers.generator.Config.*;
import static org.jers.generator.GenerateFile.writeFile;

public class GenerateRepository {
    private Class<?> clas;
    private String className = "";
    private String packageName = "";
    private List<String> imports = new ArrayList<>();
    private List<String> importStatics = new ArrayList<>();
    private List<String> methods = new ArrayList<>();
    private List<Class<?>> classes;
    private String fieldNameFirst = "";

    public GenerateRepository(Class<?> clas, List<Class<?>> classes) {
        this.clas = clas;
        this.classes = classes;
        isEntityClass();
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

    private String setFieldType(String fieldType) {
        if (isEntity(fieldType)) {
            fieldType = "Integer";
        }
        return fieldType;
    }

    public String getInitials(String word) {
        String initials = "";
        char letter;
        for (int i = 0; i < word.length(); i++) {
            letter = word.charAt(i);
            if (Character.isUpperCase(letter)) {
                initials = initials + Character.toLowerCase(letter);
            }
        }
        return initials;
    }

    private void validationAnnotation(Field field, String annotation) {
        if (annotation.contains("unique=true")) {
            addImports(true, PROJECT_PACKAGE + ".utils.SystemConstants.ID;\n");
            addImports(false, "org.springframework.data.repository.query.Param;\n");
            String fieldName = field.getName();
            addImports(true, PROJECT_PACKAGE + ".utils.SystemConstants." + ConvertText.upperCase(fieldName) + "_QUERY;\n");
            String suffix = fieldName;
            if (fieldName.toLowerCase().contains(clas.getSimpleName().toLowerCase())) {
                suffix = fieldName.replaceFirst("(?i)" + clas.getSimpleName(), "");
                if (!suffix.isEmpty()) {
                    suffix = suffix.substring(0, 1).toUpperCase() + suffix.substring(1);
                }
            }
            if (fieldNameFirst.isEmpty()) {
                fieldNameFirst = fieldName;
            }
            addImports(true, PROJECT_PACKAGE + ".utils.SystemConstants." + ConvertText.upperCase(suffix) + ";\n");
            String text = "\t@Query(value = " + ConvertText.upperCase(fieldName) + "_QUERY)\n" +
                    "\t" + field.getType().getSimpleName() + " searchBy" + ConvertText.upperFirst(suffix) + "(@Param(ID) Integer id, @Param(" + ConvertText.upperCase(suffix) + ") " + field.getType().getSimpleName() + " " + suffix + ");";
            methods.add(text);
            String var = getInitials(clas.getSimpleName());
            String value = "\"SELECT " + var + "." + fieldName +" FROM " + clas.getSimpleName() + " " + var + " WHERE ";
            if (field.getType().getSimpleName().equals("String")) {
                value = value + "LOWER(";
            }
            value = value + var + "." + fieldName;
            if (field.getType().getSimpleName().equals("String")) {
                value = value + ") = LOWER(";
            } else {
                value = value + " = ";
            }
            value = value + ":\" + " + ConvertText.upperCase(suffix) + " + \"";
            if (field.getType().getSimpleName().equals("String")) {
                value = value + ")";
            }
            value = value + " AND " + var + ".id" + clas.getSimpleName() + " != :\" + ID";
            GenerateSystemConstants.setTables(clas.getSimpleName(), "String", ConvertText.upperCase(fieldName) + "_QUERY", value);
        }
    }

    private void setMethods(Field[] fields) {
        String fieldType;
        String fieldName;
        Annotation[] annotations;
        for (Field field : fields) {
            if (isEntity(field.getType().getSimpleName())) {
                fieldType = setFieldType(field.getType().getSimpleName());
                fieldName = field.getName();
                String text = "\t@Query(value = " + ConvertText.upperCase(clas.getSimpleName()) + "_" + ConvertText.upperCase(fieldName) + "_QUERY)\n" +
                        "\tList<" + clas.getSimpleName() + "> searchBy" + ConvertText.upperFirst(fieldName) + "(@Param(ID) Integer id);\n";
                methods.add(text);
                String value = "\"SELECT " + getInitials(clas.getSimpleName()) + " FROM " + clas.getSimpleName() + " " + getInitials(clas.getSimpleName()) + " WHERE " + getInitials(clas.getSimpleName()) + "." + fieldName + " = :\" + ID + \" ORDER BY " + getInitials(clas.getSimpleName()) + "." + fieldNameFirst + " ASC\"";
                addImports(true, PROJECT_PACKAGE + ".utils.SystemConstants." + ConvertText.upperCase(clas.getSimpleName()) + "_" + ConvertText.upperCase(fieldName) + "_QUERY;\n");
                GenerateSystemConstants.setTables(clas.getSimpleName(), "String", ConvertText.upperCase(clas.getSimpleName()) + "_" + ConvertText.upperCase(fieldName) + "_QUERY", value);
            } else {
                annotations = field.getAnnotations();
                String column = annotations[0].toString();
                if (column.contains("Column")) {
                    validationAnnotation(field, column);
                }
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

    private void isEntityClass() {
        Annotation[] annotations = clas.getAnnotations();
        String annotation = annotations[0].annotationType().getSimpleName();
        if (annotation.equals("Entity")) {
            className = "I" + clas.getSimpleName() + "Repository";
            packageName = "model.repository";
            writeFile(ROUTE + "\\" + packageName.replace(".", "\\") + "\\" + className + ".java", generateInterface());
        }
    }

    private String generateInterface() {
        addImports(false, "org.springframework.stereotype.Repository;\n");
        addImports(false, "org.springframework.data.jpa.repository.Query;\n");
        addImports(false, "java.util.List;\n");
        addImports(true, PROJECT_PACKAGE + ".utils.SystemConstants." + ConvertText.upperCase(clas.getSimpleName()) + "_ALL_QUERY;\n");
        addImports(false, PROJECT_PACKAGE + ".model.entity." + clas.getSimpleName() + ";\n");
        setMethods(clas.getDeclaredFields());
        String text = "@Repository\n" +
                "public interface " + className + " extends ";
        String superClass = clas.getSuperclass().getSimpleName();
        if(!superClass.equals("Object")) {
            superClass = "I" + superClass + "Repository<" + clas.getSimpleName() + ">";
            setMethods(clas.getSuperclass().getDeclaredFields());
        } else {
            superClass = "JpaRepository<" + clas.getSimpleName() + ", Integer>";
            addImports(false, "org.springframework.data.jpa.repository.JpaRepository;\n");
        }
        text = text + superClass + " {\n" +
                "\t@Query(value = " + ConvertText.upperCase(clas.getSimpleName()) + "_ALL_QUERY)\n" +
                "\tList<" + clas.getSimpleName() + "> searchAll();\n\n" +
                printList(methods) +
                "}";
        String var = getInitials(clas.getSimpleName());
        String value = "\"SELECT " + var + " FROM " + clas.getSimpleName() + " " + var + " ORDER BY " + var + "." + fieldNameFirst + " ASC\"";
        GenerateSystemConstants.setTables(clas.getSimpleName(), "String", ConvertText.upperCase(clas.getSimpleName()) + "_ALL_QUERY", value);
        imports.sort(String::compareTo);
        importStatics.sort(String::compareTo);
        text = "package " + PROJECT_PACKAGE + "." + packageName + ";\n\n" +
                printList(imports) +
                printList(importStatics) + text;
        return text;
    }
}