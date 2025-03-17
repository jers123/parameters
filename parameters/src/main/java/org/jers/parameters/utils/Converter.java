package org.jers.parameters.utils;

public class Converter {

    public static String rewrite(Class cls) {
        String[] packageClass = cls.getSimpleName().split("");
        String name = "";
        for(int i = 0; i < packageClass.length; i++) {
            if(packageClass[i].equals(packageClass[i].toUpperCase())) {
                if(i > 0) {
                    name = name + "_";
                }
                name = name + packageClass[i].toLowerCase();
            } else{
                name = name + packageClass[i];
            }
        }
        return name;
    }
}