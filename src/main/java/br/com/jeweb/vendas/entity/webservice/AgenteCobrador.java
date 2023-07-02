package br.com.jeweb.vendas.entity.webservice;

import br.com.jeweb.core.entity.Objeto;

import java.util.Objects;

public class AgenteCobrador extends Objeto<Integer> {

    private Integer id;
    private String descricao;

    public AgenteCobrador() {
    }

    public AgenteCobrador(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {

    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgenteCobrador that = (AgenteCobrador) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "AgenteCobrador{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
