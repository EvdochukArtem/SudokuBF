package org.exmpl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.exmpl")
public class SudokuApp {
    public static void main(String[] args) {
        SpringApplication.run(SudokuApp.class, args);
    }

}
