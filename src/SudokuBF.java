import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

//TODO: Инкапсуляция! Заменить массив int[][] на объект Field

public class SudokuBF {

    public SudokuBF(int[][] fieldToSolve) {
        this.fieldToSolve = fieldToSolve;
        sudokuSolutions = new ArrayList<>();
    }

    public ArrayList<int[][]> startBF() {
        int[][] tmpField = new int [fieldToSolve.length][fieldToSolve.length];
        System.arraycopy(fieldToSolve, 0, tmpField, 0, fieldToSolve.length);

        return sudokuSolutions;
    }

    public ArrayList<int[][]> getSudokuSolutions(int[][] field) {

        /*for (int y = 0; y < field.length; y++)
            for (int x = 0; x < field[0].length; x++)
                for (int digit = 1; digit < 9; digit++)
                    if (CheckDigit(field, digit, x, y) == false)
                    {}*/
        return sudokuSolutions;
    }

    private boolean CheckDigit(int[][] field, int digit, int x, int y) {
        if (field[y][x] != -1)
            return false;

        field[y][x] = digit;
        Set set = new HashSet();

        for (int i = 0; i < field[0].length; i++)
            if (!set.add(field[y][i])) {
                field[y][x] = -1;
                return false;
            }
        set.clear();
        for (int i = 0; i < field[0].length; i++)
            if (!set.add(field[i][x])){
                field[y][x] = -1;
                return false;
            }
        set.clear();
        for (int i = x / 3; i < x / 3 + 3; i++)
            for (int j = y / 3; j < y / 3 + 3; j++)
                if (!set.add(field[j][i])){
                    field[y][x] = -1;
                    return false;
                }

        return true;
    }

    private boolean CheckField(int[][] field) {

        Set set = new HashSet();

        for (int j = 0; j < field.length; j++) {
            for (int i = 0; i < field[0].length; i++)
                if (!set.add(field[j][i]))
                    return false;
            set.clear();
        }
        for (int j = 0; j < field.length; j += 3) {
            for (int i = 0; i < field[0].length; i++)
                if (!set.add(field[i][j]))
                    return false;
            set.clear();
        }
        for (int jj = 0; jj < field.length; jj += 3)
            for (int ii = 0; ii < field[0].length; ii += 3)
                for (int i = ii / 3; i < ii / 3 + 3; i++)
                    for (int j = jj / 3; j < jj / 3 + 3; j++)
                        if (!set.add(field[j][i]))
                            return false;

        return true;
    }
    private int[][] fieldToSolve;

    private ArrayList <int[][]> sudokuSolutions;
}