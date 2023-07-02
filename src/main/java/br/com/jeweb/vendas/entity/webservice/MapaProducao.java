package br.com.jeweb.vendas.entity.webservice;

import br.com.jeweb.core.entity.Objeto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class MapaProducao extends Objeto<Integer> {

    private Integer id;
    private LocalDate data;
    private String objeto;
    private BigDecimal quantidade;
    private String unidade;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapaProducao that = (MapaProducao) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "MapaProducao{" +
                "id=" + id +
                ", data=" + data +
                ", objeto='" + objeto + '\'' +
                ", quantidade=" + quantidade +
                ", unidade='" + unidade + '\'' +
                '}';
    }
}
