package org.jers.generateapirest.configuration.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Packaging {
	JAR("jar"), WAR("war");

	@Getter
	private final String type;

	public static String[] valuesString() {
		String[] names = new String[Packaging.values().length];
		for (int i = 0; i < names.length; i++) {
			names[i] = Packaging.values()[i].getType();
		}
		return names;
	}

	public static Packaging compareDB(String type) {
		Packaging packaging = Packaging.JAR;
		Packaging[] packagings = Packaging.values();
		for (int i = 0; i < packagings.length; i++) {
			if (packagings[i].getType().equals(type)) {
				packaging = packagings[i];
				i = packagings.length;
			}
		}
		return packaging;
	}
}