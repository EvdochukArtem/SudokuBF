package org.exmpl.service.dao;

import org.exmpl.domain.FieldDB;
import org.exmpl.logic.Field;
import org.exmpl.service.db.DBInit;
import org.exmpl.service.io.FieldIO;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.exmpl.service.db.JdbcService;

import javax.sql.DataSource;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class FieldDBDaoTest {
    private final DataSource pool = JdbcConnectionPool.create("jdbc:h2:mem:database;DB_CLOSE_DELAY=-1", "", "");
    private final JdbcService source = new JdbcService(pool);
    private final FieldDBDao dao = new FieldDBDao(source);

    @BeforeEach
    void setupDB() throws IOException, SQLException {
        new DBInit(source).create();
    }

    @AfterEach
    void tearDownDB() throws SQLException {
        source.makeStatement(statement -> {
            statement.execute("drop all objects;");
        });
    }

    private int getSudokuFieldsCount() throws SQLException {
        return source.makeStatement(statement -> {
            ResultSet resultSet = statement.executeQuery("select count(*) from sudoku_fields");
            resultSet.next();
            return resultSet.getInt(1);
        });
    }

    @Test
    void saveFieldToDB() throws SQLException, IOException {
        Field field;
        field = FieldIO.readFieldFromFile("sudoku.txt");
        FieldDB field1 = new FieldDB(1, field);
        FieldDB field2 = new FieldDB(2, field);
        dao.saveFieldToDB(field1);
        dao.saveFieldToDB(field2);
        assertEquals(2, getSudokuFieldsCount());
    }

    @Test
    void getFieldFromDB() throws SQLException, IOException  {
        Field field = new Field();
        field = FieldIO.readFieldFromFile("sudoku.txt");
        FieldDB field1 = new FieldDB(1, field);
        dao.saveFieldToDB(field1);
        FieldDB loadedField = dao.getFieldFromDB(1);
        assertNotSame(field1, loadedField);
        assertEquals(field1, loadedField);
    }
}