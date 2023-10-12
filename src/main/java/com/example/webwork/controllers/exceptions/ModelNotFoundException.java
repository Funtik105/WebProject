package com.example.webwork.controllers.exceptions;

public class ModelNotFoundException extends RuntimeException {
    public ModelNotFoundException(Long id) {
        super("Could not find model " + id);
    }
}
