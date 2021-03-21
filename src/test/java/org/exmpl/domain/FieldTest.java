package org.exmpl.domain;

import org.exmpl.logic.Field;
import org.exmpl.service.io.FieldIO;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FieldTest {

    @Test
    void readFieldFromFile() {
        Field testField = new Field();
        try {
            testField = FieldIO.readFieldFromFile("sudoku.txt");
            assertEquals(TestData.SUDOKU_INCOMPLETE, testField.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void setDigitCorrect() {
        Field testField = new Field();
        assertTrue(testField.setDigit(1, 0, 0));
    }

    @Test
    void setDigitSamePosition() {
        Field testField = new Field();
        assertTrue(testField.setDigit(1, 0, 0));
        assertFalse(testField.setDigit(1, 0, 0));
    }

    @Test
    void setDigitToHigh() {
        Field testField = new Field();
        assertFalse(testField.setDigit(12, 0, 0));
    }

    @Test
    void setDigitToLow() {
        Field testField = new Field();
        assertFalse(testField.setDigit(-12, 0, 0));
    }

    @Test
    void getCellValue() {
        Field testField = new Field();
        try {
            testField = FieldIO.readFieldFromFile("sudoku.txt");
            assertEquals(7, testField.getDigit(1, 0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testClone() {
        Field testField = new Field();
        try {
            testField = FieldIO.readFieldFromFile("sudoku.txt");
            Field clonedField = testField.clone();
            assertEquals(testField, clonedField);
        } catch (IOException | CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    @Ignore
    @Test
    void testToString() {}

    @Ignore
    @Test
    void readFieldFromConsole() {}

    @Ignore
    @Test
    void testEquals() {}

    @Ignore
    @Test
    void testHashCode() {}
}