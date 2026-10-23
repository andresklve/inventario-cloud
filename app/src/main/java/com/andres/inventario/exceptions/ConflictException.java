package com.andres.inventario.exceptions;

// La petición es válida pero choca con el estado actual: duplicados, stock insuficiente, etc.
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
