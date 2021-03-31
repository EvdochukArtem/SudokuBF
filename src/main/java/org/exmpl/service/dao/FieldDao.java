package org.exmpl.service.dao;

import lombok.AllArgsConstructor;
import org.exmpl.domain.Field;
import org.exmpl.exceptions.FieldCreationFailure;
import org.exmpl.exceptions.FieldDBExtractionFailure;
import org.exmpl.exceptions.FieldSavingFailure;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.concurrent.ThreadLocalRandom;

@Repository
@AllArgsConstructor
public class FieldDao {
    private final JdbcTemplate jdbcTemplate;

    //TODO: Need to ignore while testing
    @PostConstruct
    private void fillDB() throws FieldSavingFailure {
        String [] fields = {
                "004020803080931400001054072360000024000082000702346500000410230000068709059070600",
                "073504000008070000954081020036000004897603500001290000409015207715002009080009140",
                "030021007800000519017050026001760000300090640096800200704006052609087004053240060",
                "079803000000620090401009830040370020908065740100040060803207410710036200000001300"
        };
        for (String field : fields)
            saveField(field);
    }

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

    public void saveField(String fieldString) throws FieldSavingFailure {
        int n = getFieldsCount() + 1;
        if (fieldString.length() != 81)
            throw new FieldSavingFailure();
        //TODO: Need to exclude not unique fields
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
