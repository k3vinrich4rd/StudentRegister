package br.com.api.studentregister.enums;

public enum ExceptionsEnums {

    NOT_FOUND("Object not found, enter a value exists"),
    FOUND_BUT_INACTIVE("Object found but your status in system is inactive ");
    private final String message;

    ExceptionsEnums(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
