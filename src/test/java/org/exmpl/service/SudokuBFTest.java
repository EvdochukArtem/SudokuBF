package org.exmpl.service;

import org.exmpl.TestData;
import org.exmpl.exceptions.FieldCreationFailure;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SudokuBFTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    void solveSudoku() throws FieldCreationFailure {
        SudokuBF sbf = new SudokuBF();
        String incomeJSON = TestData.SUDOKU_INCOMPLETE_JSON;
        List<String> solutions;
        solutions = sbf.solveSudoku(incomeJSON);
        assertEquals(1, solutions.size());
        assertEquals(TestData.SUDOKU_COMPLETE_STRING, solutions.get(0));
    }

    @Test
    void solveUnsolvableSudoku() throws FieldCreationFailure {
        SudokuBF sbf = new SudokuBF();
        String incomeJSON = TestData.SUDOKU_UNSOLVABLE_JSON;
        List<String> solutions;
        solutions = sbf.solveSudoku(incomeJSON);
        assertEquals(0, solutions.size());
    }
}