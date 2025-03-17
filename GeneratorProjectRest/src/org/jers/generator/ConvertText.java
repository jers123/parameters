package org.jers.generator;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConvertText {
    public static String upperFirst(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    public static String upperText(String text, String separator, boolean space) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        String[] separators = text.split(separator);
        if (separators.length == 1) {
            return upperFirst(text);
        }
        text = "";
        for (int i = 0; i < separators.length; i++) {
            if (space) {
                text = text + " ";
            }
            text = text + upperFirst(separators[i]);
        }
        if (space) {
            text = text.substring(1);
        }
        return text;
    }

    public static String upperCase(String text) {
        List<String> words = new ArrayList<>();

        String[] parts = text.split("-");
        for (String part : parts) {
            Matcher matcher = Pattern.compile("[A-Z]?[a-z]+|[A-Z]+(?![a-z])").matcher(part);
            while (matcher.find()) {
                words.add(matcher.group().toUpperCase());
            }
        }
        return String.join("_", words);
    }

    public static String lowwerCase(String text) {
        text = text.replace("-", "");
        return text.toLowerCase();
    }

    public static String getValues(String fieldType, boolean isText) {
        String test = "";
        if(fieldType.equals("String")) {
            if (isText) {
                test = test + "\"ABC\"";
            } else {
                test = test + "ABC";
            }
        } else if(fieldType.equals("Integer") || fieldType.equals("int")) {
            test = test + "1";
        } else if(fieldType.equals("Double") || fieldType.equals("double")) {
            test = test + "1.0";
        } else if(fieldType.equals("Boolean") || fieldType.equals("boolean")) {
            test = test + "true";
        } else if(fieldType.equals("Long") || fieldType.equals("long")) {
            test = test + "1L";
        } else if(fieldType.equals("Float") || fieldType.equals("float")) {
            test = test + "1F";
        } else if(fieldType.equals("HttpStatus")) {
            test = test + "HttpStatus.OK";
        } else if(fieldType.equals("LocalDate")) {
            if (isText) {
                test = test + "LocalDate.now()";
            } else {
                test = test + LocalDate.now();
            }
        } else if(fieldType.equals("LocalTime")) {
            if (isText) {
                test = test + "LocalTime.now()";
            } else {
                test = test + LocalTime.now();
            }
        } else if(fieldType.equals("LocalDateTime")) {
            if (isText) {
                test = test + "LocalDateTime.now()";
            } else {
                test = test + "" + LocalDateTime.now();
            }
        } /*else if(field.getType().isEnum()) {
            Enum<?>[] values = (Enum<?>[]) field.getType().getEnumConstants();
            test = test + cls + "." + values[0].name();
        }*/ else if(fieldType.equals("Object")) {
            test = test + "new Object()";
        } else  {
            test = test + "new " + fieldType + "()";
        }
        return test;
    }
}