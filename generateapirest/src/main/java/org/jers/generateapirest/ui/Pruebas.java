package org.jers.generateapirest.ui;

import java.util.List;
import org.jers.generateapirest.structure.ClassRuntime;
import org.jers.generateapirest.structure.FieldRuntime;
import org.jers.generateapirest.structure.MethodRuntime;
import org.jers.generateapirest.structure.ParameterRuntime;
import org.jers.generateapirest.structure.enums.Access;
import org.jers.generateapirest.structure.enums.TypeClass;

public class Pruebas {
    
    public static void main(String[] args) {
        ClassRuntime clase0 = new ClassRuntime("void");
        ClassRuntime clase1 = new ClassRuntime(String.class, 0);
        ClassRuntime clase2 = new ClassRuntime(Integer.class, 0);
        ClassRuntime clase3 = new ClassRuntime(List.class, 1);
        clase3.setTypeClass(TypeClass.INTERFACE);
        clase3.setParameterClass(clase1);
        ClassRuntime clase4 = new ClassRuntime("Map");
        clase4.setParameterClass(clase3);
        clase4.setParameterClass(clase2);
        clase4.setField(new FieldRuntime(clase1, "type"));
        clase4.setField(new FieldRuntime(clase2, "value"));
        MethodRuntime method = new MethodRuntime(clase0, "setter");
        method.setStatic(true);
        method.setParameters(new ParameterRuntime(clase3, "list"));
        method.setParameters(new ParameterRuntime(clase1, "text"));
        clase4.setMethod(method);
        method = new MethodRuntime(clase2, "getter");
        method.setAccess(Access.PROTECTED);
        method.setAbstract(true);
        method.setArray(true);
        clase4.setMethod(method);
        
        System.out.println(clase4.generatePlantUML() + "\n" + clase4.generate());
    }
}
        