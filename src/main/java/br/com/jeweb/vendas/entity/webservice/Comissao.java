package br.com.jeweb.vendas.entity.webservice;

import br.com.jeweb.core.entity.Objeto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Comissao extends Objeto<Integer> {
    private Integer id;
    private LocalDate data;
    private Integer notaFiscal;
    private BigDecimal valor;
    private String observacao;

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

    public Integer getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(Integer notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comissao comissao = (Comissao) o;
        return Objects.equals(id, comissao.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Comissao{" +
                "id=" + id +
                ", data=" + data +
                ", notaFiscal=" + notaFiscal +
                ", valor=" + valor +
                ", observacao='" + observacao + '\'' +
                '}';
    }
}
