/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jeweb.vendas.entity;

import br.com.jeweb.core.entity.Objeto;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author Jonas Esteves
 */

@Entity
@Table(name = "item")
public class Item extends Objeto<Long> implements Serializable{

    private static final long serialVersionUID = -3174976330618818810L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "id_estoque", nullable = false)
    private Integer idEstoque;

    @Transient
    private String nome;
    
    @Column(nullable = false)
    private BigDecimal quantidade;
    
    @Column(name = "vlr_tabelado", precision = 10, scale = 2, nullable = false)
    private BigDecimal valorTabelado;
    
    @Column(name = "vlr_unitario", precision = 10, scale = 2, nullable = false)
    private BigDecimal valorUnitario;
    
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal desconto;
    
    @Column(name = "vlr_total", precision = 10, scale = 2, nullable = false)
    private BigDecimal valorTotal;

    public Item() {
    }

    public Item(Integer idEstoque, String nome, BigDecimal valorTabelado) {
        this.idEstoque = idEstoque;
        this.nome = nome;
        this.valorTabelado = valorTabelado;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdEstoque() {
        return idEstoque;
    }

    public void setIdEstoque(Integer idEstoque) {
        this.idEstoque = idEstoque;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorTabelado() {
        return valorTabelado;
    }

    public void setValorTabelado(BigDecimal valorTabelado) {
        this.valorTabelado = valorTabelado;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", idEstoque=" + idEstoque +
                ", nome='" + nome + '\'' +
                ", quantidade=" + quantidade +
                ", valorTabelado=" + valorTabelado +
                ", valorUnitario=" + valorUnitario +
                ", desconto=" + desconto +
                ", valorTotal=" + valorTotal +
                '}';
    }
}
