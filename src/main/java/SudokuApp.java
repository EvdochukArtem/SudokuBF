import java.io.*;

public class SudokuApp {

    public static void main(String[] args) {

        Field gameField = null;
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
                        gameField = readFieldFromConsole(consoleReader);
                    }
                    break;
                    case "FILE":
                    case "ФАЙЛ": {
                        while (true) {
                            System.out.println("Введи путь до txt файла с полем судоку\n" +
                                    "Пример: C:/folder/sudoku.txt\n" +
                                    "В файле должно быть поле судоку в виде набора строк с цифрами\n" +
                                    "Каждая строка должна соответствовать строке поля судоку\n" +
                                    "На месте пустой ячейки вводи 0");
                            try (BufferedReader fileReader = new BufferedReader(new FileReader(consoleReader.readLine()))) {
                                gameField = readFieldFromFile(fileReader);
                                break;
                            } catch (FileNotFoundException ex) {
                                System.out.println("Неверный путь! Попробуй снова");
                            } catch (IndexOutOfBoundsException ex) {
                                System.out.println("Некорректный файл! Попробуй снова");
                            }
                        }
                    }
                    break;
                    case "ВЫХОД": {
                        line = "exit";
                        continue;
                    }
                    default:
                        System.out.println("Попробуй еще раз:");
                        break;
                }
                if ("ФАЙЛ".equals(line) || "FILE".equals(line) || "ВВОД".equals(line)) {
                    SudokuBF sbf = new SudokuBF();
                    sbf.build(gameField);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Выход из программы Sudoku BruteForce");
    }

    private static Field readFieldFromConsole(BufferedReader console) throws IOException {

        Field gameField = new Field();
        String line;
        for (int row = 0; row < Field.FIELD_LENGTH; row++) {
            System.out.println("Введи строчку #" + (row + 1));
            line = console.readLine();
            if (line.length() != Field.FIELD_LENGTH)
                row--;
            else
                for (int i = 0; i < line.length(); i++)
                    if (Character.getNumericValue(line.charAt(i)) == -1 || !gameField.setDigit(Character.getNumericValue(line.charAt(i)), i, row)) {
                        row--;
                        break;
                    }
        }
        return gameField;
    }

    private static Field readFieldFromFile(BufferedReader file) throws IOException, IndexOutOfBoundsException{

        Field gameField = new Field();
        String line;
        for (int row = 0; row < Field.FIELD_LENGTH; row++) {
            line = file.readLine();
            if (line.length() != Field.FIELD_LENGTH)
                throw new IndexOutOfBoundsException(); //Знаю что неправильное исключение но пока придумывать свое лень
            else
                for (int i = 0; i < line.length(); i++)
                    if (Character.getNumericValue(line.charAt(i)) == -1 || !gameField.setDigit(Character.getNumericValue(line.charAt(i)), i, row)) {
                        throw new IndexOutOfBoundsException(); //Знаю что неправильное исключение но пока придумывать свое лень
                    }
        }
        return gameField;
    }
}
