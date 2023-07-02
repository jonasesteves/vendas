package br.com.jeweb.vendas.entity;

import br.com.jeweb.core.entity.Objeto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tipo_telefone")
public class TipoTelefone extends Objeto<Long> implements Serializable {

    private static final long serialVersionUID = 1675591103653077331L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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
        TipoTelefone that = (TipoTelefone) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TipoTelefone{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
