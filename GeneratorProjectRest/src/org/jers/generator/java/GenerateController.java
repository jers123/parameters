package org.jers.generator.java;

import org.jers.generator.ConvertText;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import static org.jers.generator.Config.*;
import static org.jers.generator.GenerateFile.writeFile;

public class GenerateController {
    private Class<?> clas;
    private String className = "";
    private String packageName = "";
    private List<String> imports = new ArrayList<>();
    private List<String> importStatics = new ArrayList<>();
    private List<Class<?>> classes;

    public GenerateController(Class<?> clas, List<Class<?>> classes) {
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

    private void isEntity() {
        Annotation[] annotations = clas.getAnnotations();
        String annotation = annotations[0].annotationType().getSimpleName();
        if (annotation.equals("Entity")) {
            className = clas.getSimpleName() + "Controller";
            packageName = "controller";
            writeFile(ROUTE + "\\" + packageName.replace(".", "\\") + "\\" + className + ".java", generateClass());
        }
    }

    private String swaggerCreate() {
        String text = "";
        if (SWAGGER) {
            addImports(false, "io.swagger.v3.oas.annotations.Operation;\n");
            addImports(false, "io.swagger.v3.oas.annotations.responses.ApiResponses;\n");
            addImports(false, "io.swagger.v3.oas.annotations.responses.ApiResponse;\n");
            addImports(true, PROJECT_PACKAGE + ".utils.Constants.SUCCESSFULLY_CREATED_" + ConvertText.upperCase(clas.getSimpleName()) + ";\n");
            addImports(false, "io.swagger.v3.oas.annotations.headers.Header;\n");
            addImports(true, PROJECT_PACKAGE + ".utils.SystemConstants.CONTENT_TYPE;\n");
            addImports(true, "org.springframework.http.MediaType.APPLICATION_JSON_VALUE;\n");
            addImports(true, PROJECT_PACKAGE + ".utils.SystemConstants.ACCEPT;\n");
            addImports(false, "io.swagger.v3.oas.annotations.media.Content;\n");
            addImports(false, "io.swagger.v3.oas.annotations.media.Schema;\n");
            addImports(true, PROJECT_PACKAGE + ".utils.Constants." + ConvertText.upperCase(clas.getSimpleName()) + "_NAME_EXISTS;\n");
            text = "\t@Operation(summary = \"Create a new " + clas.getSimpleName() + "\", description = \"Create news registries of " + clas.getSimpleName() + "s\")\n" +
                    "\t@ApiResponses( value = {\n" +
                    "\t\t\t@ApiResponse(responseCode = \"201\", description = SUCCESSFULLY_CREATED_" + ConvertText.upperCase(clas.getSimpleName()) + ",\n" +
                    "\t\t\t\t\theaders = {\n" +
                    "\t\t\t\t\t\t\t@Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),\n";
            if (SECURITY) {
                addImports(true, PROJECT_PACKAGE + ".utils.SystemConstants.AUTHORIZATION;\n");
                text = text + "\t\t\t\t\t\t\t@Header(name = AUTHORIZATION, required = true),\n";
            }
            text = text + "\t\t\t\t\t\t\t@Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)\n" +
                    "\t\t\t\t\t},\n" +
                    "\t\t\t\t\tcontent = {\n" +
                    "\t\t\t\t\t\t@Content(mediaType = APPLICATION_JSON_VALUE,\n" +
                    "\t\t\t\t\t\t\t\tschema = @Schema(name = \"" + clas.getSimpleName() + " create\", description = \"Crete a " + clas.getSimpleName() + " schema\",\n" +
                    "\t\t\t\t\t\t\t\t\t\timplementation = " + clas.getSimpleName() + "OutputDTO.class))}),\n" +
                    "\t\t\t@ApiResponse(responseCode = \"409\", description = " + ConvertText.upperCase(clas.getSimpleName()) + "_NAME_EXISTS,\n" +
                    "\t\t\t\t\theaders = {\n" +
                    "\t\t\t\t\t\t\t@Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),\n";
            if (SECURITY) {
                text = text + "\t\t\t\t\t\t\t@Header(name = AUTHORIZATION, required = true),\n";
            }
            text = text + "\t\t\t\t\t\t\t@Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)\n" +
                    "\t\t\t\t\t},\n" +
                    "\t\t\t\t\tcontent = { @Content(mediaType = APPLICATION_JSON_VALUE) }),\n" +
                    "\t\t\t@ApiResponse(responseCode = \"500\", description = \"" + clas.getSimpleName() + " can't created by a system error\",\n" +
                    "\t\t\t\t\theaders = {\n" +
                    "\t\t\t\t\t\t\t@Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),\n";
            if (SECURITY) {
                text = text + "\t\t\t\t\t\t\t@Header(name = AUTHORIZATION, required = true),\n";
            }
            text = text + "\t\t\t\t\t\t\t@Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)\n" +
                    "\t\t\t\t\t},\n" +
                    "\t\t\t\t\tcontent = { @Content(mediaType = APPLICATION_JSON_VALUE) })\n" +
                    "\t})\n";
        }
        return text;
    }

    private String swaggerFindAll() {
        String text = "";
        if (SWAGGER) {
            addImports(true, PROJECT_PACKAGE + ".utils.Constants.YES_CONTENT;\n");
            addImports(true, PROJECT_PACKAGE + ".utils.Constants.NO_CONTENT;\n");
            text = "\t@Operation(summary = \"Show all registries of " + clas.getSimpleName() + "s\", description = \"Show all registries of " + clas.getSimpleName() + "s\")\n" +
                    "\t@ApiResponses( value = {\n" +
                    "\t\t\t@ApiResponse(responseCode = \"200\", description = YES_CONTENT + \" OR \" + NO_CONTENT,\n" +
                    "\t\t\t\t\theaders = {\n" +
                    "\t\t\t\t\t\t\t@Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)\n" +
                    "\t\t\t\t\t},\n" +
                    "\t\t\t\t\tcontent = {\n" +
                    "\t\t\t\t\t\t@Content(mediaType = APPLICATION_JSON_VALUE,\n" +
                    "\t\t\t\t\t\t\t\tschema = @Schema(name = \"" + clas.getSimpleName() + " get all\", description = \"Get all " + clas.getSimpleName() + " schema\",\n" +
                    "\t\t\t\t\t\t\t\t\t\timplementation = " + clas.getSimpleName() + "OutputDTO.class)) }),\n" +
                    "\t\t\t@ApiResponse(responseCode = \"500\", description = \"Can't get response by a system error\",\n" +
                    "\t\t\t\t\theaders = {\n" +
                    "\t\t\t\t\t\t\t@Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)\n" +
                    "\t\t\t\t\t},\n" +
                    "\t\t\t\t\tcontent = { @Content(mediaType = APPLICATION_JSON_VALUE) })\n" +
                    "\t})\n";
        }
        return text;
    }

    private String swaggerFindId() {
        String text = "";
        if (SWAGGER) {
            addImports(true, PROJECT_PACKAGE + ".utils.Constants.NO_CONTENT_ID;\n");
            text = "\t@Operation(summary = \"Show a registry of " + clas.getSimpleName() + " by id\", description = \"Show a registry of " + clas.getSimpleName() + " by id\")\n" +
                    "\t@ApiResponses( value = {\n" +
                    "\t\t\t@ApiResponse(responseCode = \"200\", description = YES_CONTENT,\n" +
                    "\t\t\t\t\theaders = {\n" +
                    "\t\t\t\t\t\t\t@Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)\n" +
                    "\t\t\t\t\t},\n" +
                    "\t\t\t\t\tcontent = {\n" +
                    "\t\t\t\t\t\t@Content(mediaType = APPLICATION_JSON_VALUE,\n" +
                    "\t\t\t\t\t\t\t\tschema = @Schema(name = \"" + clas.getSimpleName() + " get all\", description = \"Get " + clas.getSimpleName() + " by id schema\",\n" +
                    "\t\t\t\t\t\t\t\t\t\timplementation = " + clas.getSimpleName() + "OutputDTO.class)) }),\n" +
                    "\t\t\t@ApiResponse(responseCode = \"404\", description = NO_CONTENT_ID,\n" +
                    "\t\t\t\t\theaders = {\n" +
                    "\t\t\t\t\t\t\t@Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)\n" +
                    "\t\t\t\t\t},\n" +
                    "\t\t\t\t\tcontent = { @Content(mediaType = APPLICATION_JSON_VALUE) }),\n" +
                    "\t\t\t@ApiResponse(responseCode = \"500\", description = \"Can't get response by a system error\",\n" +
                    "\t\t\t\t\theaders = {\n" +
                    "\t\t\t\t\t\t\t@Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)\n" +
                    "\t\t\t\t\t},\n" +
                    "\t\t\t\t\tcontent = { @Content(mediaType = APPLICATION_JSON_VALUE) })\n" +
                    "\t})\n";
        }
        return text;
    }

    private String swaggerUpdate() {
        String text = "";
        if (SWAGGER) {
            addImports(true, PROJECT_PACKAGE + ".utils.Constants.SUCCESSFULLY_UPDATED_" + ConvertText.upperCase(clas.getSimpleName()) + ";\n");
            text = "\t@Operation(summary = \"Update a registry of " + clas.getSimpleName() + "\", description = \"Update a registry of " + clas.getSimpleName() + " by id\")\n" +
                    "\t@ApiResponses( value = {\n" +
                    "\t\t\t@ApiResponse(responseCode = \"200\", description = SUCCESSFULLY_UPDATED_" + ConvertText.upperCase(clas.getSimpleName()) + ",\n" +
                    "\t\t\t\t\theaders = {\n" +
                    "\t\t\t\t\t\t\t@Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),\n";
            if (SECURITY) {
                text = text + "\t\t\t\t\t\t\t@Header(name = AUTHORIZATION, required = true),\n";
            }
            text = text + "\t\t\t\t\t\t\t@Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)\n" +
                    "\t\t\t\t\t},\n" +
                    "\t\t\t\t\tcontent = {\n" +
                    "\t\t\t\t\t\t\t@Content(mediaType = APPLICATION_JSON_VALUE,\n" +
                    "\t\t\t\t\t\t\t\t\tschema = @Schema(name = \"" + clas.getSimpleName() + " update\", description = \"" + clas.getSimpleName() + " a gender schema\",\n" +
                    "\t\t\t\t\t\t\t\t\t\t\timplementation = " + clas.getSimpleName() + "OutputDTO.class)) }),\n" +
                    "\t\t\t@ApiResponse(responseCode = \"404\", description = NO_CONTENT_ID,\n" +
                    "\t\t\t\t\theaders = {\n" +
                    "\t\t\t\t\t\t\t@Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),\n";
            if (SECURITY) {
                text = text + "\t\t\t\t\t\t\t@Header(name = AUTHORIZATION, required = true),\n";
            }
            text = text + "\t\t\t\t\t\t\t@Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)\n" +
                    "\t\t\t\t\t},\n" +
                    "\t\t\t\t\tcontent = { @Content(mediaType = APPLICATION_JSON_VALUE) }),\n" +
                    "\t\t\t@ApiResponse(responseCode = \"409\", description = " + ConvertText.upperCase(clas.getSimpleName()) + "_NAME_EXISTS,\n" +
                    "\t\t\t\t\theaders = {\n" +
                    "\t\t\t\t\t\t\t@Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),\n";
            if (SECURITY) {
                text = text + "\t\t\t\t\t\t\t@Header(name = AUTHORIZATION, required = true),\n";
            }
            text = text + "\t\t\t\t\t\t\t@Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)\n" +
                    "\t\t\t\t\t},\n" +
                    "\t\t\t\t\tcontent = { @Content(mediaType = APPLICATION_JSON_VALUE) }),\n" +
                    "\t\t\t@ApiResponse(responseCode = \"500\", description = \"" + clas.getSimpleName() + " can't updated by a system error\",\n" +
                    "\t\t\t\t\theaders = {\n" +
                    "\t\t\t\t\t\t\t@Header(name = CONTENT_TYPE, required = true, example = APPLICATION_JSON_VALUE),\n";
            if (SECURITY) {
                text = text + "\t\t\t\t\t\t\t@Header(name = AUTHORIZATION, required = true),\n";
            }
            text = text + "\t\t\t\t\t\t\t@Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)\n" +
                    "\t\t\t\t\t},\n" +
                    "\t\t\t\t\tcontent = { @Content(mediaType = APPLICATION_JSON_VALUE) })\n" +
                    "\t})\n";
        }
        return text;
    }

    private String swaggerDelete() {
        String text = "";
        if (SWAGGER) {
            addImports(true, PROJECT_PACKAGE + ".utils.Constants.SUCCESSFULLY_DELETED_" + ConvertText.upperCase(clas.getSimpleName()) + ";\n");
            text = "\t@Operation(summary = \"Delete a registry of " + clas.getSimpleName() + "\", description = \"Delete a registry of " + clas.getSimpleName() + " by id\")\n" +
                    "\t@ApiResponses( value = {\n" +
                    "\t\t\t@ApiResponse(responseCode = \"200\", description = SUCCESSFULLY_DELETED_" + ConvertText.upperCase(clas.getSimpleName()) + ",\n" +
                    "\t\t\t\t\theaders = {\n";
            if (SECURITY) {
                text = text + "\t\t\t\t\t\t\t@Header(name = AUTHORIZATION, required = true),\n";
            }
            text = text + "\t\t\t\t\t\t\t@Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)\n" +
                    "\t\t\t\t\t},\n" +
                    "\t\t\t\t\tcontent = {\n" +
                    "\t\t\t\t\t\t\t@Content(mediaType = APPLICATION_JSON_VALUE) }),\n" +
                    "\t\t\t@ApiResponse(responseCode = \"404\", description = NO_CONTENT_ID,\n" +
                    "\t\t\t\t\theaders = {\n";
            if (SECURITY) {
                text = text + "\t\t\t\t\t\t\t@Header(name = AUTHORIZATION, required = true),\n";
            }
            text = text + "\t\t\t\t\t\t\t@Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)\n" +
                    "\t\t\t\t\t},\n" +
                    "\t\t\t\t\tcontent = { @Content(mediaType = APPLICATION_JSON_VALUE) }),\n" +
                    "\t\t\t@ApiResponse(responseCode = \"500\", description = \"Gender can't deleted by a system error\",\n" +
                    "\t\t\t\t\theaders = {\n";
            if (SECURITY) {
                text = text + "\t\t\t\t\t\t\t@Header(name = AUTHORIZATION, required = true),\n";
            }
            text = text + "\t\t\t\t\t\t\t@Header(name = ACCEPT, required = true, example = APPLICATION_JSON_VALUE)\n" +
                    "\t\t\t\t\t},\n" +
                    "\t\t\t\t\tcontent = { @Content(mediaType = APPLICATION_JSON_VALUE) })\n" +
                    "\t})\n";
        }
        return text;
    }

    private String generateClass() {
        addImports(false, PROJECT_PACKAGE + ".annotation.RestApi;\n");
        addImports(false, "org.springframework.web.bind.annotation.RequestMapping;\n");
        addImports(true, PROJECT_PACKAGE + ".utils.SystemConstants." + API_PATH + ";\n");
        addImports(true, PROJECT_PACKAGE + ".utils.SystemConstants." + setPath(clas.getSimpleName()) + ";\n");
        addImports(false, PROJECT_PACKAGE + ".model.dto." + clas.getSimpleName().toLowerCase() + "." + clas.getSimpleName() + "CreateDTO;\n");
        addImports(false, PROJECT_PACKAGE + ".model.dto." + clas.getSimpleName().toLowerCase() + "." + clas.getSimpleName() + "OutputDTO;\n");
        addImports(false, PROJECT_PACKAGE + ".model.dto." + clas.getSimpleName().toLowerCase() + "." + clas.getSimpleName() + "UpdateDTO;\n");
        addImports(false, PROJECT_PACKAGE + ".service.IBaseService;\n");
        addImports(false, PROJECT_PACKAGE + ".utils.response.ReplyMessageSimple;\n");
        addImports(true, PROJECT_PACKAGE + ".utils.SystemConstants.answerSimple;\n");
        addImports(false, "org.springframework.dao.DataAccessException;\n");
        addImports(false, PROJECT_PACKAGE + ".utils.response.ReplyMessageList;\n");
        addImports(true, PROJECT_PACKAGE + ".utils.SystemConstants.answerList;\n");
        String text = "@RestApi\n" +
                "@RequestMapping(path = " + API_PATH + " + " + setPath(clas.getSimpleName()) + ")\n";
        if (SWAGGER) {
            addImports(false, "io.swagger.v3.oas.annotations.tags.Tag;\n");
            text =text + "@Tag(name = \"" + clas.getSimpleName() + " API\", description = \"Create, update, read and delete " + clas.getSimpleName() + "s\")\n";
        }
        addImports(false, "org.springframework.beans.factory.annotation.Autowired;\n");
        addImports(false, "org.springframework.http.ResponseEntity;\n");
        text =text + "public class " + clas.getSimpleName() + "Controller implements IBaseController<" + clas.getSimpleName() + "CreateDTO, " + clas.getSimpleName() + "UpdateDTO> {\n" +
                "\n" +
                "\t@Autowired\n" +
                "\tprivate IBaseService<" + clas.getSimpleName() + "CreateDTO, " + clas.getSimpleName() + "UpdateDTO, " + clas.getSimpleName() + "OutputDTO> service;\n" +
                "\n" +
                "\t@Override\n" +
                swaggerCreate() +
                "\tpublic ResponseEntity<ReplyMessageSimple> create(" + clas.getSimpleName() + "CreateDTO entityDto) {\n" +
                "\t\ttry {\n" +
                "\t\t\treturn answerSimple(service.getCreate(entityDto));\n" +
                "\t\t} catch (DataAccessException e) {\n" +
                "\t\t\tthrow new RuntimeException(e.getMessage());\n" +
                "\t\t}\n" +
                "\t}\n" +
                "\n" +
                "\t@Override\n" +
                swaggerFindAll() +
                "\tpublic ResponseEntity<ReplyMessageList> getAll() {\n" +
                "\t\ttry {\n" +
                "\t\t\treturn answerList(service.getFindAll());\n" +
                "\t\t} catch (DataAccessException e) {\n" +
                "\t\t\tthrow new RuntimeException(e.getMessage());\n" +
                "\t\t}\n" +
                "\t}\n" +
                "\n" +
                "\t@Override\n" +
                swaggerFindId() +
                "\tpublic ResponseEntity<ReplyMessageSimple> getById(Integer id) {\n" +
                "\t\ttry {\n" +
                "\t\t\treturn answerSimple(service.getFindById(id));\n" +
                "\t\t} catch (DataAccessException e) {\n" +
                "\t\t\tthrow new RuntimeException(e.getMessage());\n" +
                "\t\t}\n" +
                "\t}\n" +
                "\n" +
                "\t@Override\n" +
                swaggerUpdate() +
                "\tpublic ResponseEntity<ReplyMessageSimple> update(" + clas.getSimpleName() + "UpdateDTO entityDto) {\n" +
                "\t\ttry {\n" +
                "\t\t\treturn answerSimple(service.getUpdate(entityDto));\n" +
                "\t\t} catch (DataAccessException e) {\n" +
                "\t\t\tthrow new RuntimeException(e.getMessage());\n" +
                "\t\t}\n" +
                "\t}\n" +
                "\n" +
                "\t@Override\n" +
                swaggerDelete() +
                "\tpublic ResponseEntity<ReplyMessageSimple> delete(Integer id) {\n" +
                "\t\ttry {\n" +
                "\t\t\treturn answerSimple(service.getDelete(id));\n" +
                "\t\t} catch (DataAccessException e) {\n" +
                "\t\t\tthrow new RuntimeException(e.getMessage());\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";
        imports.sort(String::compareTo);
        importStatics.sort(String::compareTo);
        text = "package " + PROJECT_PACKAGE + "." + packageName + ";\n\n" +
                printList(imports) +
                printList(importStatics) + text;
        return text;
    }
}