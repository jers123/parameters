package org.jers.generateapirest.structure.abstracts;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.jers.generateapirest.structure.ClassRuntime;
import org.jers.generateapirest.structure.enums.Annotation;

@Getter
@Setter
public abstract class Base implements IBase {
	private List<Annotation> annotations;
	private boolean isFinal;
	private String name;
	private boolean isArray;

	public Base(String name) {
		annotations = new ArrayList<>();
		isFinal = false;
		this.name = name;
		isArray = false;
	}

	public abstract String generatePlantUML();

	public String getTabs(int tabs) {
		return "\t".repeat(Math.max(0, tabs));
	}

	public String generateArray() {
		return isArray ? "[]" : "";
	}

	public String generateAnnotations(int tabs) {
		String result = "";
		for (Annotation annotation : this.annotations) {
			result = result + getTabs(tabs) + annotation.generate() + "\n";
		}
		return result;
	}

	public String generateParamClass(ClassRuntime clas) {
		if (!clas.isParametrizable()) {
			return "";
		} else {
			String result = "";
			for (ClassRuntime classR : clas.getParameterClasses()) {
				result = result + classR.getName() + generateParamClass(classR) + classR.generateArray() + ", ";
			}
			result = result.substring(0, result.length() - 2);
			return "<" + result + ">";
		}
	}
}