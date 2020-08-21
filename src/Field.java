import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

//TODO: Замеить тип цифр судоку с int на enum

public class Field {

    public static final int FIELD_LENGTH = 9;

    public Field() {
        field = new int [FIELD_LENGTH][FIELD_LENGTH];
        for (int i = 0; i < FIELD_LENGTH; i++)
            Arrays.fill(field[i], -1);
    }

    public boolean setDigit(int digit, int x, int y) {
        if (x < 0 || x >= FIELD_LENGTH || y < 0 || y >= FIELD_LENGTH)
            return false;
        if (field[y][x] != -1)
            return false;
        if (digit == 0) {
            field[y][x] = -1;
            return true;
        }
        else
            return CheckDigit(digit, x, y);
    }

    public int getCellValue(int x, int y) {
        return this.field[y][x];
    }

    public void showField() {
        System.out.println("-----------------");
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

    private boolean CheckDigit(int digit, int x, int y) {

        Set set = new HashSet();

        for (int i = 0; i < field[0].length; i++)
            set.add(field[y][i]);
        if (!set.add(digit))
            return false;
        set.clear();

        for (int i = 0; i < field.length; i++)
            set.add(field[i][x]);
        if (!set.add(digit))
            return false;
        set.clear();

        for (int i = 3 * (x / 3); i < 3 * (x / 3) + 3; i++)
            for (int j = 3 * (y / 3); j < 3 * (y / 3) + 3; j++)
                set.add(field[j][i]);
        if (!set.add(digit))
            return false;
        field[y][x] = digit;
        return true;
    }

    public boolean CheckField() {

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
        for (int jj = 0; jj < field.length / 3; jj++) {
            for (int ii = 0; ii < field[0].length / 3; ii++) {
                for (int i = 3 * ii; i < ii * 3 + 3; i++)
                    for (int j = jj * 3; j < jj * 3 + 3; j++)
                        if (!set.add(field[j][i]))
                            return false;
                set.clear();
            }
        }



        return true;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Field))
            return false;
        if (this == null)
            return this == obj;
        Field tmp = (Field)obj;
        return this.toString().equals(tmp.toString());
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {

        Field clone = new Field();
        for (int i = 0; i < FIELD_LENGTH; i++)
            for (int j = 0; j < FIELD_LENGTH; j++)
                clone.setDigit(this.field[j][i], i, j);

        return clone;
    }

    @Override
    public String toString() {
        String out = "-----------------\n";
        for (int y = 0; y < FIELD_LENGTH; y++) {
            for (int x = 0; x < FIELD_LENGTH; x++) {
                if (field[y][x] == -1)
                    out += "-";
                else
                    out += field[y][x];
                if ((x + 1) % 3 == 0)
                    out += "|";
                else
                    out += " ";
            }
            out += "\n";
            if ((y + 1) % 3 == 0)
                out += "-----------------\n";
        }
        return out;
    }

    private int field[][];

}
