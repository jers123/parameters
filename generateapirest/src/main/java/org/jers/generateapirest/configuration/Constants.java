package org.jers.generateapirest.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.jers.generateapirest.dependency.DependencyData;
import org.jers.generateapirest.dependency.ScopeGradle;
import org.jers.generateapirest.dependency.ScopeMaven;

public class Constants {

	private static final String PROPERTIES_FILE = "settings.properties";
	
	// PROPERTIES
	public static final String JAVA_VERSIONS = "java.versions";
	public static final String MAVEN_VERSION_EXCEPTIONS = "maven.version.exceptions";

	public static final String LOCALHOST = "localhost";

	// DATABASE LIBRARIES
	public static final DependencyData H2 = new DependencyData("H2", "com.h2database", "h2", null, ScopeMaven.TEST,
			ScopeGradle.TEST_IMPLEMENTATION, false);
	public static final DependencyData MYSQL = new DependencyData("MySQL", "com.mysql", "mysql-connector-j", null,
			ScopeMaven.RUNTIME, ScopeGradle.RUNTIME_ONLY, false);
	public static final DependencyData ORACLE = new DependencyData("Oracle", "com.oracle.database.jdbc", "ojdbc11",
			null, ScopeMaven.RUNTIME, ScopeGradle.RUNTIME_ONLY, false);
	public static final DependencyData POSTGRESQL = new DependencyData("PostgreSQL", "org.postgresql", "postgresql",
			null, ScopeMaven.RUNTIME, ScopeGradle.RUNTIME_ONLY, false);
	public static final DependencyData SQL_SERVER = new DependencyData("SQL Server", "com.microsoft.sqlserver",
			"mssql-jdbc", null, ScopeMaven.RUNTIME, ScopeGradle.RUNTIME_ONLY, false);

	public static List<DependencyData> getDependencies(boolean isMaven) {
		List<DependencyData> dependencyData;
		if (isMaven) {
			dependencyData = getMavenDependencies();
		} else {
			dependencyData = getGradleDependencies();
		}
		dependencyData.add(new DependencyData("Spring Boot Starter Parent", "org.springframework.boot",
				"spring-boot-starter-parent", null, ScopeMaven.COMPILE, ScopeGradle.IMPLEMENTATION, true));
		dependencyData.add(new DependencyData("Spring Boot Starter", "org.springframework.boot", "spring-boot-starter",
				null, ScopeMaven.COMPILE, ScopeGradle.IMPLEMENTATION, true));
		dependencyData.add(new DependencyData("Spring Boot Starter Test", "org.springframework.boot",
				"spring-boot-starter-test", null, ScopeMaven.TEST, ScopeGradle.TEST_IMPLEMENTATION, true));

		dependencyData.add(new DependencyData("Spring Web", "org.springframework.boot", "spring-boot-starter-web", null,
				ScopeMaven.COMPILE, ScopeGradle.IMPLEMENTATION, true));

		dependencyData.add(new DependencyData("Spring Configuration Processor", "org.springframework.boot",
				"spring-boot-configuration-processor", null, ScopeMaven.COMPILE, ScopeGradle.ANNOTATION_PROCESSOR,
				false));
		dependencyData.add(new DependencyData("Spring Data JPA", "org.springframework.boot",
				"spring-boot-starter-data-jpa", null, ScopeMaven.COMPILE, ScopeGradle.IMPLEMENTATION, false));
		dependencyData.add(new DependencyData("Spring DevTools", "org.springframework.boot", "spring-boot-devtools",
				null, ScopeMaven.RUNTIME, ScopeGradle.DEVELOPMENT_ONLY, false));
		dependencyData.add(new DependencyData("Spring Security", "org.springframework.boot",
				"spring-boot-starter-security", null, ScopeMaven.COMPILE, ScopeGradle.IMPLEMENTATION, false));
		dependencyData.add(new DependencyData("Spring Security Test", "org.springframework.security",
				"spring-security-test", null, ScopeMaven.TEST, ScopeGradle.TEST_IMPLEMENTATION, false));
		dependencyData.add(new DependencyData("Validation", "org.springframework.boot",
				"spring-boot-starter-validation", null, ScopeMaven.COMPILE, ScopeGradle.IMPLEMENTATION, false));
		dependencyData.add(new DependencyData("Gson", "com.google.code.gson", "gson", null, ScopeMaven.COMPILE,
				ScopeGradle.IMPLEMENTATION, false));
		dependencyData.add(new DependencyData("Lombok", "org.projectlombok", "lombok", null, ScopeMaven.PROVIDED,
				ScopeGradle.ANNOTATION_PROCESSOR, false));
		dependencyData.add(new DependencyData("Swagger", "org.springdoc", "springdoc-openapi-starter-webmvc-ui", null,
				ScopeMaven.COMPILE, ScopeGradle.IMPLEMENTATION, false));

		return dependencyData;
	}

	private static List<DependencyData> getMavenDependencies() {
		List<DependencyData> dependencyData = new ArrayList<DependencyData>();
		dependencyData.add(new DependencyData("Apache Maven", "org.apache.maven", "apache-maven", null,
				ScopeMaven.SYSTEM, ScopeGradle.ID, true));
		dependencyData.add(new DependencyData("Maven Compiler Plugin", "org.apache.maven.plugins",
				"maven-compiler-plugin", null, ScopeMaven.SYSTEM, ScopeGradle.ID, false));
		dependencyData.add(new DependencyData("Spring Boot Maven Plugin", "org.springframework.boot",
				"spring-boot-maven-plugin", null, ScopeMaven.COMPILE, ScopeGradle.IMPLEMENTATION, false));
		return dependencyData;
	}

	private static List<DependencyData> getGradleDependencies() {
		List<DependencyData> dependencyData = new ArrayList<DependencyData>();
		dependencyData.add(new DependencyData("Junit Platform Launcher", "org.junit.platform",
				"junit-platform-launcher", null, ScopeMaven.TEST, ScopeGradle.TEST_RUNTIME_ONLY, true));
		return dependencyData;
	}

	public static String[] getProperties(String property, String separator) {
		String[] values = getProperty(property).split(separator);
		for (int i = 0; i < values.length; i++) {
			values[i] = values[i].trim();
		}
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