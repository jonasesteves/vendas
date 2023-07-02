package br.com.jeweb.vendas.filter;

import br.com.jeweb.core.filter.AbstractFilter;
import br.com.jeweb.core.util.QueryBuilder;
import br.com.jeweb.vendas.entity.Pessoa;

import java.io.Serializable;

public class PessoaFilter extends AbstractFilter<Pessoa> {

    private PessoaFiltro pessoaFiltro;

    public PessoaFilter() {
        this.reset();
    }

    @Override
    public void fill(QueryBuilder<Pessoa> queryBuilder) {
        if (this.pessoaFiltro.getNome() != null && !this.pessoaFiltro.getNome().trim().isEmpty()) {
            queryBuilder.likeIgnoreCase("nome", "%" + this.pessoaFiltro.getNome() + "%");
        }

        if (this.pessoaFiltro.getCidade() != null && !this.pessoaFiltro.getCidade().trim().isEmpty()) {
            queryBuilder.likeIgnoreCase("cidade", "%" + this.pessoaFiltro.getCidade() + "%");
        }
    }

    @Override
    public void reset() {
        this.pessoaFiltro = new PessoaFiltro();
    }

    public PessoaFiltro getPessoaFiltro() {
        return pessoaFiltro;
    }

    public static class PessoaFiltro implements Serializable {

        private static final long serialVersionUID = -1011683576718580261L;

        private String nome;
        private String cidade;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getCidade() {
            return cidade;
        }

        public void setCidade(String cidade) {
            this.cidade = cidade;
        }
    }
}
