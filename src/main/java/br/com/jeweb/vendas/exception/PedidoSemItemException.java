package br.com.jeweb.vendas.exception;

public class PedidoSemItemException extends Exception{
    public PedidoSemItemException() {
    }

    public PedidoSemItemException(String message) {
        super(message);
    }

    public PedidoSemItemException(String message, Throwable cause) {
        super(message, cause);
    }


}
