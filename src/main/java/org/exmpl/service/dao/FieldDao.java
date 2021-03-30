package org.exmpl.service.dao;

import lombok.AllArgsConstructor;
import org.exmpl.domain.Field;
import org.exmpl.exceptions.FieldCreationFailure;
import org.exmpl.exceptions.FieldDBExtractionFailure;
import org.exmpl.exceptions.FieldSavingFailure;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ThreadLocalRandom;

@Repository
@AllArgsConstructor
public class FieldDao {
    private final JdbcTemplate jdbcTemplate;


    private int getFieldsCount() {
        return jdbcTemplate.query("SELECT COUNT(*) FROM sudoku_fields",
                rs -> {
                    rs.next();
                    return rs.getInt(1);
                });
    }

    public Field getRandomField() throws FieldDBExtractionFailure {
        int count = getFieldsCount();
        if (count == 0)
            throw new FieldDBExtractionFailure();
        int rnd = (count == 1) ? 1 : ThreadLocalRandom.current().nextInt(1, getFieldsCount());
        return jdbcTemplate.query(
                "SELECT field FROM sudoku_fields WHERE id = ?",
                ps -> ps.setInt(1, rnd),
                rs -> {
                    rs.next();
                    Field request = null;
                    String s = rs.getString(1);
                    try {
                        request = Field.getFieldFromJSON(
                                "{ \"field\" : \"" +
                                rs.getString(1) +
                                "\"}");
                    } catch (FieldCreationFailure e) {
                        e.printStackTrace();
                    }
                    return request;
                });
    }

    public void saveField(Field field) throws FieldSavingFailure {
        int n = getFieldsCount() + 1;
        String fieldString = field.toJSONString().replaceAll("\\D","");
        if (fieldString.length() != 81)
            throw new FieldSavingFailure();
        jdbcTemplate.execute(
                "INSERT INTO sudoku_fields(id, field) values (?, ?)",
                (PreparedStatementCallback<?>) ps -> {
                    ps.setInt(1, n);
                    ps.setString(2, fieldString);
                    ps.execute();
                    return null;
                }
        );
    }
}
