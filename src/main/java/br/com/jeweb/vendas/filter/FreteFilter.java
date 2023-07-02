package br.com.jeweb.vendas.filter;

import br.com.jeweb.core.filter.AbstractFilter;
import br.com.jeweb.core.util.QueryBuilder;
import br.com.jeweb.vendas.entity.Frete;

import java.io.Serializable;
import java.time.LocalDate;

public class FreteFilter extends AbstractFilter<Frete> {

    private FreteFiltro freteFiltro;

    public FreteFilter() {
        this.reset();
    }

    @Override
    public void fill(QueryBuilder<Frete> queryBuilder) {
        if (this.freteFiltro.getNome() != null && !this.freteFiltro.getNome().trim().isEmpty()) {
            queryBuilder.likeIgnoreCase("motorista.nome", "%" + this.freteFiltro.getNome() + "%");
        }

        if (this.freteFiltro.getData() != null && !this.freteFiltro.getData().toString().trim().isEmpty()) {
            queryBuilder.eq("data", this.freteFiltro.getData());
        }

        if (this.freteFiltro.getDestino() != null && !this.freteFiltro.getDestino().trim().isEmpty()) {
            queryBuilder.likeIgnoreCase("destino", "%" + this.freteFiltro.getDestino() + "%");
        }
    }

    @Override
    public void reset() {
        this.freteFiltro = new FreteFiltro();
    }

    public FreteFiltro getFreteFiltro() {
        return freteFiltro;
    }

    public static class FreteFiltro implements Serializable {

        private static final long serialVersionUID = -1011683576718580261L;

        private String nome;
        private LocalDate data;
        private String destino;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public LocalDate getData() {
            return data;
        }

        public void setData(LocalDate data) {
            this.data = data;
        }

        public String getDestino() {
            return destino;
        }

        public void setDestino(String destino) {
            this.destino = destino;
        }
    }
}
