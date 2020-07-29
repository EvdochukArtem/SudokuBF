import java.util.ArrayList;

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

    public ArrayList<int[][]> getSudokuSolutions() {
        return sudokuSolutions;
    }

    private int[][] fieldToSolve;
    private ArrayList <int[][]> sudokuSolutions;
}
