package br.com.jeweb.vendas.entity;

public enum Status {
    LIBERADO("Liberado"), EM_ESPERA("Em Espera"), CANCELADO("Cancelado");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
