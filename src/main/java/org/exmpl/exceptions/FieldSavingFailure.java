package org.exmpl.exceptions;

public class FieldSavingFailure extends Exception {
    public FieldSavingFailure() {
        super("Invalid field length");
    }
}
