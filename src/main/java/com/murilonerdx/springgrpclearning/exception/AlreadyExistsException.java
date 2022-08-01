package com.murilonerdx.springgrpclearning.exception;


import io.grpc.Status;

public class AlreadyExistsException extends BaseBusinessException{
    private static final String ERROR_MESSAGE = "Produto %s já cadastrado no sistema";
    private final String name;

    public AlreadyExistsException(String name) {
        super(String.format(ERROR_MESSAGE, name));
        this.name = name;
    }

    @Override
    public Status getStatusCode() {
        return Status.ALREADY_EXISTS;
    }

    @Override
    public String getMessage() {
        return String.format(ERROR_MESSAGE, name);
    }
}
