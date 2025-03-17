package org.jers.generator.java;

import org.jers.generator.ConvertText;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.jers.generator.Config.*;
import static org.jers.generator.GenerateFile.writeFile;

public class GenerateEntity {
    private String className;
    private String packageName = "model.entity";
    private List<String> atributes = new ArrayList<>();
    private List<String> imports = new ArrayList<>();
    private List<String> importStatics = new ArrayList<>();
    private Class<?> clas;
    private List<Class<?>> classes;
    private String defineClass = "";
    private String annotation ="";
    private String TABLE_NAME = "";
    private List<String> uniqueKeys = new ArrayList<>();
    private List<GenerateEntity> entities;
    private String idText;
    private List<String> gettersAndSetters = new ArrayList<>();

    public GenerateEntity(Class<?> clas, List<Class<?>> classes, List<GenerateEntity> entities) {
        this.clas = clas;
        this.classes = classes;
        this.entities = entities;
        TABLE_NAME = ConvertText.upperCase(clas.getSimpleName()) + "_TABLE";
        create();
    }

    public String getClassName() {
        return this.className;
    }

    public List<GenerateEntity> getEntities() {
        return this.entities;
    }

    public String getIdText() {
        return this.idText;
    }

    private String printList(List<String> list) {
        String text = "";
        for (String s : list) {
            text = text + s;
        }
        text = text + "\n\n";
        return text;
    }

    public void setRelation(String entity, String fieldName) {
        addImports(false, "jakarta.persistence.OneToMany;\n");
        addImports(false, "jakarta.persistence.CascadeType;\n");
        addImports(false, "jakarta.persistence.FetchType;\n");
        addImports(false, "java.util.List;\n");
        String text = "\t@OneToMany(cascade = CascadeType.ALL, mappedBy = \"" + fieldName + "\", fetch = FetchType.LAZY)\n" +
                "\tprivate List<" + entity + "> " + entity + "s;\n\n";
        atributes.add(text);
        if (!LOMBOK) {
            gettersAndSetters("List<" + entity + ">", entity + "s");
        }
    }

    private String isClassEntity(String fieldName, String fieldType) {
        String result = "";
        String[] table;
        for (int i = 0; i < entities.size(); i++) {
            if (fieldType.equals(entities.get(i).getClassName())) {
                entities.get(i).setRelation(className, fieldName);
                result = entities.get(i).getIdText();
                table = result.split("TABLE");
                addImports(true, PROJECT_PACKAGE + ".utils.SystemConstants." + table[0] + "TABLE;\n");
                break;
            }
        }
        return result;
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

    private String generateVariable(String fieldName) {
        String result ="";

        if (fieldName.toLowerCase().contains(className.toLowerCase())) {
            String suffix = fieldName.replaceFirst("(?i)" + className, "");
            if (!suffix.isEmpty()) {
                suffix = suffix.substring(0, 1).toUpperCase() + suffix.substring(1);
            }
            suffix = ConvertText.upperCase(suffix);
            GenerateSystemConstants.setTables(className, "String", suffix, "\"" + ConvertText.lowwerCase(suffix) + "\"");
            addImports(true, PROJECT_PACKAGE + ".utils.SystemConstants." + suffix + ";\n");
            result = TABLE_NAME + " + \"_\" + " + suffix;
        } else {
            result = ConvertText.upperCase(fieldName);
            GenerateSystemConstants.setTables(className, "String", result, "\"" + ConvertText.lowwerCase(result) + "\"");
            addImports(true, PROJECT_PACKAGE + ".utils.SystemConstants." + result + ";\n");

        }
        return result;
    }

    private String generateColumn(String column, Field field) {
        String fieldName = field.getName();
        String fieldType = field.getType().getSimpleName();
        column = column.replace("@jakarta.persistence.Column(", "");
        column = column.replace(")", "");
        String[] parameters = column.split(", ");
        String columnName = generateVariable(fieldName);
        String temp;
        String[] length;
        column = "\t@Column(name=" + columnName + ", ";
        for (String s : parameters) {
            if (s.equals("nullable=false")) {
                column = column + s + ", ";
            }
            if (fieldType.equals("String") && s.contains("length")) {
                temp = ConvertText.upperCase(fieldName) + "_LENGTH";
                length = s.split("=");
                GenerateSystemConstants.setTables(className, "int", temp, length[1]);
                addImports(true, PROJECT_PACKAGE + ".utils.SystemConstants." + temp + ";\n");
                column = column + "length=" + temp + ", ";
            }
            if (s.equals("unique=true")) {
                column = column + s + ", ";
                uniqueKeys.add("\t\t\t\t@UniqueConstraint(name = " +  TABLE_NAME + " + \"_\" + " + columnName + " + \"_uk\", columnNames = {" + columnName + "})");
            }
            if (s.equals("updatable=false")) {
                column = column + s + ", ";
            }
        }
        column = column.substring(0, column.length() - 2);
        column = column + ")\n";
        return column;
    }

    private void setImport(String fieldType) {
        if (fieldType.equals("LocalDateTime")) {
            addImports(false, "java.time.LocalDateTime;\n");
        } else if (fieldType.equals("LocalDate")) {
            addImports(false, "java.time.LocalDate;\n");
        } else if (fieldType.equals("LocalTime")) {
            addImports(false, "java.time.LocalTime;\n");
        } else if (fieldType.equals("Date")) {
            addImports(false, "java.util.Date;\n");
        }
    }

    private void gettersAndSetters(String fieldType, String fieldName) {
        String text = "\tpublic " + fieldType + " get" + ConvertText.upperFirst(fieldName) + "() {\n" +
                "\t\treturn " + fieldName + ";\n" +
                "\t}\n" +
                "\n" +
                "\tpublic void set" + ConvertText.upperFirst(fieldName) + "(" + fieldType + " " + fieldName + ") {\n" +
                "\t\tthis." + fieldName + " = " + fieldName + ";\n" +
                "\t}\n\n";
        gettersAndSetters.add(text);
    }

    private void setAttributes() {
        try {
            Field[] fields = clas.getDeclaredFields();
            String fieldName = "";
            String fieldType = "";
            Annotation[] annotations;
            String fieldAnnotation = "";
            for (Field field : fields) {
                fieldAnnotation = "";
                fieldName = field.getName();
                fieldType = field.getType().getSimpleName();
                String isEntity = isClassEntity(fieldName, fieldType);
                if (!isEntity.isEmpty()) {
                    addImports(false, "jakarta.persistence.JoinColumn;\n");
                    addImports(false, "jakarta.persistence.ManyToOne;\n");
                    addImports(false, "jakarta.persistence.FetchType;\n");
                    addImports(false, "jakarta.persistence.ForeignKey;\n");
                    addImports(true, PROJECT_PACKAGE + ".utils.SystemConstants." + ConvertText.upperCase(fieldName) + ";\n");
                    addImports(true, PROJECT_PACKAGE + ".utils.SystemConstants." + ConvertText.upperCase(fieldType) + "_TABLE;\n");
                    GenerateSystemConstants.setTables(className, "String", ConvertText.upperCase(fieldName), "\"" + ConvertText.upperCase(fieldName).toLowerCase() + "\"");
                    fieldAnnotation = "\t@JoinColumn(name = " + ConvertText.upperCase(fieldName) + ", referencedColumnName = " + isEntity + ", nullable = false,\n" +
                            "\t\t\tforeignKey = @ForeignKey(name = " + ConvertText.upperCase(className) + "_TABLE + \"_\" + " + ConvertText.upperCase(fieldType) + "_TABLE + \"_" + ConvertText.upperCase(fieldName).toLowerCase() + "_fk\"))\n" +
                            "\t@ManyToOne(optional = false, fetch = FetchType.LAZY)\n";
                } else {
                    setImport(fieldType);
                    annotations = field.getAnnotations();
                    addImports(false, annotations[0].annotationType().getPackageName() + "." + annotations[0].annotationType().getSimpleName() + ";\n");
                    if (annotations[0].annotationType().getSimpleName().equals("Id")) {
                        addImports(false, "jakarta.persistence.GeneratedValue;\n");
                        addImports(false, "jakarta.persistence.GenerationType;\n");
                        addImports(false, "jakarta.persistence.Column;\n");
                        idText = generateVariable(fieldName);
                        fieldAnnotation = "\t@Id\n" +
                                "\t@GeneratedValue(strategy = GenerationType.IDENTITY)\n" +
                                "\t@Column(name = " + idText + ", nullable = false, updatable = false)\n";
                    } else {
                        fieldAnnotation = fieldAnnotation + generateColumn(annotations[0].toString(), field);
                    }
                }
                fieldAnnotation = fieldAnnotation + "\tprivate " + fieldType + " " + fieldName + ";\n\n";
                atributes.add(fieldAnnotation);
                if (!LOMBOK) {
                    gettersAndSetters(field.getType().getSimpleName(), field.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateTable() {
        GenerateSystemConstants.setSubPaths(setPath(className), ConvertText.lowwerCase(ConvertText.upperCase(className)));
        addImports(false, "jakarta.persistence.Table;\n");
        addImports(true, PROJECT_PACKAGE + ".utils.SystemConstants.DATABASE;\n");
        addImports(true, PROJECT_PACKAGE + ".utils.SystemConstants.SCHEMA;\n");
        GenerateSystemConstants.setTables(className, "String", TABLE_NAME, "\"" + ConvertText.lowwerCase(ConvertText.upperCase(className)) + "\"");
        addImports(true, PROJECT_PACKAGE + ".utils.SystemConstants." + TABLE_NAME + ";\n");
        String defineTable = "@Table(name = " + TABLE_NAME + ", catalog = DATABASE, schema = SCHEMA";
        if (!uniqueKeys.isEmpty()) {
            addImports(false, "jakarta.persistence.UniqueConstraint;\n");
            defineTable = defineTable + ",\n" +
                    "\t\tuniqueConstraints = {\n";
            for (String s : uniqueKeys) {
                defineTable = defineTable + s + ",\n";
            }
            defineTable = defineTable + "\t\t}\n";
        }
        defineTable = defineTable + ")\n";
        return defineTable;
    }

    private void create() {
        className = clas.getSimpleName();
        Annotation[] annotations = clas.getAnnotations();
        String superClass = clas.getSuperclass().getSimpleName();
        if(!superClass.equals("Object")) {
            superClass = " extends " + superClass;
        } else {
            superClass = "";
        }
        annotation = annotations[0].annotationType().getSimpleName();
        addImports(false, annotations[0].annotationType().getPackageName() + "." + annotation + ";\n");
        if (annotation.equals("MappedSuperclass")) {
            addImports(false, "java.io.Serializable;\n");
            defineClass = "public abstract class " + className + superClass + " implements Serializable {\n";
        } else {
            defineClass = "public class " + className + superClass + " {\n";
        }
        setAttributes();
    }

    private String generateEntity() {
        String text = "";
        text = text + "\n@" + annotation + "\n";
        if (annotation.equals("Entity")) {
            text = text + generateTable();
        }
        if(LOMBOK) {
            addImports(false, "lombok.Getter;\n");
            addImports(false, "lombok.Setter;\n");
            text = text + "@Getter\n" +
                    "@Setter\n";
        }
        text = text + defineClass +
                printList(atributes) +
                printList(gettersAndSetters) +
                "}";
        imports.sort(String::compareTo);
        importStatics.sort(String::compareTo);
        text = "package " + PROJECT_PACKAGE + "." + packageName + ";\n\n" +
                printList(imports) +
                printList(importStatics) +
                text;
        return text;
    }

    public void generateFile() {
        writeFile(ROUTE + "\\" + packageName.replace(".", "\\") + "\\" + className + ".java", generateEntity());
    }
}