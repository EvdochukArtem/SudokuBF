package org.exmpl.domain;

import org.exmpl.exceptions.FieldCreationFailure;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
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

    public static Field getFieldFromJSON(String fieldJSON) throws FieldCreationFailure {
        Objects.requireNonNull(fieldJSON);
        JSONObject json = new JSONObject(fieldJSON);
        String fieldString = json.getString("field");
        if (fieldString.length() < Field.FIELD_LENGTH * Field.FIELD_LENGTH)
            throw new FieldCreationFailure();
        Field field = new Field();
        for (int i = 0; i < fieldString.length(); i++) {
            int x = i % Field.FIELD_LENGTH;
            int y = i / Field.FIELD_LENGTH;
            int digit = Character.getNumericValue(fieldString.charAt(i));
            if (!field.setDigit(digit, x, y))
                throw new FieldCreationFailure(fieldString.charAt(i));
        }
        return field;
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
        StringBuilder out = new StringBuilder();
        for (int y = 0; y < FIELD_LENGTH; y++) {
            for (int x = 0; x < FIELD_LENGTH; x++) {
                if (field[y][x] == -1)
                    out.append("0");
                else
                    out.append(field[y][x]);
            }
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
