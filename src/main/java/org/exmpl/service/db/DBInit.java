package org.exmpl.service.db;

import lombok.AllArgsConstructor;
import org.exmpl.SudokuApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.stream.Collectors;

@AllArgsConstructor
public class DBInit {
    final JdbcService source;

    public void create() throws SQLException, IOException {
        String sql = getSQL("dbcreate.sql");
        source.makeStatement(statement -> {
            statement.execute(sql);
        });
    }

    private String getSQL(String name) throws IOException {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        SudokuApp.class.getResourceAsStream(name),
                        StandardCharsets.UTF_8))) {
            return br.lines().collect(Collectors.joining("\n"));
        }
    }
}
