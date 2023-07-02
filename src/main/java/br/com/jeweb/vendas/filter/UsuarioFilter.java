package br.com.jeweb.vendas.filter;

import br.com.jeweb.core.filter.AbstractFilter;
import br.com.jeweb.core.util.QueryBuilder;
import br.com.jeweb.vendas.entity.Usuario;

import java.io.Serializable;

public class UsuarioFilter extends AbstractFilter<Usuario> {

    private UsuarioFiltro usuarioFiltro;

    public UsuarioFilter() {
        this.reset();
    }

    @Override
    public void fill(QueryBuilder<Usuario> queryBuilder) {
        if (this.usuarioFiltro.getNome() != null && !this.usuarioFiltro.getNome().trim().isEmpty()) {
            queryBuilder.likeIgnoreCase("nome", "%" + this.usuarioFiltro.getNome() + "%");
        }

        if (this.usuarioFiltro.getEmail() != null && !this.usuarioFiltro.getEmail().trim().isEmpty()) {
            queryBuilder.likeIgnoreCase("email", "%" + this.usuarioFiltro.getEmail() + "%");
        }
    }

    @Override
    public void reset() {
        this.usuarioFiltro = new UsuarioFiltro();
    }

    public UsuarioFiltro getUsuarioFiltro() {
        return usuarioFiltro;
    }

    public static class UsuarioFiltro implements Serializable {

        private static final long serialVersionUID = -1011683576718580261L;

        private String nome;
        private String email;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
