package org.exmpl.exceptions;

public class FieldDBExtractionFailure extends Exception {
    public FieldDBExtractionFailure() {
        super("No records in database");
    }
}
