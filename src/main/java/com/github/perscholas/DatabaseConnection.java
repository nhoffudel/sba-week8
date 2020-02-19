package com.github.perscholas;

import com.github.perscholas.utils.ConnectionBuilder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by leon on 2/18/2020.
 */
public enum DatabaseConnection {
    MANAGEMENT_SYSTEM(new ConnectionBuilder()
            .setUser("root")
            .setPassword("toor") //necessary to run on my machine
            .setPort(3306)
            .setDatabaseVendor("mariadb")
            .setHost("127.0.0.1"));

    private final ConnectionBuilder connectionBuilder;

    DatabaseConnection(ConnectionBuilder connectionBuilder) {
        this.connectionBuilder = connectionBuilder;
    }

    public Connection getDatabaseConnection() {
        return connectionBuilder
                .setDatabaseName(name().toLowerCase())
                .build();
    }

    public Connection getDatabaseEngineConnection() {
        return connectionBuilder.build();
    }

    public void drop() {
        try {
            getDatabaseEngineConnection()
                    .prepareStatement("DROP DATABASE IF EXISTS " + name().toLowerCase() + ";")
                    .execute();
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    public void create() {
        try {
            getDatabaseEngineConnection()
                    .prepareStatement("CREATE DATABASE IF NOT EXISTS " + name().toLowerCase() + ";")
                    .execute();
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    public void use() {
        try {
            getDatabaseEngineConnection()
                    .prepareStatement("USE " + name().toLowerCase() + ";")
                    .execute();
        } catch (SQLException e) {
            throw new Error(e);
        }
    }


    public void executeStatement(String sqlStatement) {
        try {
            getScrollableStatement().execute(sqlStatement);
        } catch (SQLException e) {
            String errorMessage = String.format("Failed to execute statement `%s`", sqlStatement);
            throw new Error(errorMessage, e);
        }
    }

    public void executeUpdate(String sqlStatement) {
        try {
            getScrollableStatement().executeUpdate(sqlStatement);
        } catch (SQLException e) {
            String errorMessage = String.format("Failed to execute update `%s`", sqlStatement);
            throw new Error(errorMessage, e);
        }
    }

    public ResultSet executeQuery(String sqlQuery) {
        try {
            return getScrollableStatement().executeQuery(sqlQuery);
        } catch (SQLException e) {
            String errorMessage = String.format("Failed to execute query `%s`", sqlQuery);
            throw new Error(errorMessage, e);
        }
    }

    private Statement getScrollableStatement() {
        int resultSetType = ResultSet.TYPE_SCROLL_INSENSITIVE;
        int resultSetConcurrency = ResultSet.CONCUR_READ_ONLY;
        try {
            return getDatabaseConnection().createStatement(resultSetType, resultSetConcurrency);
        } catch (SQLException e) {
            throw new Error(e);
        }
    }
}
