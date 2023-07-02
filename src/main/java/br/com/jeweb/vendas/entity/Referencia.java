package br.com.jeweb.vendas.entity;

import br.com.jeweb.core.entity.Objeto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "referencia")
public class Referencia extends Objeto<Long> implements Serializable {

    private static final long serialVersionUID = -8223131278273949709L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoReferencia tipoReferencia;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String telefone;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public TipoReferencia getTipoReferencia() {
        return tipoReferencia;
    }

    public void setTipoReferencia(TipoReferencia tipoReferencia) {
        this.tipoReferencia = tipoReferencia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Referencia that = (Referencia) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Referencia{" +
                "id=" + id +
                ", tipoReferencia=" + tipoReferencia +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }
}
