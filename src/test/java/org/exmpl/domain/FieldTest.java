package org.exmpl.domain;

import org.exmpl.TestData;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import static org.junit.jupiter.api.Assertions.*;

class FieldTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
        Field testField;
        testField = TestData.FIELD_INCOMPLETE;
        assertEquals(7, testField.getDigit(1, 0));
    }

    @Test
    void testClone() throws  CloneNotSupportedException {
        Field testField;
        testField = TestData.FIELD_INCOMPLETE;
        Field clonedField = testField.clone();
        assertEquals(testField, clonedField);
    }

    @Test
    void testToString() {
        Field field = TestData.FIELD_INCOMPLETE;
        assertEquals(field.toString(), TestData.SUDOKU_INCOMPLETE_STRING);
    }

    @Ignore
    @Test
    void testEquals() {}

    @Ignore
    @Test
    void testHashCode() {}
}