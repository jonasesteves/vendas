package br.com.jeweb.vendas.entity;

public enum TipoConta {
    POUPANCA("Poupança"), CORRENTE("Corrente");

    private String tipoConta;

    TipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    public String getTipoConta() {
        return this.tipoConta;
    }
}
