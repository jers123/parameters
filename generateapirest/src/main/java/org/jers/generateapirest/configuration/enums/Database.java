package org.jers.generateapirest.configuration.enums;

import static org.jers.generateapirest.configuration.Constants.LOCALHOST;

import org.jers.generateapirest.configuration.Constants;
import org.jers.generateapirest.dependency.DependencyData;

import lombok.Getter;

public enum Database {
	H2(Constants.H2, 8082, "PUBLIC", "sa", ""), MYSQL(Constants.MYSQL, 3306, null, "root", ""),
	ORACLE(Constants.ORACLE, 1521, "system", "system", "oracle"),
	POSTGRESQL(Constants.POSTGRESQL, 5432, "public", "postgres", "postgres"),
	SQL_SERVER(Constants.SQL_SERVER, 1433, "dbo", "sa", ""),
	COSTUMIZE_DB(Constants.POSTGRESQL, null, null, null, null),
	COSTUMIZE_TEST_DB(Constants.H2, null, null, null, null);

	@Getter
	private DependencyData dbLibrary;
	@Getter
	private String server;
	@Getter
	private String databaseName;
	@Getter
	private Integer port;
	@Getter
	private String schema;
	@Getter
	private String user;
	@Getter
	private String pass;
	@Getter
	private String jdbc;
	@Getter
	private String jdbcTest;
	@Getter
	private String hibernateDialect;
	@Getter
	private String driverClass;

	private Database(DependencyData dbLibrary, Integer port, String schema, String user, String pass) {
		this.dbLibrary = dbLibrary;
		this.databaseName = "";
		this.server = LOCALHOST;
		this.port = port;
		this.schema = schema;
		this.user = user;
		this.pass = pass;
		jdbc();
	}

	public void setData(DependencyData dbLibrary, String server, String databaseName, Integer port, String schema,
			String user, String pass) {
		if (this.equals(COSTUMIZE_DB) || this.equals(COSTUMIZE_TEST_DB)) {
			this.dbLibrary = dbLibrary;
			this.server = server;
			this.databaseName = databaseName;
			this.port = port;
			this.schema = schema;
			this.user = user;
			this.pass = pass;
			jdbc();
		}
	}
	
	public void setNullData() {
		if (this.equals(COSTUMIZE_DB) || this.equals(COSTUMIZE_TEST_DB)) {
			dbLibrary.setRequired(false);
			setData(dbLibrary, null, null, null, null, null, null);
		}
	}

	public void jdbc() {
		this.jdbc = "jdbc:";
		this.jdbcTest = this.jdbc;
		this.hibernateDialect = "org.hibernate.dialect.";
		this.driverClass = "";
		if (this.dbLibrary.equals(Constants.H2)) {
			this.jdbc = this.jdbc + "h2:~/" + this.databaseName
					+ ";DB_CLOSE_ON_EXIT=FALSE;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false";
			this.jdbcTest = this.jdbcTest + "h2:mem:" + this.databaseName + ";DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
			this.hibernateDialect = this.hibernateDialect + "H2";
			this.driverClass = "org.h2.";
		} else if (this.dbLibrary.equals(Constants.MYSQL)) {
			this.jdbc = this.jdbc + "mysql://" + this.server + ":" + this.port + "/" + this.databaseName
					+ "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			this.jdbc = this.jdbc + "mysql://" + this.server + ":" + this.port + "/" + this.databaseName
					+ "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			this.hibernateDialect = this.hibernateDialect + "MySQL8";
			this.driverClass = "com.mysql.cj.jdbc.";
		} else if (this.dbLibrary.equals(Constants.ORACLE)) {
			this.jdbc = this.jdbc + "oracle:thin:@//" + this.server + ":" + this.port + ":xe";
			this.jdbcTest = this.jdbc;
			this.hibernateDialect = this.hibernateDialect + "Oracle12c";
			this.driverClass = "oracle.jdbc.Oracle";
		} else if (this.dbLibrary.equals(Constants.POSTGRESQL)) {
			this.jdbc = this.jdbc + "postgresql://" + this.server + ":" + this.port + "/" + this.databaseName;
			this.jdbcTest = this.jdbc;
			this.hibernateDialect = this.hibernateDialect + "PostgreSQL";
			this.driverClass = "org.postgresql.";
		} else {
			this.jdbc = this.jdbc + "sqlserver://" + this.server + ":" + this.port + ";databaseName="
					+ this.databaseName;
			this.jdbcTest = this.jdbc;
			this.hibernateDialect = this.hibernateDialect + "SQLServer";
			this.driverClass = "com.microsoft.sqlserver.jdbc.SQLServer";
		}
		this.hibernateDialect = this.hibernateDialect + "Dialect";
		this.driverClass = this.driverClass + "Driver";
	}

	public static String[] valuesString() {
		String[] names = new String[Database.values().length - 2];
		for (int i = 0; i < names.length; i++) {
			names[i] = Database.values()[i].getDbLibrary().getName();
		}
		return names;
	}

	public static Database compareDB(String name) {
		Database database = Database.COSTUMIZE_DB;
		Database[] databases = Database.values();
		for (int i = 0; i < databases.length - 2; i++) {
			if (databases[i].getDbLibrary().getName().equals(name)) {
				database = databases[i];
				break;
			}
		}
		return database;
	}
}