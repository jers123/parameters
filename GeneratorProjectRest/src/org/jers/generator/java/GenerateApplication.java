package org.jers.generator.java;

import org.jers.generator.ConvertText;
import org.jers.generator.enums.Packaging;

import java.time.LocalDateTime;

import static org.jers.generator.Config.*;
import static org.jers.generator.GenerateFile.createFolder;
import static org.jers.generator.GenerateFile.writeFile;

public class GenerateApplication {

    private static final String CONFIG_PAACKAGE = "config";
    private static final String RESPONSE_PAACKAGE = "utils.response";

    private static void generateApplicationFile(String route) {
        String text = "spring.application.name=" + NAME + "\n" +
                "\n" +
                "server.port=" + PORT + "\n" +
                "\n";
        if (SECURITY) {
            text = text + "spring.security.user.name=jers\n" +
                    "spring.security.user.password=" + ARTEFACT_ID + "\n" +
                    "spring.security.auth.basic.enabled=true\n" +
                    "\n";
        }
        text = text + "spring.jpa.hibernate.ddl-auto=update\n" +
                "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect\n" +
                "spring.datasource.url=jdbc:postgresql://localhost:5432/" + DATABASE +"\n" +
                "spring.datasource.username=" + USER + "\n" +
                "spring.datasource.password=" + PASS + "\n" +
                "\n" +
                "spring.jpa.show-sql=true\n" +
                "spring.jpa.properties.hibernate.format-sql=true\n" +
                "\n";
        if (SWAGGER) {
            text = text + "springdoc.api-docs.enabled=true\n" +
                    "springdoc.swagger-ui.enabled=true\n" +
                    "springdoc.api-docs.path=/" + ARTEFACT_ID + "-docs\n" +
                    "springdoc.swagger-ui.path=/swagger-ui";
        }
        writeFile(route, text);
    }

    private static void generateApplication() {
        String className = ConvertText.upperText(ARTEFACT_ID, "-", false) + "Application";
        String text = "package " + PROJECT_PACKAGE + ";\n" +
                "\n" +
                "import org.springframework.boot.SpringApplication;\n" +
                "import org.springframework.boot.autoconfigure.SpringBootApplication;\n" +
                "\n" +
                "@SpringBootApplication\n" +
                "public class " + className + " {\n" +
                "\n" +
                "\tpublic static void main(String[] args) {\n" +
                "\t\tSpringApplication.run(" + className + ".class, args);\n" +
                "\t}\n" +
                "}";
        writeFile(ROUTE + "\\" + className + ".java", text);
        if (PACKAGING.equals(Packaging.WAR)) {
            text = "package " + PROJECT_PACKAGE + ";\n" +
                    "\n" +
                    "import org.springframework.boot.builder.SpringApplicationBuilder;\n" +
                    "import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;\n" +
                    "\n" +
                    "public class ServletInitializer extends SpringBootServletInitializer {\n" +
                    "\n" +
                    "\t@Override\n" +
                    "\tprotected SpringApplicationBuilder configure(SpringApplicationBuilder application) {\n" +
                    "\t\treturn application.sources(" + className + "Application.class);\n" +
                    "\t}\n" +
                    "}\n";
            writeFile(ROUTE + "\\ServletInitializer.java", text);
        }
    }

    private static void generateRestApiAnnotation() {
        String packageName = "annotation";
        String className = "RestApi";
        String text = "package " + PROJECT_PACKAGE + "." + packageName + ";\n" +
                "\n" +
                "import org.springframework.web.bind.annotation.CrossOrigin;\n" +
                "import org.springframework.web.bind.annotation.RequestMethod;\n" +
                "import org.springframework.web.bind.annotation.RestController;\n" +
                "\n" +
                "import java.lang.annotation.ElementType;\n" +
                "import java.lang.annotation.Retention;\n" +
                "import java.lang.annotation.RetentionPolicy;\n" +
                "import java.lang.annotation.Target;\n" +
                "\n" +
                "import static " + PROJECT_PACKAGE + ".utils.SystemConstants.LOCAL_ORIGIN_PATH;\n" +
                "import static " + PROJECT_PACKAGE + ".utils.SystemConstants.LOCAL_ORIGIN_PATH2;\n" +
                "import static " + PROJECT_PACKAGE + ".utils.SystemConstants.PUBLIC_ORIGIN_PATH;\n" +
                "import static " + PROJECT_PACKAGE + ".utils.SystemConstants.PUBLIC_ORIGIN_PATH2;\n" +
                "\n" +
                "@Target(ElementType.TYPE)\n" +
                "@Retention(RetentionPolicy.RUNTIME)\n" +
                "@RestController\n" +
                "@CrossOrigin(origins = {LOCAL_ORIGIN_PATH,\n" +
                "\t\tPUBLIC_ORIGIN_PATH,\n" +
                "\t\tLOCAL_ORIGIN_PATH2,\n" +
                "\t\tPUBLIC_ORIGIN_PATH2\n";
        if (!SECURITY) {
            text = text + "\t\t,\"*\"";
        }
        text = text + "\t}\n" +
                "\t,methods = {RequestMethod.GET,\n" +
                "\t\tRequestMethod.POST,\n" +
                "\t\tRequestMethod.PUT,\n" +
                "\t\tRequestMethod.DELETE\n" +
                "\t}\n" +
                ")\n" +
                "public @interface " + className + " {\n" +
                "}";
        writeFile(ROUTE + "\\" + packageName + "\\" + className + ".java", text);
    }

    private static void generateConfiguration() {
        String className = ConvertText.upperText(ARTEFACT_ID, "-", false) + "Configuration";
        String text = "package " + PROJECT_PACKAGE + "." + CONFIG_PAACKAGE + ";\n" +
                "\n" +
                "import org.springframework.context.annotation.Configuration;\n" +
                "import org.springframework.web.servlet.config.annotation.CorsRegistry;\n" +
                "import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;\n" +
                "\n";
        if (SECURITY) {
            text = text + "\n" +
                    "import static " + PROJECT_PACKAGE + ".utils.SystemConstants.LOCAL_ORIGIN_PATH;\n" +
                    "import static " + PROJECT_PACKAGE + ".utils.SystemConstants.LOCAL_ORIGIN_PATH2;\n" +
                    "import static " + PROJECT_PACKAGE + ".utils.SystemConstants.PUBLIC_ORIGIN_PATH;\n" +
                    "import static " + PROJECT_PACKAGE + ".utils.SystemConstants.PUBLIC_ORIGIN_PATH2;\n" +
                    "\n";
        }
        text = text + "@Configuration\n" +
                "public class " + className + " implements WebMvcConfigurer {\n" +
                "\n" +
                "\t@Override\n" +
                "\tpublic void addCorsMappings(CorsRegistry registry) {\n" +
                "\t\tregistry.addMapping(\"/**\")\n" +
                "\t\t\t\t.allowedOrigins(";
        if (SECURITY) {
            text = text + "LOCAL_ORIGIN_PATH,\n" +
                    "\t\t\t\t\t\tPUBLIC_ORIGIN_PATH,\n" +
                    "\t\t\t\t\t\tLOCAL_ORIGIN_PATH2,\n" +
                    "\t\t\t\t\t\tPUBLIC_ORIGIN_PATH2\n" +
                    "\t\t\t\t)\n";
        } else {
            text = text + "\"*\")\n";
        }
        text = text + "\t\t\t\t.allowedMethods(\"GET\", \"POST\", \"PUT\", \"DELETE\")\n" +
                "\t\t\t\t.allowedHeaders(\"*\")\n" +
                "\t\t\t\t.allowCredentials(" + SECURITY + ")\n" +
                "\t\t\t\t.maxAge(3600)\n" +
                "\t\t;\n" +
                "\t}\n" +
                "}";
        writeFile(ROUTE + "\\" + CONFIG_PAACKAGE + "\\" + className + ".java", text);
    }

    private static void generateSecurity() {
        String className = "SecurityConfig";
        String text = "package " + PROJECT_PACKAGE + "." + CONFIG_PAACKAGE + ";\n" +
                "\n" +
                "import org.springframework.context.annotation.Bean;\n" +
                "import org.springframework.context.annotation.Configuration;\n" +
                "import org.springframework.http.HttpMethod;\n" +
                "import org.springframework.security.config.Customizer;\n" +
                "import org.springframework.security.config.annotation.web.builders.HttpSecurity;\n" +
                "import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;\n" +
                "import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;\n" +
                "import org.springframework.security.web.SecurityFilterChain;\n" +
                "\n" +
                "import static " + PROJECT_PACKAGE + ".utils.SystemConstants." + API_PATH + ";\n" +
                "\n" +
                "@Configuration\n" +
                "@EnableWebSecurity\n" +
                "public class " + className + " {\n" +
                "\n" +
                "\t@Bean\n" +
                "\tpublic SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {\n" +
                "\t\thttp\n" +
                "\t\t\t.cors(Customizer.withDefaults())\n" +
                "\t\t\t.csrf(AbstractHttpConfigurer::disable)\n" +
                "\t\t\t.authorizeHttpRequests(authz -> authz\n" +
                "\t\t\t\t.requestMatchers(HttpMethod.GET, " + API_PATH + " + \"/**\").permitAll()\n" +
                "\t\t\t\t.requestMatchers(HttpMethod.POST, " + API_PATH + " + \"/**\").authenticated()\n" +
                "\t\t\t\t.requestMatchers(HttpMethod.PUT, " + API_PATH + " + \"/**\").authenticated()\n" +
                "\t\t\t\t.requestMatchers(HttpMethod.DELETE, " + API_PATH + " + \"/**\").authenticated()\n" +
                "\t\t\t\t.anyRequest().authenticated()\n" +
                "\t\t\t)\n" +
                "\t\t\t.httpBasic(Customizer.withDefaults());\n" +
                "\t\treturn http.build();\n" +
                "\t}\n" +
                "}";
        writeFile(ROUTE + "\\" + CONFIG_PAACKAGE + "\\" + className + ".java", text);
    }
    private static void generateSwagger() {
        String className = "SwaggerConfig";
        String text = "package " + PROJECT_PACKAGE + "." + CONFIG_PAACKAGE + ";\n" +
                "\n" +
                "import io.swagger.v3.oas.models.ExternalDocumentation;\n" +
                "import io.swagger.v3.oas.models.OpenAPI;\n" +
                "import io.swagger.v3.oas.models.info.Contact;\n" +
                "import io.swagger.v3.oas.models.info.Info;\n" +
                "import io.swagger.v3.oas.models.info.License;\n" +
                "import org.springdoc.core.models.GroupedOpenApi;\n" +
                "import org.springframework.context.annotation.Bean;\n" +
                "import org.springframework.context.annotation.Configuration;\n" +
                "\n" +
                "import static " + PROJECT_PACKAGE + ".utils.SystemConstants." + API_PATH + ";\n" +
                "\n" +
                "@Configuration\n" +
                "public class " + className + " {\n" +
                "\t@Bean\n" +
                "\tpublic GroupedOpenApi publicApi() {\n" +
                "\t\treturn GroupedOpenApi.builder()\n" +
                "\t\t\t\t.group(\"springshop-public\")\n" +
                "\t\t\t\t.pathsToMatch(" + API_PATH + " + \"/**\")\n" +
                "\t\t\t\t.build();\n" +
                "\t}\n" +
                "\n" +
                "\t@Bean\n" +
                "\tpublic OpenAPI springShopOpenAPI() {\n" +
                "\t\tContact contact = new Contact();\n" +
                "\t\tcontact.setName(\"Julian Enrique Rodriguez\");\n" +
                "\t\tcontact.setEmail(\"julianand2009@hotmail.com\");\n" +
                "\t\tcontact.setUrl(\"https://www.linkedin.com/in/julianerodriguezs/\");\n" +
                "\t\treturn new OpenAPI()\n" +
                "\t\t\t\t.info(new Info()\n" +
                "\t\t\t\t\t\t.title(\"" + NAME + "\")\n" +
                "\t\t\t\t\t\t.description(\"" + DESCRIPTION + "\")\n" +
                "\t\t\t\t\t\t.version(\"1.0\")\n" +
                "\t\t\t\t\t\t.license(new License().name(\"Apache 2.0\").url(\"http://springdoc.org\"))\n" +
                "\t\t\t\t\t\t.contact(contact)\n" +
                "\t\t\t\t)\n" +
                "\t\t\t\t.externalDocs(new ExternalDocumentation()\n" +
                "\t\t\t\t\t\t.description(\"SpringShop Wiki Documentation\")\n" +
                "\t\t\t\t\t\t.url(\"https://springshop.wiki.github.org/docs\")\n" +
                "\t\t\t\t);\n" +
                "\t}\n" +
                "}";
        writeFile(ROUTE + "\\" + CONFIG_PAACKAGE + "\\" + className + ".java", text);
    }

    private static void generateConfig() {
        generateConfiguration();
        if (SECURITY) {
            generateSecurity();
        }
        if (SWAGGER) {
            generateSwagger();
        }

    }

    private static void generateExceptionHandler() {
        String packageName = "exception";
        String className = "ApiExceptionHandler";
        String text = "package " + PROJECT_PACKAGE + "." + packageName + ";\n" +
                "\n" +
                "import " + PROJECT_PACKAGE + ".utils.response.ReplyMessageSimple;\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import org.springframework.http.HttpStatus;\n" +
                "import org.springframework.http.ResponseEntity;\n" +
                "import org.springframework.http.converter.HttpMessageNotReadableException;\n" +
                "import org.springframework.validation.FieldError;\n" +
                "import org.springframework.web.HttpRequestMethodNotSupportedException;\n" +
                "import org.springframework.web.bind.MethodArgumentNotValidException;\n" +
                "import org.springframework.web.bind.annotation.ExceptionHandler;\n" +
                "import org.springframework.web.bind.annotation.ResponseBody;\n" +
                "import org.springframework.web.bind.annotation.RestControllerAdvice;\n" +
                "import org.springframework.web.context.request.WebRequest;\n" +
                "import org.springframework.web.method.annotation.HandlerMethodValidationException;\n" +
                "import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;\n" +
                "\n" +
                "import java.time.LocalDateTime;\n" +
                "import java.util.ArrayList;\n" +
                "import java.util.Collection;\n" +
                "import java.util.HashMap;\n" +
                "import java.util.List;\n" +
                "import java.util.Map;\n" +
                "\n" +
                "import static " + PROJECT_PACKAGE + ".utils.Constants.HTTP_MESSAGE1;\n" +
                "import static " + PROJECT_PACKAGE + ".utils.Constants.HTTP_MESSAGE2;\n" +
                "import static " + PROJECT_PACKAGE + ".utils.Constants.ID_VALUE_MINIMUM;\n" +
                "import static " + PROJECT_PACKAGE + ".utils.SystemConstants.answerSimple;\n" +
                "\n" +
                "@RestControllerAdvice\n" +
                "public class " + className + " {\n" +
                "\t@Autowired\n" +
                "\tprivate ReplyMessageSimple replyMessageSimple;\n" +
                "\n" +
                "\t@ExceptionHandler({HttpRequestMethodNotSupportedException.class})\n" +
                "\t@ResponseBody\n" +
                "\tpublic ResponseEntity<ReplyMessageSimple> httpRequestMethodException(HttpRequestMethodNotSupportedException exception, WebRequest webRequest) {\n" +
                "\t\tList<String> messages = new ArrayList<>();\n" +
                "\t\tmessages.add(HTTP_MESSAGE1 + supportedMethods(exception) + HTTP_MESSAGE2 + exception.getMethod());\n" +
                "\t\treplyMessageSimple.setUri(webRequest.getDescription(false).replace(\"uri=\",\"\"));\n" +
                "\t\treplyMessageSimple.setHttpStatus(HttpStatus.METHOD_NOT_ALLOWED);\n" +
                "\t\treplyMessageSimple.setError(true);\n" +
                "\t\treplyMessageSimple.setMessage(messages);\n" +
                "\t\treplyMessageSimple.setResponse(null);\n" +
                "\t\treplyMessageSimple.setDate(LocalDateTime.now());\n" +
                "\t\treturn answerSimple(replyMessageSimple);\n" +
                "\t}\n" +
                "\n" +
                "\t@ExceptionHandler({MethodArgumentNotValidException.class})\n" +
                "\t@ResponseBody\n" +
                "\tpublic ResponseEntity<ReplyMessageSimple> validationFieldsException(MethodArgumentNotValidException exception, WebRequest webRequest) {\n" +
                "\t\tMap<String, String> mapErrors = new HashMap<>();\n" +
                "\t\texception.getBindingResult().getAllErrors().forEach((error) -> {\n" +
                "\t\t\tString clave = ((FieldError) error).getField();\n" +
                "\t\t\tString valor = error.getDefaultMessage();\n" +
                "\t\t\tmapErrors.put(clave, valor);\n" +
                "\t\t});\n" +
                "\t\tCollection<String> errors = mapErrors.values();\n" +
                "\t\tList<String> messages = new ArrayList<>(errors);\n" +
                "\t\treplyMessageSimple.setUri(webRequest.getDescription(false).replace(\"uri=\",\"\"));\n" +
                "\t\treplyMessageSimple.setHttpStatus(HttpStatus.BAD_REQUEST);\n" +
                "\t\treplyMessageSimple.setError(true);\n" +
                "\t\treplyMessageSimple.setMessage(messages);\n" +
                "\t\treplyMessageSimple.setResponse(null);\n" +
                "\t\treplyMessageSimple.setDate(LocalDateTime.now());\n" +
                "\t\treturn answerSimple(replyMessageSimple);\n" +
                "\t}\n" +
                "\n" +
                "\t@ExceptionHandler({HttpMessageNotReadableException.class})\n" +
                "\t@ResponseBody\n" +
                "\tpublic ResponseEntity<ReplyMessageSimple> validationFieldsExceptionBySQL(HttpMessageNotReadableException exception, WebRequest webRequest) {\n" +
                "\t\tList<String> messages = new ArrayList<>();\n" +
                "\t\tString error = exception.getMessage().trim();\n" +
                "\t\terror = error.replace(\"JSON parse error: \", \"\");\n" +
                "\t\tif(error.substring(0,20).equals(\"Unexpected character\")) {\n" +
                "\t\t\tmessages.add(\"Error a leer los datos de entrada, revise que el formato JSON este correcto\");\n" +
                "\t\t}\n" +
                "\t\tif(error.substring(0,32).equals(\"Cannot deserialize value of type\")) {\n" +
                "\t\t\tString[] errors = error.split(\" \");\n" +
                "\t\t\terrors[5] = errors[5].replace(\"`\", \"\");\n" +
                "\t\t\terrors[5] = errors[5].split(\"\\\\.\")[2];\n" +
                "\t\t\tmessages.add(\"No se puede guardar un valor de tipo \" + errors[7] + \" en una variable de tipo \" + errors[5]);\n" +
                "\t\t}\n" +
                "\t\treplyMessageSimple.setUri(webRequest.getDescription(false).replace(\"uri=\",\"\"));\n" +
                "\t\treplyMessageSimple.setHttpStatus(HttpStatus.BAD_REQUEST);\n" +
                "\t\treplyMessageSimple.setError(true);\n" +
                "\t\treplyMessageSimple.setMessage(messages);\n" +
                "\t\treplyMessageSimple.setResponse(null);\n" +
                "\t\treplyMessageSimple.setDate(LocalDateTime.now());\n" +
                "\t\treturn answerSimple(replyMessageSimple);\n" +
                "\t}\n" +
                "\n" +
                "\t@ExceptionHandler({MethodArgumentTypeMismatchException.class})\n" +
                "\t@ResponseBody\n" +
                "\tpublic ResponseEntity<ReplyMessageSimple> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception, WebRequest webRequest) {\n" +
                "\t\tList<String> messages = new ArrayList<>();\n" +
                "\t\tmessages.add(\"Se esperaba que el parámetro \" + exception.getName() + \" recibiera un tipo de dato \" +\n" +
                "\t\t\t\texception.getRequiredType().getSimpleName() + \" pero recibió un tipo de dato \" + exception.getValue().getClass().getSimpleName());\n" +
                "\n" +
                "\t\treplyMessageSimple.setUri(webRequest.getDescription(false).replace(\"uri=\",\"\"));\n" +
                "\t\treplyMessageSimple.setHttpStatus(HttpStatus.BAD_REQUEST);\n" +
                "\t\treplyMessageSimple.setError(true);\n" +
                "\t\treplyMessageSimple.setMessage(messages);\n" +
                "\t\treplyMessageSimple.setResponse(null);\n" +
                "\t\treplyMessageSimple.setDate(LocalDateTime.now());\n" +
                "\t\treturn answerSimple(replyMessageSimple);\n" +
                "\t}\n" +
                "\n" +
                "\t@ExceptionHandler({HandlerMethodValidationException.class})\n" +
                "\t@ResponseBody\n" +
                "\tpublic ResponseEntity<ReplyMessageSimple> handlerMethodValidationException(HandlerMethodValidationException exception, WebRequest webRequest) {\n" +
                "\t\tList<String> messages = new ArrayList<>();\n" +
                "\t\tmessages.add(ID_VALUE_MINIMUM);\n" +
                "\n" +
                "\t\treplyMessageSimple.setUri(webRequest.getDescription(false).replace(\"uri=\",\"\"));\n" +
                "\t\treplyMessageSimple.setHttpStatus(HttpStatus.BAD_REQUEST);\n" +
                "\t\treplyMessageSimple.setError(true);\n" +
                "\t\treplyMessageSimple.setMessage(messages);\n" +
                "\t\treplyMessageSimple.setResponse(null);\n" +
                "\t\treplyMessageSimple.setDate(LocalDateTime.now());\n" +
                "\t\treturn answerSimple(replyMessageSimple);\n" +
                "\t}\n" +
                "\n" +
                "\tprivate String supportedMethods(HttpRequestMethodNotSupportedException exception) {\n" +
                "\t\tString method = exception.getSupportedHttpMethods().toString();\n" +
                "\t\tmethod = method.replace(\"[\",\"\");\n" +
                "\t\tmethod = method.replace(\"]\",\"\");\n" +
                "\t\treturn method;\n" +
                "\t}\n" +
                "}";
        writeFile(ROUTE + "\\" + packageName + "\\" + className + ".java", text);
    }

    private static void generateReplyMessage() {
        String className = "ReplyMessage";
        String text = "package " + PROJECT_PACKAGE + "." + RESPONSE_PAACKAGE + ";\n" +
                "\n" +
                "import com.fasterxml.jackson.annotation.JsonFormat;\n";
        if(SWAGGER) {
            text = text + "import io.swagger.v3.oas.annotations.media.Schema;\n";
        }
        if (LOMBOK) {
            text = text + "import lombok.Getter;\n" +
                    "import lombok.Setter;\n";
        }
        text = text + "import org.springframework.http.HttpStatus;\n" +
                "\n" +
                "import java.time.LocalDateTime;\n" +
                "import java.util.List;\n" +
                "\n";
        if (SWAGGER) {
            text = text + "import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;\n" +
                    "\"import static " + PROJECT_PACKAGE + ".utils.SystemConstants." + API_PATH + ";\n";
        }
        text = text + "\n";
        if (LOMBOK) {
            text = text + "@Getter\n" +
                    "@Setter\n";
        }
        if (SWAGGER) {
            text = text + "@Schema(hidden = true)\n";
        }
        text = text + "public abstract class " + className + " {\n";
        if (SWAGGER) {
            text = text + "\t@Schema(description = \"Is the full path of request\", defaultValue = " + API_PATH + ", requiredMode=REQUIRED)\n";
        }
        text = text + "\tprivate String uri;\n";
        if (SWAGGER) {
            text = text + "\n" +
                    "\t@Schema(description = \"Is the http status of request\", defaultValue = \"400\", requiredMode=REQUIRED)\n";
        }
        text = text + "\tprivate HttpStatus httpStatus;\n";
        if (SWAGGER) {
            text = text + "\n" +
                    "\t@Schema(description = \"Show if the response is error\", defaultValue = \"true\", requiredMode=REQUIRED)\n";
        }
        text = text + "\tprivate Boolean error;\n";
        if (SWAGGER) {
            text = text + "\n" +
                    "\t@Schema(description = \"Is a list of messages with happen\", defaultValue = \"{response1, response2}\", requiredMode=REQUIRED)\n";
        }
        text = text + "\tprivate List<String> message;\n" +
                "\n" +
                "\t@JsonFormat(pattern = \"dd/MM/yyyy HH:mm:ss\")";
        if (SWAGGER) {
            text = text + "\n" +
                    "\t@Schema(description = \"Is the full date time of request\", defaultValue = \"" + LocalDateTime.now() + "\", requiredMode=REQUIRED)\n";
        }
        text = text + "\tprivate LocalDateTime date;\n";
        if (!LOMBOK) {
            text = text + "\n" +
                    "\tpublic String getUri() {\n" +
                    "\t\treturn uri;\n" +
                    "\t}\n" +
                    "\n" +
                    "\tpublic void setUri(String uri) {\n" +
                    "\t\tthis.uri = uri;\n" +
                    "\t}\n" +
                    "\n" +
                    "\tpublic HttpStatus getHttpStatus() {\n" +
                    "\t\treturn httpStatus;\n" +
                    "\t}\n" +
                    "\n" +
                    "\tpublic void setHttpStatus(HttpStatus httpStatus) {\n" +
                    "\t\tthis.httpStatus = httpStatus;\n" +
                    "\t}\n" +
                    "\n" +
                    "\tpublic Boolean getError() {\n" +
                    "\t\treturn error;\n" +
                    "\t}\n" +
                    "\n" +
                    "\tpublic void setError(Boolean error) {\n" +
                    "\t\tthis.error = error;\n" +
                    "\t}\n" +
                    "\n" +
                    "\tpublic List<String> getMessage() {\n" +
                    "\t\treturn message;\n" +
                    "\t}\n" +
                    "\n" +
                    "\tpublic void setMessage(List<String> message) {\n" +
                    "\t\tthis.message = message;\n" +
                    "\t}\n" +
                    "\n" +
                    "\tpublic LocalDateTime getDate() {\n" +
                    "\t\treturn date;\n" +
                    "\t}\n" +
                    "\n" +
                    "\tpublic void setDate(LocalDateTime date) {\n" +
                    "\t\tthis.date = date;\n" +
                    "\t}\n";
        }
        text = text + "}";
        writeFile(ROUTE + "\\" + RESPONSE_PAACKAGE.replace(".", "\\") + "\\" + className + ".java", text);
    }

    private static void generateReplyMessageList() {
        String className = "ReplyMessageList";
        String text = "package " + PROJECT_PACKAGE + "." + RESPONSE_PAACKAGE + ";\n" +
                "\n";
        if (SWAGGER) {
            text = text + "import io.swagger.v3.oas.annotations.media.Schema;\n";
        }
        if (LOMBOK) {
            text = text + "import lombok.Getter;\n" +
                    "import lombok.Setter;\n";
        }
        text = text + "import " + PROJECT_PACKAGE + ".model.dto.BaseEntityOutputDTO;\n" +
                "import org.springframework.stereotype.Component;\n" +
                "\n" +
                "import java.util.List;\n" +
                "\n";
        if (SWAGGER) {
            text = text + "import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;\n";
        }
        text = text + "\n";
        if (LOMBOK) {
            text = text + "@Getter\n" +
                    "@Setter\n";
        }
        text = text + "@Component\n";
        if (SWAGGER) {
            text = text + "@Schema(name = \"Reply message list\", description = \"Show the api response with the entity list processed\")\n";
        }
        text = text + "public class " + className + "<BO extends BaseEntityOutputDTO> extends ReplyMessage {\n";
        if (SWAGGER) {
            text = text + "\t@Schema(description = \"Entities list created, find or updated\", defaultValue = \"{}\", requiredMode=REQUIRED)\n";
        }
        text = text + "\tprivate List<BO> response;\n";
        if (!LOMBOK) {
            text = text + "\tpublic List<BO> getResponse() {\n" +
                    "\t\treturn response;\n" +
                    "\t}\n" +
                    "\n" +
                    "\tpublic void setResponse(List<BO> response) {\n" +
                    "\t\tthis.response = response;\n" +
                    "\t}\n";
        }
        text = text + "}";
        writeFile(ROUTE + "\\" + RESPONSE_PAACKAGE.replace(".", "\\") + "\\" + className + ".java", text);
    }

    private static void generateReplyMessageSimple() {
        String className = "ReplyMessageSimple";
        String text = "package " + PROJECT_PACKAGE + "." + RESPONSE_PAACKAGE + ";\n" +
                "\n";
        if (SWAGGER) {
            text = text + "import io.swagger.v3.oas.annotations.media.Schema;\n";
        }
        if (LOMBOK) {
            text = text + "import lombok.Getter;\n" +
                    "import lombok.Setter;\n";
        }
        text = text + "import " + PROJECT_PACKAGE + ".model.dto.BaseEntityOutputDTO;\n" +
                "import org.springframework.stereotype.Component;\n" +
                "\n";
        if (SWAGGER) {
            text = text + "import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;\n";
        }
        text = text + "\n";
        if (LOMBOK) {
            text = text + "@Getter\n" +
                    "@Setter\n";
        }
        text = text + "@Component\n";
        if (SWAGGER) {
            text = text + "@Schema(name = \"Reply message simple\", description = \"Show the api response with the entity processed\")\n";
        }
        text = text + "public class " + className + "<BO extends BaseEntityOutputDTO> extends ReplyMessage {\n";
        if (SWAGGER) {
            text = text + "\t@Schema(description = \"Entity created, find or updated\", defaultValue = \"{}\", requiredMode=REQUIRED)\n";
        }
        text = text + "\tprivate BO response;\n";
        if (!LOMBOK) {
            text = text + "\tpublic BO getResponse() {\n" +
                    "\t\treturn response;\n" +
                    "\t}\n" +
                    "\n" +
                    "\tpublic void setResponse(BO response) {\n" +
                    "\t\tthis.response = response;\n" +
                    "\t}\n";
        }
        text = text + "}";
        writeFile(ROUTE + "\\" + RESPONSE_PAACKAGE.replace(".", "\\") + "\\" + className + ".java", text);
    }

    private static void generateResponse() {
        generateReplyMessage();
        generateReplyMessageList();
        generateReplyMessageSimple();
    }

    private static void generateMapperInterface() {
        String packageName = "utils.mappers";
        String className = "IMapper";
        String text = "package " + PROJECT_PACKAGE + "." + packageName + ";\n" +
                "\n" +
                "import " + PROJECT_PACKAGE + ".model.dto.BaseEntityInputDTO;\n" +
                "import " + PROJECT_PACKAGE + ".model.dto.BaseEntityOutputDTO;\n" +
                "import " + PROJECT_PACKAGE + ".model.entity.BaseEntity;\n" +
                "\n" +
                "import java.util.ArrayList;\n" +
                "import java.util.List;\n" +
                "\n" +
                "public interface " + className + "<BEC extends BaseEntityInputDTO, BEU extends BaseEntityInputDTO, BEO extends BaseEntityOutputDTO, BE extends BaseEntity> {\n" +
                "\tBE create(BEC entityDto);\n" +
                "\tBEO read(BE entity);\n" +
                "\tdefault List<BEO> readList(List<BE> entities) {\n" +
                "\t\tList<BEO> entitiesDto = new ArrayList<>();\n" +
                "\t\tfor (BE entity : entities) {\n" +
                "\t\t\tentitiesDto.add(read(entity));\n" +
                "\t\t}\n" +
                "\t\treturn entitiesDto;\n" +
                "\t}\n" +
                "\tBE update(BEU entityDto, BE entity);\n" +
                "}";
        writeFile(ROUTE + "\\" + packageName.replace(".", "\\") + "\\" + className + ".java", text);
    }

    private static void generateRepositoryInterface() {
        String packageName = "model.repository";
        String className = "IBaseRepository";
        String text = "package " + PROJECT_PACKAGE + "." + packageName + ";\n" +
                "\n" +
                "import " + PROJECT_PACKAGE + ".model.entity.BaseEntity;\n" +
                "import org.springframework.data.jpa.repository.JpaRepository;\n" +
                "import org.springframework.data.repository.NoRepositoryBean;\n" +
                "\n" +
                "@NoRepositoryBean\n" +
                "public interface " + className + "<E extends BaseEntity> extends JpaRepository<E, Integer> {\n" +
                "}";
        writeFile(ROUTE + "\\" + packageName.replace(".", "\\") + "\\" + className + ".java", text);
    }

    private static void generateServiceInterface() {
        String packageName = "service";
        String className = "IBaseService";
        String text = "package " + PROJECT_PACKAGE + "." + packageName + ";\n" +
                "\n" +
                "import " + PROJECT_PACKAGE + ".model.dto.BaseEntityInputDTO;\n" +
                "import " + PROJECT_PACKAGE + ".model.dto.BaseEntityOutputDTO;\n" +
                "import " + PROJECT_PACKAGE + "." + RESPONSE_PAACKAGE + ".ReplyMessageList;\n" +
                "import " + PROJECT_PACKAGE + "." + RESPONSE_PAACKAGE + ".ReplyMessageSimple;\n" +
                "import org.springframework.transaction.annotation.Transactional;\n" +
                "\n" +
                "public interface " + className + "<BC extends BaseEntityInputDTO, BU extends BaseEntityInputDTO, BO extends BaseEntityOutputDTO> {\n" +
                "\t@Transactional()\n" +
                "\tReplyMessageSimple<BO> getCreate(BC entityDto);\n" +
                "\n" +
                "\t@Transactional(readOnly = true)\n" +
                "\tReplyMessageList<BO> getFindAll();\n" +
                "\n" +
                "\t@Transactional(readOnly = true)\n" +
                "\tReplyMessageSimple<BO> getFindById(Integer id);\n" +
                "\n" +
                "\t@Transactional()\n" +
                "\tReplyMessageSimple<BO> getUpdate(BU entityDto);\n" +
                "\n" +
                "\t@Transactional()\n" +
                "\tReplyMessageSimple<BO> getDelete(Integer id);\n" +
                "\n" +
                "\tString getUri(String method);\n" +
                "\n" +
                "\tString getUri(String method, Integer id);\n" +
                "}";
        writeFile(ROUTE + "\\" + packageName + "\\" + className + ".java", text);
    }

    private static void generateControllerInterface() {
        String packageName = "controller";
        String className = "IBaseController";
        String text = "package " + PROJECT_PACKAGE + "." + packageName + ";\n" +
                "\n" +
                "import jakarta.validation.Valid;\n" +
                "import jakarta.validation.constraints.Min;\n" +
                "import " + PROJECT_PACKAGE + ".model.dto.BaseEntityInputDTO;\n" +
                "import " + PROJECT_PACKAGE + "." + RESPONSE_PAACKAGE + ".ReplyMessageList;\n" +
                "import " + PROJECT_PACKAGE + "." + RESPONSE_PAACKAGE + ".ReplyMessageSimple;\n" +
                "import org.springframework.http.ResponseEntity;\n" +
                "import org.springframework.web.bind.annotation.DeleteMapping;\n" +
                "import org.springframework.web.bind.annotation.GetMapping;\n" +
                "import org.springframework.web.bind.annotation.PathVariable;\n" +
                "import org.springframework.web.bind.annotation.PostMapping;\n" +
                "import org.springframework.web.bind.annotation.PutMapping;\n" +
                "import org.springframework.web.bind.annotation.RequestBody;\n" +
                "\n" +
                "import static " + PROJECT_PACKAGE + ".utils.Constants.ID_VALUE_MINIMUM;\n" +
                "import static " + PROJECT_PACKAGE + ".utils.SystemConstants.CREATE_PATH;\n" +
                "import static " + PROJECT_PACKAGE + ".utils.SystemConstants.DELETE_PATH;\n" +
                "import static " + PROJECT_PACKAGE + ".utils.SystemConstants.GET_ALL_PATH;\n" +
                "import static " + PROJECT_PACKAGE + ".utils.SystemConstants.GET_ID_PATH;\n" +
                "import static " + PROJECT_PACKAGE + ".utils.SystemConstants.ID;\n" +
                "import static " + PROJECT_PACKAGE + ".utils.SystemConstants.UPDATE_PATH;\n" +
                "import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;\n" +
                "\n" +
                "public interface " + className + "<BC extends BaseEntityInputDTO, BU extends BaseEntityInputDTO> {\n" +
                "    @PostMapping(value = CREATE_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)\n" +
                "    ResponseEntity<ReplyMessageSimple> create(@Valid @RequestBody BC entityDto);\n" +
                "\n" +
                "    @GetMapping(GET_ALL_PATH)\n" +
                "    ResponseEntity<ReplyMessageList> getAll();\n" +
                "\n" +
                "    @GetMapping(value = GET_ID_PATH + \"{\" + ID + \"}\", produces = APPLICATION_JSON_VALUE)\n" +
                "    ResponseEntity<ReplyMessageSimple> getById(@PathVariable(ID) @Min(value = 1, message = ID_VALUE_MINIMUM) Integer id);\n" +
                "\n" +
                "    @PutMapping(value = UPDATE_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)\n" +
                "    ResponseEntity<ReplyMessageSimple> update(@Valid @RequestBody BU entityDto);\n" +
                "\n" +
                "    @DeleteMapping(value = DELETE_PATH + \"{\" + ID + \"}\", produces = APPLICATION_JSON_VALUE)\n" +
                "    ResponseEntity<ReplyMessageSimple> delete(@PathVariable(ID) @Min(value = 1, message = ID_VALUE_MINIMUM) Integer id);\n" +
                "}";
        writeFile(ROUTE + "\\" + packageName + "\\" + className + ".java", text);
    }

    private static void generateInterfaces() {
        generateRepositoryInterface();
        generateMapperInterface();
        generateServiceInterface();
        generateControllerInterface();
    }

    public static void generate() {
        ROUTE = ROUTE + "\\" + ARTEFACT_ID + "\\src";
        String folders = "\\main\\resources";
        createFolder(ROUTE + folders, "static");
        createFolder(ROUTE + folders, "templates");
        generateApplicationFile(ROUTE + folders + "\\application.properties");
        ROUTE = ROUTE + "\\main\\java\\" + GROUP_ID.replace(".", "\\") + "\\" + ARTEFACT_ID;
        generateApplication();
        generateRestApiAnnotation();
        generateConfig();
        generateExceptionHandler();
        generateResponse();
        generateInterfaces();
        AnalyzerClass.loadClasses();
    }
}