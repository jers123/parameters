package org.jers.generateapirest.configuration.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DBLibrary {
    H2("com.h2database", "h2"),
    MYSQL("com.mysql", "mysql-connector-j"),
    ORACLE("com.oracle.database.jdbc", "ojdbc11"),
    POSTGRESQL("org.postgresql", "postgresql"),
    SQL_SERVER("com.microsoft.sqlserver", "mssql-jdbc");

    @Getter
    private final String groupId;

    @Getter
    private final String artefactId;
}
