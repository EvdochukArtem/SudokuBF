package org.exmpl.service.dao;

import org.exmpl.domain.FieldDB;
import org.exmpl.logic.Field;
import org.exmpl.service.db.JdbcService;

import lombok.AllArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
public class FieldDBDao {
    private final JdbcService source;

    public void saveFieldToDB(FieldDB field) throws SQLException {
        source.makePreparedStatement("INSERT INTO sudoku_fields(id, field) VALUES (?, ?)", insertField -> {
            insertField.setInt(1, field.getId());
            insertField.setString(2, field.getField());
            insertField.execute();
        });
    }

    public FieldDB getFieldFromDB(int id) throws SQLException {
        return source.makePreparedStatement(
                "SELECT field FROM sudoku_fields WHERE id = (?)",
                prepStatement -> {
                    prepStatement.setInt(1, id);
                    ResultSet result = prepStatement.executeQuery();
                    if (result.next()) {
                        return new FieldDB(id, result.getString(1));
                    } else {
                        return null;
                    }
                });
    }
}
