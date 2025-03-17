package org.jers.generator.java;

import org.jers.generator.ConvertText;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.jers.generator.Config.*;
import static org.jers.generator.GenerateFile.writeFile;

public class GenerateSystemConstants {
    private static List<String> PATHS = new ArrayList<>();
    private static List<String> HEADERS = new ArrayList<>();
    private static List<String> SUBPATHS = new ArrayList<>();
    private static List<String> DATABASES = new ArrayList<>();
    private static Map<String, List<String>> TABLES = new TreeMap<>();
    private static String ip;

    private static String setImports() {
        String text = "import " + PROJECT_PACKAGE + ".utils.response.ReplyMessageList;\n" +
                "import " + PROJECT_PACKAGE + ".utils.response.ReplyMessageSimple;\n" +
                "import org.springframework.http.ResponseEntity;\n" +
                "\n" +
                "import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;\n";
        return text;
    }

    private static void setPaths() {
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            ip = "192.168.1.9";
            e.printStackTrace();
        }
        PATHS.add("\tpublic static final String LOCAL_ORIGIN_PATH = \"http://localhost:" + PORT + "\";\n");
        PATHS.add("\tpublic static final String PUBLIC_ORIGIN_PATH = \"http://" + ip + ":" + PORT + "\";\n");
        PATHS.add("\tpublic static final String LOCAL_ORIGIN_PATH2 = \"http://localhost:" + (PORT + 1) + "\";\n");
        PATHS.add("\tpublic static final String PUBLIC_ORIGIN_PATH2 = \"http://" + ip + ":" + (PORT + 1) + "\";\n");
        PATHS.add("\tpublic static final String " + API_PATH + " = \"/" + ConvertText.lowwerCase(ARTEFACT_ID) + "\";\n");
        PATHS.add("\tpublic static final String CREATE_PATH = \"/create\";\n");
        PATHS.add("\tpublic static final String DELETE_PATH = \"/delete/\";\n");
        PATHS.add("\tpublic static final String GET_ALL_PATH = \"/get-all\";\n");
        PATHS.add("\tpublic static final String GET_ID_PATH = \"/get-id/\";\n");
        PATHS.add("\tpublic static final String UPDATE_PATH = \"/update\";\n");
    }

    private static void setHeaders() {
        HEADERS.add("\tpublic static final String ACCEPT = \"Accept\";\n");
        HEADERS.add("\tpublic static final String AUTHORIZATION = \"Authorization\";\n");
        HEADERS.add("\tpublic static final String CONTENT_TYPE = \"Content-Type\";\n");
        HEADERS.add("\tpublic static final String LOCATION = \"Location\";\n");
    }

    public static void setSubPaths(String attribute, String value) {
        String subPath = "\tpublic static final String " + attribute + " = \"" + value + "\";\n";
        boolean result = false;
        for(String s : SUBPATHS) {
            if(s.equals(subPath)) {
                result = true;
                break;
            }
        }
        if (!result) {
            SUBPATHS.add(subPath);
        }
        SUBPATHS.sort(String::compareTo);
    }

    private static void setDatabase() {
        DATABASES.add("\tpublic static final String DATABASE = \"" + DATABASE + "\";\n");
        DATABASES.add("\tpublic static final String SCHEMA = \"" + SCHEMA + "\";\n");
    }

    public static void setTables(String key, String type, String attribute, String value) {
        key = ConvertText.upperCase(key);
        String table = "\tpublic static final " + type + " " + attribute + " = " + value + ";\n";
        List<String> list = TABLES.get(key);
        if (list == null) {
            list = new ArrayList<>();
            TABLES.put(key, list);
        }
        boolean result = false;
        for(String s : list) {
            if(s.equals(table)) {
                result = true;
                break;
            }
        }
        if (!result) {
            list.add(table);
        }
    }

    private static String generateAnswer(String clas) {
        String text ="\tpublic static ResponseEntity<ReplyMessage" + clas + "> answer" + clas + "(ReplyMessage" + clas + " replyMessage) {\n" +
                "\t\treturn ResponseEntity\n" +
                "\t\t\t\t.status(replyMessage.getHttpStatus())\n" +
                "\t\t\t\t.header(LOCATION, replyMessage.getUri())\n" +
                "\t\t\t\t.header(ACCEPT, APPLICATION_JSON_VALUE)\n" +
                "\t\t\t\t.body(replyMessage);\n" +
                "\t}\n\n";
        return text;
    }

    private static String printList(String annotation, List<String> list) {
        String text = "\t// " + annotation + "\n";
        for (String s : list) {
            text = text + s;
        }
        text = text + "\n";
        return text;
    }

    private static String printMap() {
        String text = "";
        for (Map.Entry<String, List<String>> entry : TABLES.entrySet()) {
            text = text + printList(entry.getKey(), entry.getValue());
            text = text + "\n";
        }
        return text;
    }

    public static void generateClass() {
        String className = "SystemConstants";
        setPaths();
        setHeaders();
        setDatabase();

        String text = "package " + PROJECT_PACKAGE + ".utils;\n" +
                "\n" +
                setImports() +
                "\n" +
                "public class " + className + " {\n\n" +
                printList("PATHS", PATHS) +
                printList("HEADERS", HEADERS) +
                printList("SUBPATHS", SUBPATHS) +
                printList("DATABASE", DATABASES) +
                printMap() +
                generateAnswer("Simple") +
                generateAnswer("List") +
                "}";
        writeFile(ROUTE + "\\utils\\" + className + ".java", text);
    }
}