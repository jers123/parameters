package org.jers.generateapirest.dependency;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ScopeMaven {
    COMPILE("compile"),
    PROVIDED("provided"),
    RUNTIME("runtime"),
    SYSTEM("system"),
    TEST("test");
    
    @Getter
    private final String scope;
}