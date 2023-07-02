package br.com.jeweb.vendas.entity;

public enum Categoria {
    C("C"), D("D"), E("E"), AC("AC"), AD("AD"), AE("AE");

    private String categoria;

    Categoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCategoria() {
        return this.categoria;
    }
}
