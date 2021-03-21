package org.exmpl.service.io;

import org.exmpl.logic.Field;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FieldIO {

    public static Field readFieldFromConsole(BufferedReader console) throws IOException {
        String line;
        Field field = new Field();
        for (int row = 0; row < Field.FIELD_LENGTH; row++) {
            System.out.println("Введи строчку #" + (row + 1));
            line = console.readLine();
            if ("ВЫХОД".equals(line)) {
                return null;
            }
            if (line.length() != Field.FIELD_LENGTH) {
                System.out.println("Число цифр не равно 9");
                row--;
            }
            else
                for (int i = 0; i < line.length(); i++) {
                    if (Character.getNumericValue(line.charAt(i)) == -1) {
                        System.out.println("Строка содержит недопустимые знаки");
                        row--;
                        break;
                    }
                    if (!field.setDigit(Character.getNumericValue(line.charAt(i)), i, row)) {
                        System.out.println("Цифра " + line.charAt(i)
                                + " не может занимать указанное место в строке по правилам судоку");
                        row--;
                        break;
                    }
                }
        }
        return field;
    }

    public static Field readFieldFromFile(String filePath) throws IOException {

        String line;
        Field field = new Field();
        BufferedReader fileReader = new BufferedReader(new FileReader(filePath));
        for (int row = 0; row < Field.FIELD_LENGTH; row++) {
            line = fileReader.readLine();
            if (line.length() != Field.FIELD_LENGTH) {
                System.out.println("Число цифр в строке " + row + " не равно 9");
                return null;
            }
            else
                for (int i = 0; i < line.length(); i++) {
                    if (Character.getNumericValue(line.charAt(i)) == -1) {
                        System.out.println("Строка " + i + " содержит недопустимые знаки");
                        return null;
                    }
                    if (!field.setDigit(Character.getNumericValue(line.charAt(i)), i, row)) {
                        System.out.println("Цифра " + line.charAt(i)
                                + " не может занимать указанное место в строке " + i + "  по правилам судоку");
                        return null;
                    }
                }
        }
        return field;
    }
}
