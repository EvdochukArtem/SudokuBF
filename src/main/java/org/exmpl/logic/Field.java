package org.exmpl.logic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

//TODO: Замеить тип цифр судоку с int на enum

public class Field implements Cloneable {

    public static final int FIELD_LENGTH = 9;

    private final int [][] field;

    public Field() {
        field = new int [FIELD_LENGTH][FIELD_LENGTH];
        for (int i = 0; i < FIELD_LENGTH; i++)
            Arrays.fill(field[i], -1);
    }

    public boolean setDigit(int digit, int x, int y) {
        if (x < 0 || x >= FIELD_LENGTH || y < 0 || y >= FIELD_LENGTH)
            return false;
        if (digit < 0 || digit > 9)
            return false;
        if (field[y][x] != -1)
            return false;
        if (digit == 0) {
            field[y][x] = -1;
            return true;
        }
        else
            return checkDigit(digit, x, y);
    }

    public int getDigit(int x, int y) {
        return this.field[y][x];
    }

    private boolean checkDigit(int digit, int x, int y) {

        Set<Integer> set = new HashSet<>();

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

    /*public boolean CheckField() {

        Set<Integer> set = new HashSet<>();

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
    }*/

    @Override
    public Field clone() throws CloneNotSupportedException {
        super.clone();
        Field clone = new Field();
        for (int i = 0; i < FIELD_LENGTH; i++)
            for (int j = 0; j < FIELD_LENGTH; j++)
                clone.setDigit(this.field[j][i], i, j);

        return clone;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("-----------------\n");
        for (int y = 0; y < FIELD_LENGTH; y++) {
            for (int x = 0; x < FIELD_LENGTH; x++) {
                if (field[y][x] == -1)
                    out.append("-");
                else
                    out.append(field[y][x]);
                if ((x + 1) % 3 == 0)
                    out.append("|");
                else
                    out.append(" ");
            }
            out.append("\n");
            if ((y + 1) % 3 == 0)
                out.append("-----------------\n");
        }
        return out.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field1 = (Field) o;
        for (int i = 0; i < FIELD_LENGTH; i++)
            for(int j = 0; j < FIELD_LENGTH; j++)
                if (getDigit(i, j) != field1.getDigit(i ,j))
                    return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(field);
    }
}
