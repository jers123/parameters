package org.jers.generateapirest.structure;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.jers.generateapirest.structure.abstracts.IBase2;
import static org.jers.generateapirest.structure.abstracts.IBase2.STATIC;
import org.jers.generateapirest.structure.enums.Access;

@Getter
@Setter
public class MethodRuntime extends ParameterRuntime implements IBase2 {
	private Access access;
	private boolean isAbstract;
	private boolean isStatic;

	private List<ParameterRuntime> parameters;
	private String content;

	public MethodRuntime(ClassRuntime clas, String name) {
		super(clas, name);
		access = Access.PUBLIC;
		isAbstract = false;
		isStatic = false;
		parameters = new ArrayList<>();
		content = "";
	}

	public void setParameters(ParameterRuntime parameter) {
		parameters.add(parameter);
	}

	@Override
	public String generatePlantUML() {
		String plantUML = getTabs(1) + access.getPlantUML() + getReservedPlantUML(isAbstract, ABSTRACT)
				+ getReservedPlantUML(isStatic, STATIC) + getName();
		plantUML = plantUML + "(" + generatePlantUML(parameters) + ")" + " : " + getClas().getName()
				+ generateParamClass(getClas()) + generateArray();
		return plantUML;
	}

	@Override
	public String generate() {
		String text = getTabs(1) + access.getText() + " " + getReservedCode(isAbstract, isFinal(), isStatic)
				+ getClas().getName() + generateParamClass(getClas()) + generateArray() + " " + getName() + "(";
		if (!parameters.isEmpty()) {
			for (ParameterRuntime parameter : parameters) {
				text = text + parameter.generate() + ", ";
			}
			text = text.substring(0, text.length() - 2);
		}
		text = text + ") {\n" + content + getTabs(1) + "}\n";
		return text;
	}
}