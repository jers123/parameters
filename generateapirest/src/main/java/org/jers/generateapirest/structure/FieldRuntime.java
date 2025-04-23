package org.jers.generateapirest.structure;

import lombok.Getter;
import lombok.Setter;
import org.jers.generateapirest.structure.abstracts.IBase2;
import org.jers.generateapirest.structure.enums.Access;

@Getter
@Setter
public class FieldRuntime extends ParameterRuntime implements IBase2 {
	private Access access;
	private boolean isStatic;

	private String value;

	public FieldRuntime(ClassRuntime clas, String name) {
		super(clas, name);
		access = Access.PUBLIC;
		isStatic = false;
		value = "";
	}

	@Override
	public String generatePlantUML() {
		String plantUML = getTabs(1) + access.getPlantUML() + getReservedPlantUML(isStatic, STATIC);
		plantUML = plantUML + getName() + " : " + getClas().getName() + generateParamClass(getClas()) + generateArray();
		return plantUML;
	}

	@Override
	public String generate() {
		String text = getTabs(1) + access.getText() + " " + getReservedCode(false, isFinal(), isStatic)
				+ getClas().getName() + generateParamClass(getClas()) + generateArray() + " " + getName();
		if (!value.equals("")) {
			text = text + " = " + value;
		}
		text = text + ";\n";
		return text;
	}
}