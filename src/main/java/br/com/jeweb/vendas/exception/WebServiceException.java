package br.com.jeweb.vendas.exception;

public class WebServiceException extends Exception{
    public WebServiceException() {

    }

    public WebServiceException(String message) {
        super(message);
    }

    public WebServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
