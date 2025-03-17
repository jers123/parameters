package org.jers.generateapirest.dependency;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ScopeGradle {
    ANNOTATION_PROCESSOR("annotationProcessor"),
    API("api"),
    COMPILE_ONLY("compileOnly"),
    DEVELOPMENT_ONLY("developmentOnly"),
    ID("id"),
    IMPLEMENTATION("implementation"),
    RUNTIME_ONLY("runtimeOnly"),
    TEST_COMPILE_ONLY("testCompileOnly"),
    TEST_IMPLEMENTATION("testImplementation"),
    TEST_RUNTIME_ONLY("testRuntimeOnly");
    
    @Getter
    private final String scope;
}