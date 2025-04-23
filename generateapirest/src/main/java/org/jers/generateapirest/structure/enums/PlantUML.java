package org.jers.generateapirest.structure.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PlantUML {
	ABSTRACT("abstract"), ANNOTATION("anotation"), CLASS("class"), ENTITY("entity"), ENUM("enum"),
	EXCEPTION("exception"), INTERFACE("interface"), METACLASS("metaclass"), STRUCT("struct");

	private final String text;
}