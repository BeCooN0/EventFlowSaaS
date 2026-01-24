package com.example.eventflowsaas.security;

import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.connections.spi.DatabaseConnectionInfo;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class ConnectionProvider implements MultiTenantConnectionProvider<String> {
    private final DataSource dataSource;

    public ConnectionProvider(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        try {
           return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Connection getConnection(String s) throws SQLException {
        Connection anyConnection = getAnyConnection();
        String valSchemaName = s.replaceAll("$[a-zAZ0-9_]", "");
        anyConnection.createStatement().execute("SET search_path TO " + valSchemaName);
        return null;
    }

    @Override
    public Connection getReadOnlyConnection(String tenantIdentifier) throws SQLException {
        return MultiTenantConnectionProvider.super.getReadOnlyConnection(tenantIdentifier);
    }

    @Override
    public void releaseConnection(String s, Connection connection) throws SQLException {
        releaseAnyConnection(connection);
        connection.createStatement().execute("SET search_path TO " + "public");
        releaseAnyConnection(connection);
    }

    @Override
    public void releaseReadOnlyConnection(String tenantIdentifier, Connection connection) throws SQLException {
        MultiTenantConnectionProvider.super.releaseReadOnlyConnection(tenantIdentifier, connection);
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean handlesConnectionSchema() {
        return MultiTenantConnectionProvider.super.handlesConnectionSchema();
    }

    @Override
    public boolean handlesConnectionReadOnly() {
        return MultiTenantConnectionProvider.super.handlesConnectionReadOnly();
    }

    @Override
    public DatabaseConnectionInfo getDatabaseConnectionInfo(Dialect dialect) {
        return MultiTenantConnectionProvider.super.getDatabaseConnectionInfo(dialect);
    }

    @Override
    public boolean isUnwrappableAs(Class<?> aClass) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return null;
    }
}
