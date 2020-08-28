import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;

public class SudokuApp {

    public static void main(String[] args) {

        Field gameField = new Field();
        String exitCode = "exit";
        String line = "";
        BufferedReader reader = null;
        BufferedReader fileReader = null;
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
                        reader = consoleReader;
                        System.out.println("Вводи построчно поле для судоку. На месте пустой ячейки вводи 0");
                    }
                    break;
                    case "ФАЙЛ": {
                        System.out.println("Введи путь до txt файла с полем судоку\n" +
                                "Пример: C:/folder/sudoku.txt\n" +
                                "В файле должно быть поле судоку в виде набора строк с цифрами\n" +
                                "Каждая строка должна соответствовать строке поля судоку\n" +
                                "На месте пустой ячейки вводи 0");
                        line = consoleReader.readLine();
                        fileReader = new BufferedReader(new FileReader(new File(line)));
                        reader = fileReader;
                    }
                    break;
                    case "ВЫХОД": {
                        line = "exit";
                        continue;
                    }
                }
                for (int row = 0; row < gameField.FIELD_LENGTH; row++) {
                    System.out.println("Введите строчку #" + (row + 1));
                    line = reader.readLine();
                    if (line.length() != gameField.FIELD_LENGTH)
                        row--;
                    else
                        for (int i = 0; i < line.length(); i++)
                            if (Character.getNumericValue(line.charAt(i)) == -1 || !gameField.setDigit(Character.getNumericValue(line.charAt(i)), i, row)) {
                                row--;
                                break;
                            }
                }
                SudokuBF sbf = new SudokuBF();
                sbf.build(gameField);
            }
        } catch (Exception e) {
            e.printStackTrace();
            line = "exit";
        }
        System.out.println("Выход из программы Sudoku BruteForce");
    }


}
