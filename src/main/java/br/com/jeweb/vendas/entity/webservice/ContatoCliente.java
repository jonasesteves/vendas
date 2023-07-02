package br.com.jeweb.vendas.entity.webservice;

import br.com.jeweb.core.entity.Objeto;

import java.time.LocalDate;
import java.util.Objects;

public class ContatoCliente extends Objeto<Integer> {

    private Integer id;
    private Cliente cliente;
    private String assunto;
    private String texto;
    private String usuario;
    private LocalDate data;
    private boolean fechado;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public boolean isFechado() {
        return fechado;
    }

    public void setFechado(boolean fechado) {
        this.fechado = fechado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContatoCliente that = (ContatoCliente) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ContatoCliente{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", assunto='" + assunto + '\'' +
                ", texto='" + texto + '\'' +
                ", usuario='" + usuario + '\'' +
                ", data=" + data +
                ", fechado=" + fechado +
                '}';
    }
}
