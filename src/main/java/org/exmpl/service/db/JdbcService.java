package org.exmpl.service.db;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class JdbcService {
    private final DataSource connectionPool;

    public JdbcService(DataSource connectionPool) {
        this.connectionPool = connectionPool;
    }

    @FunctionalInterface
    public interface SQLFunction<T, R> {
        R apply(T object) throws SQLException;
    }

    @FunctionalInterface
    public interface SQLConsumer<T> {
        void accept(T object) throws SQLException;
    }

    public void makeConnection(SQLConsumer<Connection> consumer) throws SQLException {
        Objects.requireNonNull(consumer);
        try (Connection connection = connectionPool.getConnection()) {
            consumer.accept(connection);
        }
    }

    public<R> R makeConnection(SQLFunction<Connection,  ? extends R> function) throws SQLException {
        Objects.requireNonNull(function);
        try (Connection connection = connectionPool.getConnection()) {
            return function.apply(connection);
        }
    }

    public void makeStatement(SQLConsumer<Statement> consumer) throws SQLException {
        Objects.requireNonNull(consumer);
        makeConnection(connection -> {
            try (Statement statement = connection.createStatement()) {
                consumer.accept(statement);
            }
        });
    }

    public <R> R makeStatement(SQLFunction<Statement,  ? extends R> function) throws SQLException {
        Objects.requireNonNull(function);
        return makeConnection(connection -> {
            try (Statement statement = connection.createStatement()) {
                return function.apply(statement);
            }
        });
    }

    public void makePreparedStatement(String request, SQLConsumer<PreparedStatement> consumer) throws SQLException {
        Objects.requireNonNull(consumer);
        makeConnection(connection -> {
            try (PreparedStatement prepStatement = connection.prepareStatement(request)) {
                consumer.accept(prepStatement);
            }
        });
    }
    public <R> R makePreparedStatement(String request, SQLFunction<PreparedStatement,  ? extends R> function) throws SQLException {
        Objects.requireNonNull(function);
        return makeConnection(connection -> {
            try(PreparedStatement prepStatement = connection.prepareStatement(request)) {
                return function.apply(prepStatement);
            }
        });
    }
}
