package org.jers.generateapirest.structure.abstracts;

public interface IBase2 {
    public final String ABSTRACT = "abstract";
    public final String FINAL = "final";
    public final String STATIC = "static";
    
    default public String getReservedPlantUML(boolean isActive, String reserved) {
        if(isActive) {
            return "{" + reserved + "} ";
        }
        return "";
    }
    
    default public String getReservedCode(boolean isAbstract, boolean isFinal, boolean isStatic) {
        String text = "";
        if(isStatic) {
            text = text + STATIC + " ";
        }
        if(isFinal) {
            text = text + FINAL + " ";
        }
        if(isAbstract) {
            text = text + ABSTRACT + " ";
        }
        return text;
    }
}