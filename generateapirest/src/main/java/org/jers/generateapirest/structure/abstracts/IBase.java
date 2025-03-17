package org.jers.generateapirest.structure.abstracts;

import java.util.List;
import org.jers.generateapirest.structure.ParameterRuntime;

public interface IBase {
    public String generate();
    
    default public  String generatePlantUML(List<? extends Base> elements) {
        String plantUML = "";
        for(Base element : elements) {
            plantUML = plantUML + element.generatePlantUML();
            if(element.getClass() == ParameterRuntime.class) {
                plantUML = plantUML + ", ";
            } else {
                plantUML = plantUML + "\n";
            }
        }
        if(plantUML.endsWith(", ")) {
            plantUML = plantUML.substring(0, plantUML.length() - 2);
        }
        return plantUML;
    }
}