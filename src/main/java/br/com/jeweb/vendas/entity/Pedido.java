/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jeweb.vendas.entity;


import br.com.jeweb.core.entity.Objeto;
import br.com.jeweb.vendas.entity.webservice.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Jonas Esteves
 */
@Entity
@Table(name = "pedido")
public class Pedido extends Objeto<Long> implements Serializable{

    private static final long serialVersionUID = -7814614483891843927L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pedido", nullable = false)
    private List<Item> itens;
    
    @Column(name = "id_empresa", nullable = false)
    private Integer idEmpresa;
    
    @Column(name = "id_operacao", nullable = false)
    private Integer idOperacao;
    
    @Column(name = "id_tipo_bonificacao")
    private Integer idTipoBonificacao;
    
    @Column(name = "id_cliente", nullable = false)
    private Integer idCliente;
    
    @Column(name = "id_agente_cobrador")
    private Integer idAgenteCobrador;
    
    @Column(name = "id_tipo_cobranca")
    private Integer idTipoCobranca;
    
    @Column(name = "id_forma_pgto")
    private Integer idFormaPagamento;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_vendedor", referencedColumnName = "id_top")
    private Usuario usuario;

    @Column(name = "dt_emissao", nullable = false)
    private LocalDate dataEmissao;

    @Column(name = "hr_emissao")
    private LocalTime horaEmissao;
    
    @Column(name = "desconto_medio", precision = 10, scale = 2, nullable = false)
    private BigDecimal descontoMedio;
    
    @Column(name = "vlr_total", precision = 10, scale = 2, nullable = false)
    private BigDecimal valorTotal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    
    @Column(name = "obs")
    private String observacao;
    
    @Column(nullable = false)
    private Boolean enviado;

    @Transient
    private Cliente cliente;

    @Transient
    private String operacao;

    @Transient
    private AgenteCobrador agenteCobrador;

    @Transient
    private TipoCobranca tipoCobranca;

    @Transient
    private FormaPagamento formaPagamento;

    @Transient
    private Empresa empresa;

    @Transient
    private TipoBonificacao tipoBonificacao;

    @Transient
    private Integer codEmbarque;

    @Transient
    private Integer notaFiscal;

    public Pedido() {
        this.itens = new ArrayList<>();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
    
    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Integer getIdOperacao() {
        return idOperacao;
    }

    public void setIdOperacao(Integer idOperacao) {
        this.idOperacao = idOperacao;
    }

    public Integer getIdTipoBonificacao() {
        return idTipoBonificacao;
    }

    public void setIdTipoBonificacao(Integer idTipoBonificacao) {
        this.idTipoBonificacao = idTipoBonificacao;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdAgenteCobrador() {
        return idAgenteCobrador;
    }

    public void setIdAgenteCobrador(Integer idAgenteCobrador) {
        this.idAgenteCobrador = idAgenteCobrador;
    }

    public Integer getIdTipoCobranca() {
        return idTipoCobranca;
    }

    public void setIdTipoCobranca(Integer idTipoCobranca) {
        this.idTipoCobranca = idTipoCobranca;
    }

    public Integer getIdFormaPagamento() {
        return idFormaPagamento;
    }

    public void setIdFormaPagamento(Integer idFormaPagamento) {
        this.idFormaPagamento = idFormaPagamento;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public LocalTime getHoraEmissao() {
        return horaEmissao;
    }

    public void setHoraEmissao(LocalTime horaEmissao) {
        this.horaEmissao = horaEmissao;
    }

    public BigDecimal getDescontoMedio() {
        return descontoMedio;
    }

    public void setDescontoMedio(BigDecimal descontoMedio) {
        this.descontoMedio = descontoMedio;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Boolean getEnviado() {
        return enviado;
    }

    public void setEnviado(Boolean enviado) {
        this.enviado = enviado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public AgenteCobrador getAgenteCobrador() {
        return agenteCobrador;
    }

    public void setAgenteCobrador(AgenteCobrador agenteCobrador) {
        this.agenteCobrador = agenteCobrador;
    }

    public TipoCobranca getTipoCobranca() {
        return tipoCobranca;
    }

    public void setTipoCobranca(TipoCobranca tipoCobranca) {
        this.tipoCobranca = tipoCobranca;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public TipoBonificacao getTipoBonificacao() {
        return tipoBonificacao;
    }

    public void setTipoBonificacao(TipoBonificacao tipoBonificacao) {
        this.tipoBonificacao = tipoBonificacao;
    }

    public Integer getCodEmbarque() {
        return codEmbarque;
    }

    public void setCodEmbarque(Integer codEmbarque) {
        this.codEmbarque = codEmbarque;
    }

    public Integer getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(Integer notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", itens=" + itens +
                ", idEmpresa=" + idEmpresa +
                ", idOperacao=" + idOperacao +
                ", idTipoBonificacao=" + idTipoBonificacao +
                ", idCliente=" + idCliente +
                ", idAgenteCobrador=" + idAgenteCobrador +
                ", idTipoCobranca=" + idTipoCobranca +
                ", idFormaPagamento=" + idFormaPagamento +
                ", usuario=" + usuario +
                ", dataEmissao=" + dataEmissao +
                ", horaEmissao=" + horaEmissao +
                ", descontoMedio=" + descontoMedio +
                ", valorTotal=" + valorTotal +
                ", status=" + status +
                ", observacao='" + observacao + '\'' +
                ", enviado=" + enviado +
                ", cliente=" + cliente +
                ", operacao='" + operacao + '\'' +
                ", agenteCobrador=" + agenteCobrador +
                ", tipoCobranca=" + tipoCobranca +
                ", formaPagamento=" + formaPagamento +
                ", empresa=" + empresa +
                ", tipoBonificacao=" + tipoBonificacao +
                ", codEmbarque=" + codEmbarque +
                ", notaFiscal=" + notaFiscal +
                '}';
    }
}
