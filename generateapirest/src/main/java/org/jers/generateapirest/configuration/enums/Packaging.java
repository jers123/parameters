package org.jers.generateapirest.configuration.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Packaging {
    JAR("jar"),
    WAR("war");

    @Getter
    private final String type;
}
