package org.exmpl.service.db;

import lombok.AllArgsConstructor;
import org.exmpl.SudokuApp;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DBInit {
    private final JdbcTemplate source;

    private String getSQL(String dbName) throws IOException {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        SudokuApp.class.getResourceAsStream(dbName),
                        StandardCharsets.UTF_8))) {
            return br.lines().collect(Collectors.joining("\n"));
        }
    }

    public void createDB() throws IOException {
        String sql = getSQL("dbcreate.sql");
        source.execute(sql);
    }
}
