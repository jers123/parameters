package org.jers.generateapirest.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.jers.generateapirest.dependency.DependencyData;
import org.jers.generateapirest.dependency.ScopeGradle;
import org.jers.generateapirest.dependency.ScopeMaven;

public class Constants {
    
    private static final String PROPERTIES_FILE = "settings.properties";
    //PROPERTIES
    public static final String JAVA_VERSIONS = "java.versions";
    
    public static final String LOCALHOST = "localhost";

    public static final DependencyData APACHE_MAVEN = new DependencyData("org.apache.maven", "apache-maven");
    public static final DependencyData MAVEN_COMPILER_PLUGINS = new DependencyData("org.apache.maven.plugins", "maven-compiler-plugin");
    
    //spring LIBRARIES
    public static final DependencyData SPRING_BOOT_CONFIGURATION_PROCESSOR = new DependencyData("org.springframework.boot", "spring-boot-devtools", null, ScopeMaven.RUNTIME, ScopeGradle.TEST_IMPLEMENTATION);
    public static final DependencyData SPRING_BOOT_DEVTOOLS = new DependencyData("org.springframework.boot", "spring-boot-devtools", null, ScopeMaven.RUNTIME, ScopeGradle.TEST_IMPLEMENTATION);
    public static final DependencyData SPRING_BOOT_MAVEN_PLUGIN = new DependencyData("org.springframework.boot", "spring-boot-starter-test", null, ScopeMaven.TEST, ScopeGradle.TEST_IMPLEMENTATION);
    public static final DependencyData SPRING_BOOT_STARTER = new DependencyData("org.springframework.boot", "spring-boot-starter", null, ScopeMaven.COMPILE, ScopeGradle.IMPLEMENTATION);
    public static final DependencyData SPRING_BOOT_STARTER_DATA_JPA = new DependencyData("org.springframework.boot", "spring-boot-starter-test", null, ScopeMaven.TEST, ScopeGradle.TEST_IMPLEMENTATION);
    public static final DependencyData SPRING_BOOT_STARTER_PARENT = new DependencyData("org.springframework.boot", "spring-boot-starter-parent", null, ScopeMaven.COMPILE, ScopeGradle.IMPLEMENTATION);
    public static final DependencyData SPRING_BOOT_STARTER_SECURITY = new DependencyData("org.springframework.boot", "spring-boot-starter-test", null, ScopeMaven.TEST, ScopeGradle.TEST_IMPLEMENTATION);
    public static final DependencyData SPRING_BOOT_STARTER_TEST = new DependencyData("org.springframework.boot", "spring-boot-starter-test", null, ScopeMaven.TEST, ScopeGradle.TEST_IMPLEMENTATION);
    public static final DependencyData SPRING_BOOT_STARTER_VALIDATION = new DependencyData("org.springframework.boot", "spring-boot-starter-test", null, ScopeMaven.TEST, ScopeGradle.TEST_IMPLEMENTATION);
    public static final DependencyData SPRING_BOOT_STARTER_WEB = new DependencyData("org.springframework.boot", "spring-boot-starter-test", null, ScopeMaven.TEST, ScopeGradle.TEST_IMPLEMENTATION);
    public static final DependencyData SPRING_SECURIRTY_TEST = new DependencyData("org.springframework.boot", "spring-boot-starter-test", null, ScopeMaven.TEST, ScopeGradle.TEST_IMPLEMENTATION);
    
    //DATABASE LIBRARIES
    public static final DependencyData H2 = new DependencyData("com.h2database", "h2", null, ScopeMaven.TEST, ScopeGradle.TEST_IMPLEMENTATION);
    public static final DependencyData MYSQL = new DependencyData("com.mysql", "mysql-connector-j", null, ScopeMaven.RUNTIME, ScopeGradle.RUNTIME_ONLY);
    public static final DependencyData ORACLE = new DependencyData("com.oracle.database.jdbc", "ojdbc11", null, ScopeMaven.RUNTIME, ScopeGradle.RUNTIME_ONLY);
    public static final DependencyData POSTGRESQL = new DependencyData("org.postgresql", "postgresql", null, ScopeMaven.RUNTIME, ScopeGradle.RUNTIME_ONLY);
    public static final DependencyData SQL_SERVER = new DependencyData("com.microsoft.sqlserver", "mssql-jdbc", null, ScopeMaven.RUNTIME, ScopeGradle.RUNTIME_ONLY);
    
    //OTHERS LIBRARIES
    public static final DependencyData JUNIT_PLATFORM_LAUNCHER = new DependencyData("org.junit.platform", "junit-platform-launcher", null, ScopeMaven.TEST, ScopeGradle.TEST_RUNTIME_ONLY);
    public static final DependencyData LOMBOK = new DependencyData("org.projectlombok", "lombok", null, ScopeMaven.COMPILE, ScopeGradle.ANNOTATION_PROCESSOR);
    public static final DependencyData SPRINGDOC_OPENAPI_STARTER_WEBMVC_UI = new DependencyData("org.springdoc", "springdoc-openapi-starter-webmvc-ui", null, ScopeMaven.COMPILE, ScopeGradle.IMPLEMENTATION);
    
    public static String[] getProperties(String property, String separator) {
        String[] values = getProperty(property).split(separator);
        return values;
    }
    
    public static String getProperty(String property) {
        Properties properties = new Properties();
        try (InputStream input = Constants.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                System.out.println("Archivo no encontrado: " + PROPERTIES_FILE);
                return null;
            }
            properties.load(input);
            return properties.getProperty(property);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}