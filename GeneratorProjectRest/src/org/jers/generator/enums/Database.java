package org.jers.generator.enums;

public enum Database {
    H2,
    MySQL,
    Oracle_DB,
    PostgreSQL,
    SQL_Server;

    private String name;
    private String groupId;
    private String artefactId;
    private String jdbc;
    private String port;
    private String user;
    private String pass;
}