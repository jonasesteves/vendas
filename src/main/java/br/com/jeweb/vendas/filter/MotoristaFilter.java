package br.com.jeweb.vendas.filter;

import br.com.jeweb.core.filter.AbstractFilter;
import br.com.jeweb.core.util.QueryBuilder;
import br.com.jeweb.vendas.entity.Motorista;

import java.io.Serializable;

public class MotoristaFilter extends AbstractFilter<Motorista> {

    private MotoristaFiltro motoristaFiltro;

    public MotoristaFilter() {
        this.reset();
    }

    @Override
    public void fill(QueryBuilder<Motorista> queryBuilder) {
        if (this.motoristaFiltro.getCpf() != null && !this.motoristaFiltro.getCpf().trim().isEmpty()) {
            queryBuilder.likeIgnoreCase("cpf", "%" + this.motoristaFiltro.getCpf() + "%");
        }

        if (this.motoristaFiltro.getNome() != null && !this.motoristaFiltro.getNome().trim().isEmpty()) {
            queryBuilder.likeIgnoreCase("nome", "%" + this.motoristaFiltro.getNome() + "%");
        }
    }

    @Override
    public void reset() {
        this.motoristaFiltro = new MotoristaFiltro();
    }

    public MotoristaFiltro getMotoristaFiltro() {
        return motoristaFiltro;
    }

    public static class MotoristaFiltro implements Serializable {

        private static final long serialVersionUID = -1011683576718580261L;

        private String nome;
        private String cpf;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getCpf() {
            return cpf;
        }

        public void setCpf(String cpf) {
            this.cpf = cpf;
        }
    }
}
