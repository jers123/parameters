package org.jers.generateapirest.structure;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import org.jers.generateapirest.structure.abstracts.Base;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.jers.generateapirest.configuration.Config;
import org.jers.generateapirest.structure.abstracts.IBase2;
import org.jers.generateapirest.structure.enums.Access;
import org.jers.generateapirest.structure.enums.PlantUML;
import org.jers.generateapirest.structure.enums.TypeClass;

@Getter
@Setter
public class ClassRuntime extends Base implements IBase2 {
	private PlantUML plantUML;
	private String packageName;
	private List<String> imports;
	private List<String> importStatics;

	private Access access;
	private boolean isAbstract;
	private boolean isStatic;

	private TypeClass typeClass;
	private String primitiveName;
	private List<ClassRuntime> parameterClasses;
	private boolean isParametrizable;
	private int parameters;
	private ClassRuntime superclass;
	private List<ClassRuntime> implementClasses;

	private List<FieldRuntime> fields;
	private List<MethodRuntime> methods;

	public ClassRuntime(String name) {
		super(name);
		plantUML = PlantUML.CLASS;
		packageName = Config.PACKAGE_RUTE;
		imports = new ArrayList<>();
		importStatics = new ArrayList<>();
		access = Access.PUBLIC;
		isAbstract = false;
		isStatic = false;
		typeClass = TypeClass.CLASS;
		parameterClasses = new ArrayList<>();
		isParametrizable = false;
		superclass = null;
		implementClasses = new ArrayList<>();
		fields = new ArrayList<>();
		methods = new ArrayList<>();
	}

	public ClassRuntime(Class clas, int parameters) {
		this(clas.getSimpleName());
		packageName = clas.getName();
		// System.out.println(packageName + getName());
		access = Access.copare(clas.getModifiers());
		isAbstract = Modifier.isAbstract(clas.getModifiers());
		isStatic = Modifier.isStatic(clas.getModifiers());
		if (parameters > 0) {
			isParametrizable = true;
		}
	}

	public void setParameterClass(ClassRuntime clas) {
		if (parameterClasses.isEmpty()) {
			isParametrizable = true;
		}
		parameterClasses.add(clas);
	}

	public void setMethod(MethodRuntime method) {
		methods.add(method);
	}

	public void setField(FieldRuntime field) {
		fields.add(field);
	}

	private String generate(List<? extends Base> elements) {
		String text = "";
		for (Base element : elements) {
			text = text + element.generate() + "\n";
		}
		return text;
	}

	private void setImportMetaList(List<? extends ParameterRuntime> metaClasses) {
		for (ParameterRuntime parameter : metaClasses) {
			setImport(parameter.getClas());
			setImportParamClass(parameter.getClas());
		}
	}

	private void setImportList(List<ClassRuntime> classes) {
		for (ClassRuntime clas : classes) {
			setImport(clas);
		}
	}

	private void setImport(ClassRuntime clas) {
		if (clas != null) {
			String text = "import " + clas.getPackageName() + ";\n";
			if (!clas.getPackageName().contains("java.lang") && !imports.contains(text)) {
				imports.add(text);
			}
		}
	}

	public void setImportParamClass(ClassRuntime clas) {
		if (clas.isParametrizable()) {
			for (ClassRuntime classR : clas.getParameterClasses()) {
				setImport(classR);
				setImportParamClass(classR);
			}
		}
	}

	public void autoSetImports() {
		setImportList(parameterClasses);
		setImport(superclass);
		setImportList(implementClasses);
		setImportMetaList(fields);
		setImportMetaList(methods);
	}

	private String generateList(List<String> list) {
		String text = "";
		for (String text1 : list) {
			text = text + text1;
		}
		return text;
	}

	@Override
	public String generatePlantUML() {
		String plantUML = typeClass.getText() + " " + getName() + generateParamClass(this) + " {\n";
		plantUML = plantUML + generatePlantUML(fields);
		plantUML = plantUML + generatePlantUML(methods);
		plantUML = plantUML + "}";
		return plantUML;
	}

	@Override
	public String generate() {
		autoSetImports();
		String text = "package " + packageName + ";\n\n";
		text = text + generateList(imports) + "\n";
		text = text + generateList(importStatics) + "\n";
		text = text + access.getText() + " " + getReservedCode(false, isFinal(), isStatic) + typeClass.getText() + " "
				+ getName() + generateParamClass(this) + " ";
		if (superclass != null) {
			text = text + "extends " + superclass.getName() + " ";
		}
		if (!implementClasses.isEmpty()) {
			for (ClassRuntime clas : implementClasses) {
				text = text + clas.getName() + generateParamClass(clas) + ", ";
			}
			text = text.substring(0, text.length() - 2);
			text = text + " ";
		}
		text = text + "{\n";
		text = text + generate(fields);
		text = text + generate(methods);
		text = text + "}";
		return text;
	}
}