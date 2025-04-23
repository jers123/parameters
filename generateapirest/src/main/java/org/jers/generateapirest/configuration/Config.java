package org.jers.generateapirest.configuration;

import java.util.List;

import org.jers.generateapirest.configuration.enums.Database;
import org.jers.generateapirest.configuration.enums.Packaging;
import org.jers.generateapirest.dependency.DependencyData;

public class Config {
	public static final String MAVEN_EXISTS = "Versión de Maven no encontrada";
	public static final String VERSION_DEFAULT = "1.0";

	public static String SPRING_VERSION = "3.4.4";
	public static String JAVA_VERSION = "23";
	public static String MAVEN_VERSION = "3.9.9";
	public static Packaging PACKAGING = Packaging.JAR;

	public static String GROUP_ID = "org.jers";
	public static String ARTEFACT_ID = "parameters";
	public static String NAME = "Parameters";
	public static String DESCRIPTION = "This is the backend development for technical testing of Accenture";
	public static String PACKAGE_RUTE = "";

	public static List<DependencyData> DEPENDENCIES;
	
	public static String ROUTE = "C:\\Spring\\Workspace";
	public static Boolean LOMBOK = true;
	public static Boolean SECURITY = false;
	public static Boolean SWAGGER = false;

	public static Database DATABASE = Database.COSTUMIZE_DB;
	public static Database DATABASE_TEST = Database.COSTUMIZE_TEST_DB;
	public static int PORT = 8080;
	public static boolean IS_MAVEN = true;

	public static void modifyConstants() {
		GROUP_ID = GROUP_ID.toLowerCase();
		ARTEFACT_ID = ARTEFACT_ID.toLowerCase();
		PACKAGE_RUTE = GROUP_ID + "." + ARTEFACT_ID.replace("-", "");
		ROUTE = ROUTE + "\\" + ARTEFACT_ID.replace("-", "") + "\\";
	}

}