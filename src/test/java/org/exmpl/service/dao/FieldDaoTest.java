package org.exmpl.service.dao;

import org.exmpl.TestData;
import org.exmpl.domain.Field;
import org.exmpl.exceptions.FieldDBExtractionFailure;
import org.exmpl.exceptions.FieldSavingFailure;
import org.exmpl.service.db.DBInit;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = FieldDaoTestConfiguration.class)
class FieldDaoTest {
    @Rule
    ExpectedException exception = ExpectedException.none();

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

    @Autowired
    FieldDao fieldDao;

    @BeforeEach
    void createDB(@Autowired DBInit dbInit) throws IOException {
        dbInit.createDB();
    }

    @AfterEach
    void clearDB() {
        jdbcTemplate.execute("drop all objects;");
    }

    int getFieldsCount() {
        return jdbcTemplate.query("SELECT COUNT(*) FROM sudoku_fields",
                rs -> {
                    rs.next();
                    return rs.getInt(1);
                });
    }

    @Test
    void saveField() throws FieldSavingFailure {
        assertEquals(0, getFieldsCount());
        fieldDao.saveField(TestData.SUDOKU_INCOMPLETE_STRING);
        assertEquals(1, getFieldsCount());
    }

    @Test
    void getRandomField() throws FieldSavingFailure, FieldDBExtractionFailure {
        fieldDao.saveField(TestData.SUDOKU_INCOMPLETE_STRING);
        Field dbField = fieldDao.getRandomField();
        Assertions.assertNotSame(TestData.FIELD_INCOMPLETE, dbField);
        assertEquals(TestData.FIELD_INCOMPLETE, dbField);
    }

}