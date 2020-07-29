import java.util.Arrays;

//TODO: Замеить тип цифр судоку с int на enum

public class Field {

    public final int FIELD_LENGTH = 9;

    public Field() {
        field = new int [FIELD_LENGTH][FIELD_LENGTH];
        for (int i = 0; i < FIELD_LENGTH; i++)
            Arrays.fill(field[i], -1);
    }

    public int[][] getField() {
        return field;
    }

    public boolean setDigit(int digit, int x, int y) {
        if (x < 0 || x >= FIELD_LENGTH || y < 0 || y >= FIELD_LENGTH)
            return false;
        else if (field[y][x] != -1)
            return false;
        else if (digit == 0)
            field[y][x] = -1;
        else
            field[y][x] = digit;
        return true;
    }

    public void showField() {
        for (int y = 0; y < FIELD_LENGTH; y++) {
            for (int x = 0; x < FIELD_LENGTH; x++) {
                if (field[y][x] == -1)
                    System.out.print("-");
                else
                    System.out.print(field[y][x]);
                if ((x + 1) % 3 == 0)
                    System.out.print("|");
                else
                    System.out.print(" ");
            }
            System.out.println();
            if ((y + 1) % 3 == 0)
                System.out.println("-----------------");
        }
    }

    private int field[][];

}
