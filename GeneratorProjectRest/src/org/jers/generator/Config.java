package org.jers.generator;

import org.jers.generator.enums.Packaging;

public class Config {
    public static String SPRING_VERSION = "3.4.0";
    public static String JAVA_VERSION = "17";
    public static String MAVEN_VERSION = "3.9.9";
    public static Packaging PACKAGING = Packaging.JAR;

    public static String GROUP_ID = "org.testaccenture";
    public static String ARTEFACT_ID = "franchise";
    public static String NAME = setName();
    public static final String DESCRIPTION = "This is the backend development for technical testing of Accenture";

    public static String ROUTE = "C:\\Spring\\Workspace";
    public static final Boolean LOMBOK = true;
    public static final Boolean SECURITY = false;
    public static final Boolean SWAGGER = false;
    public static final String SWAGGER_VERSION = "2.6.0";

    public static final String DATABASE = ARTEFACT_ID + "DB";
    public static final String SCHEMA = "public";
    public static final String USER = "postgres";
    public static final String PASS = "asd";

    public static final Integer PORT = 8080;

    public static final String PROJECT_PACKAGE = GROUP_ID + "." + ConvertText.lowwerCase(ARTEFACT_ID);
    public static final String API_PATH = setPath(ARTEFACT_ID);

    private static String setName() {
        return ConvertText.upperText(ARTEFACT_ID,"-", true);
    }

    private static String setName(String name) {
        return ConvertText.upperText(name,"-", true);
    }

    public static String setPath(String path) {
        return ConvertText.upperCase(path) + "_PATH";
    }
}