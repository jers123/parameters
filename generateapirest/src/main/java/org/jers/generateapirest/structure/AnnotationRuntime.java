package org.jers.generateapirest.structure;

import java.util.List;
import org.jers.generateapirest.structure.enums.Access;
import org.jers.generateapirest.structure.enums.PlantUML;
import org.jers.generateapirest.structure.enums.TypeClass;

public class AnnotationRuntime {
    private PlantUML plantUML;
    private String packageName;
    private List<String> imports;
    private List<String> importStatics;
    
    private Access access;
    
    private TypeClass typeClass;
    private ClassRuntime superclass;
    private List<ClassRuntime> implementClasses;
    
    private List<FieldRuntime> fields;
}