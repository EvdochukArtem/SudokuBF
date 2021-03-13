package org.exmpl.domain;

import org.exmpl.logic.Field;
import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class FieldDB {
    private final int id;
    private final String field;

    public FieldDB(int id, Field field) {
        this.id = id;
        this.field = convertFieldToDBString(field.toString());
    }

    public FieldDB(int id, String field) {
        this.id = id;
        this.field = field;
    }

    private String convertFieldToDBString(String fieldString) {
        StringBuilder result = new StringBuilder();
        Pattern pattern = Pattern.compile("[0-9]");
        Matcher m = pattern.matcher(fieldString);
        while(m.find()){
            result.append(m.group());
        }
        return result.toString();
    }
}
