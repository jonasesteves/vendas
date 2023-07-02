package br.com.jeweb.vendas.entity;

public enum TipoReferencia {
    PESSOAL("Pessoal"), COMERCIAL("Comercial");

    private String tipoReferencia;

    TipoReferencia(String tipoReferencia) {
        this.tipoReferencia = tipoReferencia;
    }

    public String getTipoReferencia() {
        return this.tipoReferencia;
    }
}
