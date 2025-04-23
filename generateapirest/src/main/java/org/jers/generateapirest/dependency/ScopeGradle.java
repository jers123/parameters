package org.jers.generateapirest.dependency;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ScopeGradle {
	ANNOTATION_PROCESSOR("annotationProcessor"), API("api"), COMPILE_ONLY("compileOnly"),
	DEVELOPMENT_ONLY("developmentOnly"), ID("id"), IMPLEMENTATION("implementation"), RUNTIME_ONLY("runtimeOnly"),
	TEST_COMPILE_ONLY("testCompileOnly"), TEST_IMPLEMENTATION("testImplementation"),
	TEST_RUNTIME_ONLY("testRuntimeOnly");

	@Getter
	private final String scope;

	public static String[] valuesString() {
		String[] scopes = new String[ScopeGradle.values().length];
		for (int i = 0; i < scopes.length; i++) {
			scopes[i] = ScopeGradle.values()[i].getScope();
		}
		return scopes;
	}

	public static ScopeGradle compareDB(String scopeText) {
		ScopeGradle scope = ScopeGradle.ANNOTATION_PROCESSOR;
		ScopeGradle[] scopes = ScopeGradle.values();
		for (int i = 0; i < scopes.length; i++) {
			if (scopes[i].getScope().equals(scopeText)) {
				scope = scopes[i];
				i = scopes.length;
			}
		}
		return scope;
	}
}