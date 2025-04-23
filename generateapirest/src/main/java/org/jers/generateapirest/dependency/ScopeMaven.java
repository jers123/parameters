package org.jers.generateapirest.dependency;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ScopeMaven {
	COMPILE("compile"), PROVIDED("provided"), RUNTIME("runtime"), SYSTEM("system"), TEST("test");

	@Getter
	private final String scope;

	public static String[] valuesString() {
		String[] scopes = new String[ScopeMaven.values().length];
		for (int i = 0; i < scopes.length; i++) {
			scopes[i] = ScopeMaven.values()[i].getScope();
		}
		return scopes;
	}

	public static ScopeMaven compareDB(String scopeText) {
		ScopeMaven scope = ScopeMaven.COMPILE;
		ScopeMaven[] scopes = ScopeMaven.values();
		for (int i = 0; i < scopes.length; i++) {
			if (scopes[i].getScope().equals(scopeText)) {
				scope = scopes[i];
				i = scopes.length;
			}
		}
		return scope;
	}
}