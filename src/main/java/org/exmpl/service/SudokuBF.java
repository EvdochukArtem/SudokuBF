package org.exmpl.service;

import org.exmpl.domain.Field;
import org.exmpl.exceptions.FieldCreationFailure;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SudokuBF {

    private List<Field> solvedFields;

    public List<String> solveSudoku(String fieldToSolve) throws FieldCreationFailure {
        solvedFields = new ArrayList<>();
        Field field = getFieldFromJSON(fieldToSolve);
        reqInsert(field);
        return solvedFields.stream().map(Field::toJSONString).collect(Collectors.toList());
    }

    private Field getFieldFromJSON(String fieldJSON) throws FieldCreationFailure {
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

    private void reqInsert(Field field) {
        int xx;
        int yy;

        try {
            Field tmpField = field.clone();
            stop_search: {
                for (int y = 0; y < Field.FIELD_LENGTH; y++)
                    for (int x = 0; x < Field.FIELD_LENGTH; x++)
                        if (field.getDigit(x, y) == -1) {
                            xx = x;
                            yy = y;
                            break stop_search;
                        }
                solvedFields.add(field);
                return;
            }
            for (int i = 1; i < 10; i++) {
                if (tmpField.setDigit(i, xx, yy)) {
                    reqInsert(tmpField);
                    tmpField = field.clone();
                }
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace(); //Знаю что так делать не хорошо, слышал что лучше оборачивать в UncheckedExceptions
                                 //но хз в какой уместнее.
        }
    }
}