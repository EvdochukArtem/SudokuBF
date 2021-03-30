package org.exmpl;

import org.exmpl.service.db.DBInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication(scanBasePackages = "org.exmpl")
public class SudokuApp {
    @Autowired
    private DBInit dbInit;

    @PostConstruct
    void init() throws IOException {
        dbInit.createDB();
    }

    public static void main(String[] args) {
        SpringApplication.run(SudokuApp.class, args);
    }
}
