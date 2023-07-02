package br.com.jeweb.vendas.entity.webservice;

import br.com.jeweb.core.entity.Objeto;

import java.util.Objects;

public class Empresa extends Objeto<Integer> {
    private Integer id;
    private String nome;

    public Empresa() {
    }

    public Empresa(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empresa empresa = (Empresa) o;
        return Objects.equals(id, empresa.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
