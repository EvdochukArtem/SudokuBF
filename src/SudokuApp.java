import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;

public class SudokuApp {

    public static void main(String[] args) {

        Field gameField = new Field();
        String exitCode = "exit";
        String line = "";
        try ( BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
        //try (BufferedReader reader = new BufferedReader(new FileReader(new File("sudoku.txt")))) {
            //while (!line.equals(exitCode)) {

                //TODO: Написать приветствие

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
                //sbf.build(gameField);
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
