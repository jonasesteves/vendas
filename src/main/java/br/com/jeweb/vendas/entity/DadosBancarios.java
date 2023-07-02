package br.com.jeweb.vendas.entity;

import br.com.jeweb.core.entity.Objeto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "dados_bancarios")
public class DadosBancarios extends Objeto<Long> implements Serializable {

    private static final long serialVersionUID = 33870585240317433L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_banco")
    private Banco banco;

    @Column(nullable = false)
    private String agencia;

    @Column(name = "conta", nullable = false)
    private String conta;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoConta tipoConta;

    @Column
    private Long operacao;

    @Column(nullable = false)
    private String favorecido;

    public DadosBancarios() {
        this.banco = new Banco();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    public Long getOperacao() {
        return operacao;
    }

    public void setOperacao(Long operacao) {
        this.operacao = operacao;
    }

    public String getFavorecido() {
        return favorecido;
    }

    public void setFavorecido(String favorecido) {
        this.favorecido = favorecido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DadosBancarios that = (DadosBancarios) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DadosBancarios{" +
                "id=" + id +
                ", banco=" + banco +
                ", agencia=" + agencia +
                ", conta='" + conta + '\'' +
                ", tipoConta=" + tipoConta +
                ", operacao=" + operacao +
                ", favorecido='" + favorecido + '\'' +
                '}';
    }
}
