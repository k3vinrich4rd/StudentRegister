package br.com.api.studentregister.exception;


import br.com.api.studentregister.enums.ExceptionsEnums;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException() {
        super(ExceptionsEnums.NOT_FOUND.getMessage());

    }
}
