package org.exmpl;

import org.exmpl.logic.SudokuBF;

import java.io.IOException;
import java.sql.SQLException;

public class SudokuApp {

    public static void main(String[] args) {
        SudokuBF sudokuBF = new SudokuBF();
        sudokuBF.startSudokuBF();
    }

}
