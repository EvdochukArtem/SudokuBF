package org.exmpl.logic;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SudokuBF {

    public void startSudokuBF() {

        Field gameField = new Field();
        final String exitCode = "exit";
        String line = "";
        System.out.println("***Sudoku BruteForce***");
        try ( BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {
            while (!line.equals(exitCode))
            {
                System.out.println("Для ввода судоку построчно напиши ВВОД\n" +
                        "Для ввода из файла напиши ФАЙЛ\n" +
                        "Для выхода из программы напиши ВЫХОД\n");
                line = consoleReader.readLine();
                switch (line) {
                    case "ВВОД": {
                        System.out.println("Вводи построчно поле для судоку. На месте пустой ячейки вводи 0");
                        if (gameField.readFieldFromConsole(consoleReader))
                            reqInsert(gameField);
                        else
                            line = exitCode;
                    }
                    break;
                    case "FILE":
                    case "ФАЙЛ": {
                        while (true) {
                            System.out.println("Введи путь до txt файла с полем судоку\n" +
                                    "Пример: C:/folder/sudoku.txt\n" +
                                    "В файле должно быть поле судоку в виде набора строк с цифрами\n" +
                                    "Каждая строка должна соответствовать строке поля судоку\n" +
                                    "На месте пустой ячейки вводи 0" +
                                    "Для выхода из программы напиши ВЫХОД\n");
                            String tmp = consoleReader.readLine();
                            if ("ВЫХОД".equals(tmp)) {
                                line = exitCode;
                                break;
                            }
                            try {
                                if (gameField.readFieldFromFile(tmp)) {
                                    reqInsert(gameField);
                                    break;
                                }
                                else
                                    System.out.println("Некорректный файл! Попробуй снова");
                            } catch (FileNotFoundException ex) {
                                System.out.println("Неверный путь! Попробуй снова");
                            }
                        }
                    }
                    break;
                    case "ВЫХОД": {
                        line = exitCode;
                        continue;
                    }
                    default:
                        System.out.println("Попробуй еще раз:");
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Выход из программы Sudoku BruteForce");
    }

    private void reqInsert(Field field) {
        int xx;
        int yy;

        try {
            Field tmpField = field.clone();
            stop_search: {
                for (int y = 0; y < Field.FIELD_LENGTH; y++)
                    for (int x = 0; x < Field.FIELD_LENGTH; x++)
                        if (field.getCellValue(x, y) == -1) {
                            xx = x;
                            yy = y;
                            break stop_search;
                        }
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.print(field.toString());
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                return;
            }
            for (int i = 1; i < 10; i++) {
                if (tmpField.setDigit(i, xx, yy)) {
                    reqInsert(tmpField);
                    tmpField = field.clone();
                }
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace(); //Знаю что так делать не хорошо, слышал что лучше оборачивать в UnckeckedExceptions
                                 //но хз в какой уместнее.
        }
    }

    private String convertFieldToDBString(String fieldString) {
        StringBuilder result = new StringBuilder();
        Pattern pattern = Pattern.compile("[0-9]");
        Matcher m = pattern.matcher(fieldString);
        if(m.find()){
            result.append(m.group());
        }
        return result.toString();
    }
}