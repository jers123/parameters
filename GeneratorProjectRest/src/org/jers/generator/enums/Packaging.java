package org.jers.generator.enums;

public enum Packaging {
    JAR("jar"),
    WAR("war");

    private final String type;

    private Packaging(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}