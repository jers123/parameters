package org.jers.generateapirest.structure;

import org.jers.generateapirest.structure.abstracts.Base;
import lombok.Getter;
import lombok.Setter;
import org.jers.generateapirest.structure.abstracts.IBase2;

@Getter
@Setter
public class ParameterRuntime extends Base implements IBase2 {
    private ClassRuntime clas;
    
    public ParameterRuntime(ClassRuntime clas, String name) {
        super(name);
        this.clas = clas;
    }
    
    @Override
    public String generatePlantUML() {
        String plantUML = getName() + " : " + getClas().getName() + generateParamClass(getClas()) + generateArray();
        return plantUML;
    }

    @Override
    public String generate() {
        String text = getReservedCode(false, isFinal(), false) + getClas().getName() + generateParamClass(getClas())
                + generateArray() + " " + getName();
        return text;
    }
}