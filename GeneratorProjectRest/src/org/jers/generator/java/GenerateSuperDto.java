package org.jers.generator.java;

import org.jers.generator.ConvertText;
import org.jers.generator.enums.ClassDTO;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.jers.generator.Config.*;
import static org.jers.generator.GenerateFile.writeFile;

public class GenerateSuperDto {
    private Class<?> clas;
    private String className;
    private String packageName;
    private List<String> imports = new ArrayList<>();
    private List<String> importStatics = new ArrayList<>();
    private List<String> attributes = new ArrayList<>();
    private List<Class<?>> classes;
    private String requiredMode = "";
    private String schemaField = "";
    private String validation ="";
    private ClassDTO dto;
    private List<String> gettersAndSetters = new ArrayList<>();

    public GenerateSuperDto(Class<?> clas, List<Class<?>> classes) {
        this.clas = clas;
        this.classes = classes;
        isSuperClass();
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

    private void gettersAndSetters(String fieldType, String fieldName) {
        String text = "\tpublic " + fieldType + " get" + ConvertText.upperFirst(fieldName) + "() {\n" +
                "\t\treturn " + fieldName + ";\n" +
                "\t}\n" +
                "\n" +
                "\tpublic void set" + ConvertText.upperFirst(fieldName) + "(" + fieldType + " " + fieldName + ") {\n" +
                "\t\tthis." + fieldName + " = " + fieldName + ";\n" +
                "\t}\n";
        //System.out.println(text);
        gettersAndSetters.add(text);
    }

    private void isSuperClass() {
        Annotation[] annotations = clas.getAnnotations();
        String annotation = annotations[0].annotationType().getSimpleName();
        if (annotation.equals("MappedSuperclass")) {
            className = clas.getSimpleName() + "InputDTO";
            packageName = "model.dto";
            writeFile(ROUTE + "\\" + packageName.replace(".", "\\") + "\\" + className + ".java", generateInputDTO());
            className = clas.getSimpleName() + "OutputDTO";
            imports = new ArrayList<>();
            importStatics = new ArrayList<>();
            attributes = new ArrayList<>();
            writeFile(ROUTE + "\\" + packageName.replace(".", "\\") + "\\" + className + ".java", generateSuperOutputDTO());
        } else if (annotation.equals("Entity")) {
            packageName = "model.dto." + ConvertText.lowwerCase(clas.getSimpleName());
            className = clas.getSimpleName() + "CreateDTO";
            imports = new ArrayList<>();
            importStatics = new ArrayList<>();
            attributes = new ArrayList<>();
            writeFile(ROUTE + "\\" + packageName.replace(".", "\\") + "\\" + className + ".java", generateCreateDTO());
            className = clas.getSimpleName() + "UpdateDTO";
            imports = new ArrayList<>();
            importStatics = new ArrayList<>();
            attributes = new ArrayList<>();
            writeFile(ROUTE + "\\" + packageName.replace(".", "\\") + "\\" + className + ".java", generateUpdateDTO());
            className = clas.getSimpleName() + "OutputDTO";
            imports = new ArrayList<>();
            importStatics = new ArrayList<>();
            attributes = new ArrayList<>();
            writeFile(ROUTE + "\\" + packageName.replace(".", "\\") + "\\" + className + ".java", generateOutputDTO());
        }
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

    private void setValidationSwagger(Field field, boolean isId) {
        validation = "";
        if (isId) {
            //if (!dto.equals(ClassDTO.UPDATE) && isEntity(field.getType().getSimpleName())) {
                if (!dto.equals(ClassDTO.OUTPUT)) {
                    validation = validation + "\t@Min(value=1, message=ID_VALUE_MINIMUM)\n";
                    addImports(false, "jakarta.validation.constraints.Min;\n");
                    addImports(true, PROJECT_PACKAGE + ".utils.Constants.ID_VALUE_MINIMUM;\n");
                }
                if (SWAGGER) {
                    schemaField = "\t@Schema(defaultValue = \"1\", minimum = \"1\", requiredMode=REQUIRED, description = \"" + ConvertText.upperText(clas.getSimpleName(), "-", true) + " identification on registries\")\n";
                    addImports(true, "io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;\n");
                }
            //}
        } else {
            Annotation[] annotations = field.getAnnotations();
            String column = annotations[0].toString();
            column = column.replace("@jakarta.persistence.Column(", "");
            column = column.replace(")", "");
            String[] parameters = column.split(", ");
            boolean result = true;
            String fieldType = setFieldType(field.getType().getSimpleName());
            for (String s : parameters) {
                if (s.equals("nullable=false")) {
                    result = false;
                }
                if (s.equals("insertable=false")) {
                    result = true;
                }
            }
            String variable = ConvertText.upperCase(field.getName());
            schemaField = "\t@Schema(defaultValue=\"" + ConvertText.getValues(fieldType, false) + "\", ";
            if (result) {
                requiredMode = "NOT_REQUIRED";
            } else  {
                requiredMode = "REQUIRED";
                schemaField = schemaField + "nullable=" + result + ", ";
                validation = validation + "\t@NotNull(message=" + variable + "_NOT_NULL)\n";
                if (!dto.equals(ClassDTO.OUTPUT)) {
                    addImports(false, "jakarta.validation.constraints.NotNull;\n");
                    addImports(true, PROJECT_PACKAGE + ".utils.Constants." + variable + "_NOT_NULL;\n");
                    GenerateConstants.setField(clas.getSimpleName(), variable + "_NOT_NULL", "\"El " + field.getName() + " del " + ConvertText.upperText(clas.getSimpleName(), "-", true) + " no puede ser nulo.\"");
                }
                if (fieldType.equals("String")) {

                    validation = validation + "\t@NotBlank(message=" + variable + "_NOT_BLANK)\n" +
                            "\t@Size(min=1, max=" + variable + "_LENGTH, message=" + variable + "_SIZE)";
                    schemaField = schemaField + "minLength=1, maxLength=" + variable + "_LENGTH, ";
                    if (!dto.equals(ClassDTO.OUTPUT)) {
                        addImports(false, "jakarta.validation.constraints.NotBlank;\n");
                        addImports(false, "jakarta.validation.constraints.Size;\n");
                        addImports(true, PROJECT_PACKAGE + ".utils.Constants." + variable + "_NOT_BLANK;\n");
                        GenerateConstants.setField(clas.getSimpleName(), variable + "_NOT_BLANK", "\"El " + field.getName() + " del " + ConvertText.upperText(clas.getSimpleName(), "-", true) + " no puede ser vacío.\"");
                        addImports(true, PROJECT_PACKAGE + ".utils.Constants." + variable + "_SIZE;\n");
                        addImports(true, PROJECT_PACKAGE + ".utils.SystemConstants." + variable + "_LENGTH;\n");
                        GenerateConstants.addImports(true, PROJECT_PACKAGE + ".utils.SystemConstants." + variable + "_LENGTH;\n");
                        GenerateConstants.setField(clas.getSimpleName(), variable + "_SIZE", "\"El tamaño del " + field.getName() + " del " + ConvertText.upperText(clas.getSimpleName(), "-", true) + " es mínimo de 1 y máximo de \" + " + variable + "_LENGTH + \".\"");
                    }
                } else if (fieldType.equals("Integer")) {
                    validation = validation + "\t@Min(value=1, message=ID_VALUE_MINIMUM)\n";
                    if (!dto.equals(ClassDTO.OUTPUT)) {
                        addImports(false, "jakarta.validation.constraints.Min;\n");
                        addImports(true, PROJECT_PACKAGE + ".utils.Constants.ID_VALUE_MINIMUM;\n");
                    }
                }
                validation = validation + "\n";
            }
            if (SWAGGER) {
                addImports(true, "io.swagger.v3.oas.annotations.media.Schema.RequiredMode." + requiredMode + ";\n");
            }
            schemaField = schemaField + "requiredMode=" + requiredMode + ", description=\"\")\n";
        }
        if (dto.equals(ClassDTO.OUTPUT)) {
            validation = "";
        }
    }

    private String setJsonFormat(String fieldType) {
        String text = "";
        if (fieldType.equals("LocalDate")) {
            text = "\t@JsonFormat(pattern = \"dd/MM/yyyy\")\n";
        } else if (fieldType.equals("LocalTime")) {
            text = "\t@JsonFormat(pattern = \"HH:mm:ss\")\n";
        } else if (fieldType.equals("LocalDateTime") || fieldType.equals("Date")) {
            text = "\t@JsonFormat(pattern = \"dd/MM/yyyy HH:mm:ss\")\n";
        }
        if (!text.isEmpty()) {
            addImports(false,"com.fasterxml.jackson.annotation.JsonFormat;\n");
        }
        return  text;
    }

    private void setAttributes() {
        String text = "";
        String fieldName;
        String fieldType;
        Field[] fields = clas.getDeclaredFields();
        Annotation[] annotations;
        String column;
        for(Field field : fields) {
            text = "";
            if (isEntity(field.getType().getSimpleName())) {
                if (!dto.equals(ClassDTO.UPDATE)) {
                    fieldName = field.getName();
                    fieldType = setFieldType(field.getType().getSimpleName());
                    setImport(fieldType);
                    setValidationSwagger(field, true);
                    text = validation;
                    if (SWAGGER) {
                        text = text + schemaField;
                    }
                    text = text + "\tprivate " + fieldType + " " + fieldName + ";\n";
                    if (SWAGGER) {
                        text = text + "\n";
                    }
                    attributes.add(text);
                    if (!LOMBOK) {
                        gettersAndSetters(fieldType, fieldName);
                    }
                }
            } else {
                annotations = field.getAnnotations();
                column = annotations[0].toString();
                if (dto.equals(ClassDTO.INPUT) || (dto.equals(ClassDTO.CREATE) && !column.contains("Id"))) {
                    fieldName = field.getName();
                    fieldType = setFieldType(field.getType().getSimpleName());
                    setImport(fieldType);
                    setValidationSwagger(field, false);
                    text = setJsonFormat(fieldType) +
                            validation;
                    if (SWAGGER) {
                        text = text + schemaField;
                    }
                    text = text + "\tprivate " + fieldType + " " + fieldName + ";\n";
                    if (SWAGGER) {
                        text = text + "\n";
                    }
                    attributes.add(text);
                    if (!LOMBOK) {
                        gettersAndSetters(fieldType, fieldName);
                    }
                } else if (dto.equals(ClassDTO.OUTPUT)) {
                    fieldName = field.getName();
                    fieldType = setFieldType(field.getType().getSimpleName());
                    setImport(fieldType);
                    if (column.contains("Id")) {
                        setValidationSwagger(field, true);
                    } else {
                        setValidationSwagger(field, false);
                    }
                    text = setJsonFormat(fieldType) +
                            validation;
                    if (SWAGGER) {
                        text = text + schemaField;
                    }
                    text = text + "\tprivate " + fieldType + " " + fieldName + ";\n";
                    if (SWAGGER) {
                        text = text + "\n";
                    }
                    attributes.add(text);
                    if (!LOMBOK) {
                        gettersAndSetters(fieldType, fieldName);
                    }
                } else if (dto.equals(ClassDTO.UPDATE) && column.contains("Id")) {
                    fieldName = field.getName();
                    fieldType = setFieldType(field.getType().getSimpleName());
                    setImport(fieldType);
                    setValidationSwagger(field, true);
                    text = setJsonFormat(fieldType) +
                            validation;
                    if (SWAGGER) {
                        text = text + schemaField;
                    }
                    text = text + "\tprivate " + fieldType + " " + fieldName + ";\n";
                    if (SWAGGER) {
                        text = text + "\n";
                    }
                    attributes.add(text);
                    if (!LOMBOK) {
                        gettersAndSetters(fieldType, fieldName);
                    }
                }
            }

        }
    }

    private String generateInputDTO() {
        dto = ClassDTO.INPUT;
        setAttributes();
        if (LOMBOK) {
            addImports(false, "lombok.Getter;\n");
            addImports(false, "lombok.Setter;\n");
        }
        if (SWAGGER) {
            addImports(true, "io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;\n");
        }
        String text = "";
        if (LOMBOK) {
            text = text + "@Getter\n" +
                    "@Setter\n";
        }
        if (SWAGGER) {
            addImports(false, "io.swagger.v3.oas.annotations.media.Schema;\n");
            text = text + "@Schema(hidden = true)\n";
        }
        text = text + "public abstract class " + className + " {\n" +
                printList(attributes) +
                printList(gettersAndSetters) +
                "}";
        imports.sort(String::compareTo);
        importStatics.sort(String::compareTo);
        text = "package " + PROJECT_PACKAGE + "." + packageName + ";\n\n" +
                printList(imports) +
                printList(importStatics) + text;
        return text;
    }

    private String generateSuperOutputDTO() {
        dto = ClassDTO.INPUT;
        if (LOMBOK) {
            addImports(false, "lombok.Getter;\n");
            addImports(false, "lombok.Setter;\n");
        }
        if (SWAGGER) {
            addImports(false, "io.swagger.v3.oas.annotations.media.Schema;\n");
            addImports(true, "io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;\n");
        }
        imports.sort(String::compareTo);
        importStatics.sort(String::compareTo);
        String text = "package " + PROJECT_PACKAGE + "." + packageName + ";\n\n" +
                printList(imports) +
                printList(importStatics);
        if (LOMBOK) {
            text = text + "@Getter\n" +
                    "@Setter\n";
        }
        if (SWAGGER) {
            text = text + "@Schema(hidden = true)\n";
        }
        text = text + "public abstract class " + className + " extends " + clas.getSimpleName() + "InputDTO {\n\n}";
        return text;
    }

    private String generateCreateDTO() {
        dto = ClassDTO.CREATE;
        gettersAndSetters = new ArrayList<>();
        setAttributes();
        String text = "";
        if (LOMBOK) {
            addImports(false, "lombok.Getter;\n");
            addImports(false, "lombok.Setter;\n");
            text = text + "@Getter\n" +
                    "@Setter\n";
        }
        if (SWAGGER) {
            addImports(false, "io.swagger.v3.oas.annotations.media.Schema;\n");
            text = text + "@Schema(name = \"" + ConvertText.upperText(clas.getSimpleName(),"-", true) + " create\", description = \"" + ConvertText.upperText(clas.getSimpleName(),"-", true) + " entity with all new properties to save\")\n";
        }
        String superClass = clas.getSuperclass().getSimpleName();
        if(!superClass.equals("Object")) {
            addImports(false, PROJECT_PACKAGE + ".model.dto." + superClass + "InputDTO;\n");
            superClass = " extends " + superClass + "InputDTO";
        } else {
            superClass = "";
        }
        text = text + "public class " + className + superClass + " {\n" +
                printList(attributes) +
                printList(gettersAndSetters) +
                "}";
        imports.sort(String::compareTo);
        importStatics.sort(String::compareTo);
        text = "package " + PROJECT_PACKAGE + "." + packageName + ";\n\n" +
                printList(imports) +
                printList(importStatics) + text;
        return text;
    }

    private String generateUpdateDTO() {
        dto = ClassDTO.UPDATE;
        gettersAndSetters = new ArrayList<>();
        setAttributes();
        String text = "";
        if (LOMBOK) {
            addImports(false, "lombok.Getter;\n");
            addImports(false, "lombok.Setter;\n");
            text = text + "@Getter\n" +
                    "@Setter\n";
        }
        if (SWAGGER) {
            addImports(false, "io.swagger.v3.oas.annotations.media.Schema;\n");
            text = text + "@Schema(name = \"" + ConvertText.upperText(clas.getSimpleName(),"-", true) + " update\", description = \"" + ConvertText.upperText(clas.getSimpleName(),"-", true) + " entity with all properties to update\")\n";
        }
        String superClass = " extends " + clas.getSimpleName() + "CreateDTO";
        text = text + "public class " + className + superClass + " {\n" +
                printList(attributes) +
                printList(gettersAndSetters) +
                "}";
        imports.sort(String::compareTo);
        importStatics.sort(String::compareTo);
        text = "package " + PROJECT_PACKAGE + "." + packageName + ";\n\n" +
                printList(imports) +
                printList(importStatics) + text;
        return text;
    }

    private String generateOutputDTO() {
        dto = ClassDTO.OUTPUT;
        gettersAndSetters = new ArrayList<>();
        setAttributes();
        String text = "";
        if (LOMBOK) {
            addImports(false, "lombok.Getter;\n");
            addImports(false, "lombok.Setter;\n");
            text = text + "@Getter\n" +
                    "@Setter\n";
        }
        if (SWAGGER) {
            addImports(false, "io.swagger.v3.oas.annotations.media.Schema;\n");
            text = text + "@Schema(name = \"" + ConvertText.upperText(clas.getSimpleName(),"-", true) + " output\", description = \"" + ConvertText.upperText(clas.getSimpleName(),"-", true) + " entity with all properties\")\n";
        }
        String superClass = clas.getSuperclass().getSimpleName();
        if(!superClass.equals("Object")) {
            addImports(false, PROJECT_PACKAGE + ".model.dto." + superClass + "OutputDTO;\n");
            superClass = " extends " + superClass + "OutputDTO";
        } else {
            superClass = "";
        }
        text = text + "public class " + className + superClass + " {\n" +
                printList(attributes) +
                printList(gettersAndSetters) +
                "}";
        imports.sort(String::compareTo);
        importStatics.sort(String::compareTo);
        text = "package " + PROJECT_PACKAGE + "." + packageName + ";\n\n" +
                printList(imports) +
                printList(importStatics) + text;
        return text;
    }
}