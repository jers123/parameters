package org.jers.generator.java;

import org.jers.generator.ConvertText;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.jers.generator.Config.*;
import static org.jers.generator.GenerateFile.writeFile;

public class GenerateService {
    private Class<?> clas;
    private String className = "";
    private String packageName = "";
    private List<String> imports = new ArrayList<>();
    private List<String> importStatics = new ArrayList<>();
    private List<Class<?>> classes;
    private List<String> repositories = new ArrayList<>();
    private String tab = "";

    public GenerateService(Class<?> clas, List<Class<?>> classes) {
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
            className = clas.getSimpleName() + "Service";
            packageName = "service";
            writeFile(ROUTE + "\\" + packageName.replace(".", "\\") + "\\" + className + ".java", generateClass());
        }
    }

    private void addRepositories(String fieldType) {
        String text = "\t@Autowired\n" +
                "\tprivate I" + fieldType + "Repository " + fieldType + "Repository;\n" +
                "\n";
        boolean result = false;
        for (String s : repositories) {
            if (s.equals(text)) {
                result = true;
                break;
            }
        }
        if (!result) {
            addImports(false, PROJECT_PACKAGE + ".model.repository.I" + fieldType + "Repository;\n");
            repositories.add(text);
        }
    }

    private String process(String tabParam, String id) {
        tab = tabParam;
        boolean isEntity = false;
        boolean isSearch = false;
        String text = "";
        String search = "";
        String conditions = "";
        String conditions2 = "";
        String setEntities = "";
        Field[] fields = clas.getDeclaredFields();
        Annotation[] annotations;
        String column;
        String exists = "";
        for (Field field : fields) {
            if (isEntity(field.getType().getSimpleName())) {
                addImports(false, PROJECT_PACKAGE + ".model.entity." + field.getType().getSimpleName() + ";\n");
                addRepositories(field.getType().getSimpleName());
                search = search + tabParam + field.getType().getSimpleName() + " " + field.getName() + " = " + field.getType().getSimpleName() + "Repository.findById(entityDto.getId" + field.getType().getSimpleName() + "()).orElse(null);\n";
                exists = ConvertText.upperCase(field.getName()) + "_" + ConvertText.upperCase(clas.getSimpleName()) + "_NOT_EXISTS";
                GenerateConstants.setField(clas.getSimpleName(), exists, "\"El " + field.getName() + " no existe en la tabla " + field.getType().getSimpleName() + ".\"");
                addImports(true, PROJECT_PACKAGE + ".utils.Constants." + exists + ";\n");
                setEntities = setEntities + tabParam + "\tentity.set" + ConvertText.upperFirst(field.getName()) + "(" + field.getName() + ");\n";
                conditions = conditions + tabParam + "if (" + field.getName() + " == null) {\n" +
                        tabParam + "\tmessages.add(" + exists + ");\n" +
                        tabParam + "}\n";
                conditions2 = conditions2 + field.getName() + " != null && ";
                isEntity = true;
            } else {
                annotations = field.getAnnotations();
                column = annotations[0].toString();
                if (column.contains("unique=true")) {
                    search = search + tabParam + field.getType().getSimpleName() + " " + field.getName() + " = repository.searchBy" + ConvertText.upperFirst(field.getName()) + "(" + id + ", entityDto.get" + ConvertText.upperFirst(field.getName()) + "());\n";
                    exists = ConvertText.upperCase(field.getName()) + "_EXISTS";
                    GenerateConstants.setField(clas.getSimpleName(), exists, "\"El " + field.getName() + " del " + clas.getSimpleName() + " ya existe.\"");
                    addImports(true, PROJECT_PACKAGE + ".utils.Constants." + exists + ";\n");
                    conditions = conditions + tabParam + "if (" + field.getName() + " != null) {\n" +
                            tabParam + "\tmessages.add(" + exists + ");\n" +
                            tabParam + "}\n";
                    conditions2 = conditions2 + field.getName() + " == null && ";
                    isSearch = true;
                }
            }
        }
        if (isSearch || isEntity) {
            conditions2 = conditions2.substring(0, conditions2.length() - 4);
            conditions2 = tabParam + "if (" + conditions2 + ") {\n";
            tab = tab + "\t";
        }
        text = search + conditions + conditions2;
        if (isEntity) {
            if (id.equals("0")) {
                text = text + tab + clas.getSimpleName() +" entity = new " + clas.getSimpleName() + "();\n";
            }
            text = text + setEntities +
                    tab + "entity = mapper.update(entityDto, entity);\n";
        } else {
            if (id.equals("0")) {
                text = text + tab + clas.getSimpleName() + " entity = mapper.create(entityDto);\n";
            } else {
                text = text + tab + "entity = mapper.update(entityDto, entity);\n";
            }
        }
        return text;
    }

    private String delete() {
        tab = "\t\t\t\t";
        String text = "";
        String condition = "";
        Field[] fields;
        for (Class<?> aClass : classes) {
            if (!clas.getSimpleName().equals(aClass.getSimpleName())) {
                fields = aClass.getDeclaredFields();
                for (Field field : fields) {
                    if (field.getType().getSimpleName().equals(clas.getSimpleName())) {
                        text = text + "(entity.get" + aClass.getSimpleName() + "s() == null || entity.get" + aClass.getSimpleName() + "s().isEmpty()) && ";
                    }
                }
            }
        }
        if (!text.isEmpty()) {
            text = text.substring(0, text.length() - 4);
            text = "\t\t\t\tif (" + text + ") {\n";
            tab = tab + "\t";
        }
        return text;
    }

    private String generateCreate() {
        String successfully = "SUCCESSFULLY_CREATED_" + ConvertText.upperCase(clas.getSimpleName());
        addImports(true, PROJECT_PACKAGE + ".utils.SystemConstants.CREATE_PATH;\n");
        addImports(false, "java.util.List;\n");
        addImports(false, "java.util.ArrayList;\n");
        addImports(false, "org.springframework.http.HttpStatus;\n");
        GenerateConstants.setField(clas.getSimpleName(), successfully,"\"" + clas.getSimpleName() + " creado exitosamente.\"");
        addImports(true, PROJECT_PACKAGE + ".utils.Constants." + successfully + ";\n");
        addImports(false, "java.time.LocalDateTime;\n");
        String text = "\t@Override\n" +
                "\tpublic ReplyMessageSimple<" + clas.getSimpleName() + "OutputDTO> getCreate(" + clas.getSimpleName() + "CreateDTO entityDto) {\n" +
                "\t\treplyMessageSimple.setUri(getUri(CREATE_PATH));\n" +
                "\t\treplyMessageSimple.setError(true);\n" +
                "\t\treplyMessageSimple.setResponse(null);\n" +
                "\t\tList<String> messages = new ArrayList<>();\n" +
                "\t\ttry {\n" +
                process("\t\t\t", "0") +
                tab + clas.getSimpleName() + "OutputDTO entityOutput = mapper.read(repository.save(entity));\n" +
                tab + "replyMessageSimple.setHttpStatus(HttpStatus.CREATED);\n" +
                tab + "replyMessageSimple.setError(false);\n" +
                tab + "messages.add(" + successfully + ");\n" +
                tab + "replyMessageSimple.setResponse(entityOutput);\n";
        if (tab.equals("\t\t\t\t")) {
            text = text + "\t\t\t} else {\n" +
                    "\t\t\t\treplyMessageSimple.setHttpStatus(HttpStatus.CONFLICT);\n" +
                    "\t\t\t}\n";
        }
        text = text + "\t\t} catch (Exception e) {\n" +
                "\t\t\treplyMessageSimple.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);\n" +
                "\t\t\tmessages.add(e.getMessage());\n" +
                "\t\t}\n" +
                "\t\treplyMessageSimple.setMessage(messages);\n" +
                "\t\treplyMessageSimple.setDate(LocalDateTime.now());\n" +
                "\t\treturn replyMessageSimple;\n" +
                "\t}\n";
        return text;
    }

    private String generateGetAll() {
        addImports(true, PROJECT_PACKAGE + ".utils.SystemConstants.GET_ALL_PATH;\n");
        addImports(true, PROJECT_PACKAGE + ".utils.Constants.YES_CONTENT;\n");
        addImports(true, PROJECT_PACKAGE + ".utils.Constants.NO_CONTENT;\n");
        String text = "\t@Override\n" +
                "\tpublic ReplyMessageList<" + clas.getSimpleName() + "OutputDTO> getFindAll() {\n" +
                "\t\treplyMessageList.setUri(getUri(GET_ALL_PATH));\n" +
                "\t\treplyMessageList.setError(true);\n" +
                "\t\treplyMessageList.setResponse(null);\n" +
                "\t\tList<String> messages = new ArrayList<>();\n" +
                "\t\ttry {\n" +
                "\t\t\tList<" + clas.getSimpleName() + "> entities = repository.searchAll();\n" +
                "\t\t\tif (!entities.isEmpty()) {\n" +
                "\t\t\t\tList<" + clas.getSimpleName() + "OutputDTO> entitiesDto = mapper.readList(entities);\n" +
                "\t\t\t\tmessages.add(YES_CONTENT);\n" +
                "\t\t\t\treplyMessageList.setResponse(entitiesDto);\n" +
                "\t\t\t} else {\n" +
                "\t\t\t\tmessages.add(NO_CONTENT);\n" +
                "\t\t\t}\n" +
                "\t\t\treplyMessageList.setHttpStatus(HttpStatus.OK);\n" +
                "\t\t\treplyMessageList.setError(false);\n" +
                "\t\t} catch (Exception e) {\n" +
                "\t\t\treplyMessageList.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);\n" +
                "\t\t\tmessages.add(e.getMessage());\n" +
                "\t\t}\n" +
                "\t\treplyMessageList.setMessage(messages);\n" +
                "\t\treplyMessageList.setDate(LocalDateTime.now());\n" +
                "\t\treturn replyMessageList;\n" +
                "\t}\n";
        return text;
    }

    private String generateGetId() {
        addImports(true, PROJECT_PACKAGE + ".utils.SystemConstants.GET_ID_PATH;\n");
        addImports(true, PROJECT_PACKAGE + ".utils.Constants.NO_CONTENT_ID;\n");
        String text = "\t@Override\n" +
                "\tpublic ReplyMessageSimple<" + clas.getSimpleName() + "OutputDTO> getFindById(Integer id) {\n" +
                "\t\treplyMessageSimple.setUri(getUri(GET_ID_PATH, id));\n" +
                "\t\treplyMessageSimple.setError(true);\n" +
                "\t\treplyMessageSimple.setResponse(null);\n" +
                "\t\tList<String> messages = new ArrayList<>();\n" +
                "\t\ttry {\n" +
                "\t\t\t" + clas.getSimpleName() + " entity = repository.findById(id).orElse(null);\n" +
                "\t\t\tif (entity != null) {\n" +
                "\t\t\t\t" + clas.getSimpleName() + "OutputDTO entityDto = mapper.read(entity);\n" +
                "\t\t\t\treplyMessageSimple.setHttpStatus(HttpStatus.OK);\n" +
                "\t\t\t\treplyMessageSimple.setError(false);\n" +
                "\t\t\t\tmessages.add(YES_CONTENT);\n" +
                "\t\t\t\treplyMessageSimple.setResponse(entityDto);\n" +
                "\t\t\t} else {\n" +
                "\t\t\t\treplyMessageSimple.setHttpStatus(HttpStatus.NOT_FOUND);\n" +
                "\t\t\t\tmessages.add(NO_CONTENT_ID + id);\n" +
                "\t\t\t}\n" +
                "\t\t} catch (Exception e) {\n" +
                "\t\t\treplyMessageSimple.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);\n" +
                "\t\t\tmessages.add(e.getMessage());\n" +
                "\t\t}\n" +
                "\t\treplyMessageSimple.setMessage(messages);\n" +
                "\t\treplyMessageSimple.setDate(LocalDateTime.now());\n" +
                "\t\treturn replyMessageSimple;\n" +
                "\t}\n";
        return text;
    }

    private String generateUpdate() {
        String successfully = "SUCCESSFULLY_UPDATED_" + ConvertText.upperCase(clas.getSimpleName());
        addImports(true, PROJECT_PACKAGE + ".utils.SystemConstants.UPDATE_PATH;\n");
        GenerateConstants.setField(clas.getSimpleName(), successfully,"\"" + clas.getSimpleName() + " actualizado exitosamente.\"");
        addImports(true, PROJECT_PACKAGE + ".utils.Constants." + successfully + ";\n");
        String text = "\t@Override\n" +
                "\tpublic ReplyMessageSimple<" + clas.getSimpleName() + "OutputDTO> getUpdate(" + clas.getSimpleName() + "UpdateDTO entityDto) {\n" +
                "\t\treplyMessageSimple.setUri(getUri(UPDATE_PATH));\n" +
                "\t\treplyMessageSimple.setError(true);\n" +
                "\t\treplyMessageSimple.setResponse(null);\n" +
                "\t\tList<String> messages = new ArrayList<>();\n" +
                "\t\ttry {\n" +
                "\t\t\t" + clas.getSimpleName() + " entity = repository.findById(entityDto.getId" + clas.getSimpleName() + "()).orElse(null);\n" +
                "\t\t\tif (entity != null) {\n" +
                process("\t\t\t\t", "entityDto.getId" + clas.getSimpleName() + "()") +
                tab + clas.getSimpleName() + "OutputDTO entityOutput = mapper.read(repository.save(entity));\n" +
                tab + "replyMessageSimple.setHttpStatus(HttpStatus.CREATED);\n" +
                tab + "replyMessageSimple.setError(false);\n" +
                tab + "messages.add(" + successfully + ");\n" +
                tab + "replyMessageSimple.setResponse(entityOutput);\n";
        if (tab.equals("\t\t\t\t\t")) {
            text = text + "\t\t\t\t} else {\n" +
                    "\t\t\t\t\treplyMessageSimple.setHttpStatus(HttpStatus.CONFLICT);\n" +
                    "\t\t\t\t}\n";
        }
        text = text + "\t\t\t} else {\n" +
                "\t\t\t\treplyMessageSimple.setHttpStatus(HttpStatus.NOT_FOUND);\n" +
                "\t\t\t\tmessages.add(NO_CONTENT_ID + entityDto.getId" + clas.getSimpleName() + "());\n" +
                "\t\t\t}\n" +
                "\t\t} catch (Exception e) {\n" +
                "\t\t\treplyMessageSimple.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);\n" +
                "\t\t\tmessages.add(e.getMessage());\n" +
                "\t\t}\n" +
                "\t\treplyMessageSimple.setMessage(messages);\n" +
                "\t\treplyMessageSimple.setDate(LocalDateTime.now());\n" +
                "\t\treturn replyMessageSimple;\n" +
                "\t}";
        return text;
    }

    private String generateDelete() {
        String successfully = "SUCCESSFULLY_DELETED_" + ConvertText.upperCase(clas.getSimpleName());
        addImports(true, PROJECT_PACKAGE + ".utils.SystemConstants.DELETE_PATH;\n");
        GenerateConstants.setField(clas.getSimpleName(), successfully,"\"" + clas.getSimpleName() + " eliminado exitosamente.\"");
        addImports(true, PROJECT_PACKAGE + ".utils.Constants." + successfully + ";\n");
        String text = "\t@Override\n" +
                "\tpublic ReplyMessageSimple<" + clas.getSimpleName() + "OutputDTO> getDelete(Integer id) {\n" +
                "\t\treplyMessageSimple.setUri(getUri(DELETE_PATH, id));\n" +
                "\t\treplyMessageSimple.setError(true);\n" +
                "\t\treplyMessageSimple.setResponse(null);\n" +
                "\t\tList<String> messages = new ArrayList<>();\n" +
                "\t\ttry {\n" +
                "\t\t\t" + clas.getSimpleName() + " entity = repository.findById(id).orElse(null);\n" +
                "\t\t\tif (entity != null) {\n" +
                delete() +
                tab + "repository.delete(entity);\n" +
                tab + "replyMessageSimple.setHttpStatus(HttpStatus.OK);\n" +
                tab + "replyMessageSimple.setError(false);\n" +
                tab + "messages.add(" + successfully + ");\n";
        if (tab.equals("\t\t\t\t\t")) {
            String error = ConvertText.upperCase(clas.getSimpleName()) +"_RELATION";
            GenerateConstants.setField(clas.getSimpleName(), error,"\"No se puede eliminar el " + clas.getSimpleName() + " porque tiene relación con otras tablas.\"");
            addImports(true, PROJECT_PACKAGE + ".utils.Constants." + error + ";\n");
            text = text + "\t\t\t\t} else {\n" +
                    "\t\t\t\t\treplyMessageSimple.setHttpStatus(HttpStatus.CONFLICT);\n" +
                    "\t\t\t\t\tmessages.add(" + error + ");\n" +
                    "\t\t\t\t}\n";
        }
        text = text + "\t\t\t} else {\n" +
                "\t\t\t\treplyMessageSimple.setHttpStatus(HttpStatus.NOT_FOUND);\n" +
                "\t\t\t\tmessages.add(NO_CONTENT_ID + id);\n" +
                "\t\t\t}\n" +
                "\t\t} catch (Exception e) {\n" +
                "\t\t\treplyMessageSimple.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);\n" +
                "\t\t\tmessages.add(e.getMessage());\n" +
                "\t\t}\n" +
                "\t\treplyMessageSimple.setMessage(messages);\n" +
                "\t\treplyMessageSimple.setDate(LocalDateTime.now());\n" +
                "\t\treturn replyMessageSimple;\n" +
                "\t}\n";
        return text;
    }

    private String generateUri() {
        addImports(true, PROJECT_PACKAGE + ".utils.SystemConstants." + API_PATH + ";\n");
        addImports(true, PROJECT_PACKAGE + ".utils.SystemConstants." + setPath(clas.getSimpleName()) + ";\n");
        String text = "\t@Override\n" +
                "\tpublic String getUri(String method) {\n" +
                "\t\treturn " + API_PATH + " + " + setPath(clas.getSimpleName()) + " + method;\n" +
                "\t}\n" +
                "\n" +
                "\t@Override\n" +
                "\tpublic String getUri(String method, Integer id) {\n" +
                "\t\treturn " + API_PATH + " + " + setPath(clas.getSimpleName()) + " + method + id;\n" +
                "\t}\n";
        return text;
    }

    private String generateClass() {
        addImports(false, "org.springframework.stereotype.Service;\n");
        addImports(false, "org.springframework.validation.annotation.Validated;\n");
        addImports(false, PROJECT_PACKAGE + ".model.dto." + clas.getSimpleName().toLowerCase() + "." + clas.getSimpleName() + "CreateDTO;\n");
        addImports(false, PROJECT_PACKAGE + ".model.dto." + clas.getSimpleName().toLowerCase() + "." + clas.getSimpleName() + "OutputDTO;\n");
        addImports(false, PROJECT_PACKAGE + ".model.dto." + clas.getSimpleName().toLowerCase() + "." + clas.getSimpleName() + "UpdateDTO;\n");
        addImports(false, PROJECT_PACKAGE + ".model.entity." + clas.getSimpleName() + ";\n");
        addImports(false, PROJECT_PACKAGE + ".model.repository.I" + clas.getSimpleName() + "Repository;\n");
        addImports(false, PROJECT_PACKAGE + ".utils.mappers.IMapper;\n");
        addImports(false, "org.springframework.beans.factory.annotation.Autowired;\n");
        addImports(false, PROJECT_PACKAGE + ".utils.response.ReplyMessageList;\n");
        addImports(false, PROJECT_PACKAGE + ".utils.response.ReplyMessageSimple;\n");
        repositories.add("\t@Autowired\n" +
                "\tprivate I" + clas.getSimpleName() + "Repository repository;\n" +
                "\n");
        String create = generateCreate();
        String text = "@Service\n" +
                "@Validated\n" +
                "public class " + className + " implements IBaseService<" + clas.getSimpleName() + "CreateDTO, " + clas.getSimpleName() + "UpdateDTO, " + clas.getSimpleName() + "OutputDTO> {\n" +
                "\n" +
                printList(repositories) +
                "\t@Autowired\n" +
                "\tprivate ReplyMessageSimple<" + clas.getSimpleName() + "OutputDTO> replyMessageSimple;\n" +
                "\n" +
                "\t@Autowired\n" +
                "\tprivate ReplyMessageList<" + clas.getSimpleName() + "OutputDTO> replyMessageList;\n" +
                "\n" +
                "\t@Autowired\n" +
                "\tprivate IMapper<" + clas.getSimpleName() + "CreateDTO, " + clas.getSimpleName() + "UpdateDTO, " + clas.getSimpleName() + "OutputDTO, " + clas.getSimpleName() + "> mapper;\n" +
                "\n" +
                create +
                "\n" +
                generateGetAll() +
                "\n" +
                generateGetId() +
                "\n" +
                generateUpdate() +
                "\n" +
                generateDelete() +
                "\n" +
                generateUri() +
                "\n}";
        imports.sort(String::compareTo);
        importStatics.sort(String::compareTo);
        text = "package " + PROJECT_PACKAGE + "." + packageName + ";\n\n" +
                printList(imports) +
                printList(importStatics) + text;
        return text;
    }
}