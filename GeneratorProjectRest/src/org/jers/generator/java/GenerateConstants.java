package org.jers.generator.java;

import org.jers.generator.ConvertText;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.jers.generator.Config.*;
import static org.jers.generator.Config.ROUTE;
import static org.jers.generator.GenerateFile.writeFile;

public class GenerateConstants {
    private static List<String> FIELDS = new ArrayList<>();
    private static Map<String, List<String>> TABLES = new TreeMap<>();
    private static List<String> imports = new ArrayList<>();
    private static List<String> importStatics = new ArrayList<>();

    private static void setFields() {
        FIELDS.add("\tpublic static final String ID_VALUE_MINIMUM = \"El ID ingresado debe ser mayor o igual a 1\";\n");
        FIELDS.add("\tpublic static final String NO_CONTENT = \"No hay registros\";\n");
        FIELDS.add("\tpublic static final String NO_CONTENT_ID = \"No hay registros con el id = \";\n");
        FIELDS.add("\tpublic static final String YES_CONTENT = \"Si hay registros\";\n");
        FIELDS.add("\n\t// ERRORS\n\tpublic static final String HTTP_MESSAGE1 = \"Problema de método HTTP, se esperaba la petición por medio del método \";\n");
        FIELDS.add("\tpublic static final String HTTP_MESSAGE2 = \" pero se solicitó por medio del metodo \";\n\n");
    }

    public static void addImports(boolean isStatic, String importText) {
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

    public static void setField(String key, String attribute, String value) {
        key = ConvertText.upperCase(key);
        String field = "\tpublic static final String " + attribute + " = " + value + ";\n";
        List<String> list = TABLES.get(key);
        if (list == null) {
            list = new ArrayList<>();
            TABLES.put(key, list);
        }
        boolean result = false;
        for(String s : list) {
            if(s.equals(field)) {
                result = true;
                break;
            }
        }
        if (!result) {
            list.add(field);
        }
    }

    private static String printList(String annotation, List<String> list) {
        String text = "\t// " + annotation + "\n";
        list.sort(String::compareTo);
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
        String className = "Constants";
        setFields();
        imports.sort(String::compareTo);
        importStatics.sort(String::compareTo);
        String text = "package " + PROJECT_PACKAGE + ".utils;\n" +
                "\n" +
                //printList(imports) +
                printList("", importStatics) +
                "public class " + className + " {\n\n" +
                printList("", FIELDS) +
                printMap() +
                "}";
        writeFile(ROUTE + "\\utils\\" + className + ".java", text);
    }
}