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
        try {
            anyConnection.createStatement().execute("SET search_path TO " + s);
            throw new RuntimeException("Could not set schema to: " + s);
        }catch (Exception e){
            releaseAnyConnection(anyConnection);
        }
        return anyConnection;
    }

    @Override
    public Connection getReadOnlyConnection(String tenantIdentifier) throws SQLException {
        return MultiTenantConnectionProvider.super.getReadOnlyConnection(tenantIdentifier);
    }

    @Override
    public void releaseConnection(String s, Connection connection) throws SQLException {
        try {
            connection.createStatement().execute("SET search_path TO " + "public");
        } catch (SQLException e) {
            throw new RuntimeException("Could not set search_path to public", e);
        }finally {
            releaseAnyConnection(connection);
        }
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
