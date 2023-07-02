package br.com.jeweb.vendas.exception;

public class ResourceNotFoundException extends Exception{
    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }


}
