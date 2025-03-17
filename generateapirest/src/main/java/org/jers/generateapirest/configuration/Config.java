package org.jers.generateapirest.configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.jers.generateapirest.configuration.enums.Packaging;
import org.jers.generateapirest.dependency.maven.OnlineVersionDetector;

public class Config {
    public static final String MAVEN_EXISTS = "Versión de Maven no encontrada";
    public static final String MAVEN_VERSION_DEFAULT = "3.9.9";
        
    public static String SPRING_VERSION = "3.4.3";
    public static String JAVA_VERSION = "23";
    public static String MAVEN_VERSION = "3.9.9";
    public static Packaging PACKAGING = Packaging.JAR;

    public static String GROUP_ID = "org.jers";
    public static String ARTEFACT_ID = "parameters";
    public static String NAME = "Parameters";
    public static String DESCRIPTION = "This is the backend development for technical testing of Accenture";
    public static String PACKAGE_RUTE = "";
    
    //public Database database1 = Database.

    public static String ROUTE = "C:\\Spring\\Workspace";
    public static Boolean LOMBOK = true;
    public static Boolean SECURITY = false;
    public static Boolean SWAGGER = false;
    
    public static String DATABASE = "";
    public static String SCHEMA = "public";
    public static String USER = "postgres";
    public static String PASS = "asd";

    public static Integer PORT = 8080;

    public static void modifyConstants() {
        GROUP_ID = GROUP_ID.toLowerCase();
        ARTEFACT_ID = ARTEFACT_ID.toLowerCase();
        PACKAGE_RUTE = GROUP_ID + "." + ARTEFACT_ID.replace("-", "");
        if(SWAGGER) {
            OnlineVersionDetector.getVersion(Constants.SPRINGDOC_OPENAPI_STARTER_WEBMVC_UI);
        }
    }
    
    
}