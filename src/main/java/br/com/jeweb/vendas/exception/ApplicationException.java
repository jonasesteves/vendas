package br.com.jeweb.vendas.exception;


public class ApplicationException extends RuntimeException {
    private static final long serialVersionUID = 3607940088523410450L;

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable throwable) {
        super(message, throwable);
        this.printStackTrace();
    }
}
