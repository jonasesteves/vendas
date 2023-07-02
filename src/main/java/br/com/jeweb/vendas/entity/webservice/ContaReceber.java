package br.com.jeweb.vendas.entity.webservice;

import br.com.jeweb.core.entity.Objeto;
import br.com.jeweb.vendas.entity.Usuario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class ContaReceber extends Objeto<Integer> {
    private Integer id;
    private Usuario usuario;
    private LocalDate emissao;
    private LocalDate vencimento;
    private String titulo;
    private Cliente cliente;
    private BigDecimal valor;
    private BigDecimal saldo;
    private String notaFiscal;
    private boolean vencido;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getEmissao() {
        return emissao;
    }

    public void setEmissao(LocalDate emissao) {
        this.emissao = emissao;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(String notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public boolean isVencido() {
        return vencido;
    }

    public void setVencido(boolean vencido) {
        this.vencido = vencido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContaReceber that = (ContaReceber) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ContaReceber{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", emissao=" + emissao +
                ", vencimento=" + vencimento +
                ", titulo='" + titulo + '\'' +
                ", cliente=" + cliente +
                ", valor=" + valor +
                ", saldo=" + saldo +
                ", notaFiscal='" + notaFiscal + '\'' +
                ", vencido=" + vencido +
                '}';
    }
}
