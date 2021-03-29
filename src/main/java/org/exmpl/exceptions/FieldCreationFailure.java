package org.exmpl.exceptions;

public class FieldCreationFailure extends Exception {

    public FieldCreationFailure() {
        super("Received JSON has invalid length");
    }

    public FieldCreationFailure(char c) {
        super("Received JSON contains invalid symbols: " + c);
    }
}
