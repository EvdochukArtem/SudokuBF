package org.exmpl.service.io;

import org.exmpl.TestData;
import org.exmpl.logic.Field;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FieldIOTest {

    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Test
    void readFieldFromFileCorrect() throws IOException {
        String path = TestData.class.getResource("sudoku_incomplete.txt").toExternalForm();
        path = path.replace("file:/", "");
        Field field = FieldIO.readFieldFromFile(path);
        assertEquals(TestData.SUDOKU_INCOMPLETE, field.toString());
    }

    @Test
    void readFieldFromFileIncorrect() throws IOException {
        String path = TestData.class.getResource("sudoku_complete.txt").toExternalForm();
        path = path.replace("file:/", "");
        Field field = FieldIO.readFieldFromFile(path);
        assertNotEquals(field.toString(), TestData.SUDOKU_INCOMPLETE);
    }

    @Test
    void readFieldFromConsole() throws IOException {
        BufferedReader consoleMock = Mockito.mock(BufferedReader.class);
        when(consoleMock.readLine()).thenReturn("079803000",
                "000620090",
                "401009830",
                "040370020",
                "908065740",
                "100040060",
                "803207410",
                "710036200",
                "000001300");
        Field field = FieldIO.readFieldFromConsole(consoleMock);
        assertEquals(TestData.SUDOKU_INCOMPLETE, field.toString());
        verify(consoleMock, times(9)).readLine();
    }
}