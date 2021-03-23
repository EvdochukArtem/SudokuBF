package org.exmpl.domain;

import org.exmpl.TestData;
import org.exmpl.logic.Field;
import org.exmpl.service.io.FieldIO;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FieldTest {

    @Rule
    public ExpectedException thrown= ExpectedException.none();

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
    void getCellValue() throws IOException {
        Field testField;
        String path = TestData.class.getResource("sudoku_incomplete.txt").toExternalForm();
        path = path.replace("file:/", "");
        testField = FieldIO.readFieldFromFile(path);
        assertEquals(7, testField.getDigit(1, 0));

    }

    @Test
    void testClone() throws  CloneNotSupportedException, IOException{
        Field testField;
        String path = TestData.class.getResource("sudoku_incomplete.txt").toExternalForm();
        path = path.replace("file:/", "");
        testField = FieldIO.readFieldFromFile(path);
        Field clonedField = testField.clone();
        assertEquals(testField, clonedField);
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