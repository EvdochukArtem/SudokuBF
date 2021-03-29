package org.exmpl;

import org.exmpl.domain.Field;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class TestData {

    public final static String SUDOKU_INCOMPLETE =
            "-----------------\n" +
            "- 7 9|8 - 3|- - -|\n" +
            "- - -|6 2 -|- 9 -|\n" +
            "4 - 1|- - 9|8 3 -|\n" +
            "-----------------\n" +
            "- 4 -|3 7 -|- 2 -|\n" +
            "9 - 8|- 6 5|7 4 -|\n" +
            "1 - -|- 4 -|- 6 -|\n" +
            "-----------------\n" +
            "8 - 3|2 - 7|4 1 -|\n" +
            "7 1 -|- 3 6|2 - -|\n" +
            "- - -|- - 1|3 - -|\n" +
            "-----------------\n";

    public final static Field FIELD_COMPLETE = getFieldFromFile("sudoku_complete.txt");

    public final static Field FIELD_INCOMPLETE = getFieldFromFile("sudoku_incomplete.txt");

    public final static String SUDOKU_COMPLETE_STRING = getFieldStringFromResource("sudoku_complete.txt");

    public final static String SUDOKU_INCOMPLETE_STRING = getFieldStringFromResource("sudoku_incomplete.txt");

    public final static String SUDOKU_INCOMPLETE_JSON
            = "{ \"field\" : \"079803000000620090401009830040370020908065740100040060803207410710036200000001300\" }";

    public final static String SUDOKU_COMPLETE_JSON
            = "{ \"field\" : \"279813654385624197461759832546378921928165743137942568853297416714536289692481375\" }";

    public final static String SUDOKU_UNSOLVABLE_JSON
            = "{ \"field\" : \"516849732307605000809700065135060907472591006968370050253186074684207500791050608\" }";

    private static Field getFieldFromFile(String resourceName) {
        String stringField = getFieldStringFromResource(resourceName).replaceAll("\n", "");
        Field field = new Field();
        for (int i = 0; i < stringField.length(); i++) {
            int x = i % Field.FIELD_LENGTH;
            int y = i / Field.FIELD_LENGTH;
            int digit = Character.getNumericValue(stringField.charAt(i));
            field.setDigit(digit, x, y);
        }
        return field;
    }

    private static String getFieldStringFromResource(String resourceName){
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        SudokuApp.class.getResourceAsStream(resourceName),
                        StandardCharsets.UTF_8))) {
            return br.lines().collect(Collectors.joining(""));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
