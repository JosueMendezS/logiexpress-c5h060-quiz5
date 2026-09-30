package com.logiexpress.exception;

public class PesoExcedidoException extends RuntimeException {

    public PesoExcedidoException(String mensaje) {
        super(mensaje);
    }
}