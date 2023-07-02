package br.com.jeweb.vendas.entity.webservice;

import java.util.Objects;

public class RestricoesCobranca {

    private AgenteCobrador agenteCobrador;
    private Empresa empresa;
    private TipoCobranca tipoCobranca;
    private FormaPagamento formaPagamento;

    public RestricoesCobranca() {
    }

    public AgenteCobrador getAgenteCobrador() {
        return agenteCobrador;
    }

    public void setAgenteCobrador(AgenteCobrador agenteCobrador) {
        this.agenteCobrador = agenteCobrador;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestricoesCobranca that = (RestricoesCobranca) o;
        return Objects.equals(agenteCobrador, that.agenteCobrador) &&
                Objects.equals(empresa, that.empresa) &&
                Objects.equals(tipoCobranca, that.tipoCobranca) &&
                Objects.equals(formaPagamento, that.formaPagamento);
    }

    @Override
    public int hashCode() {

        return Objects.hash(agenteCobrador, empresa, tipoCobranca, formaPagamento);
    }

    @Override
    public String toString() {
        return "RestricoesCobranca{" +
                "agenteCobrador=" + agenteCobrador +
                ", empresa=" + empresa +
                ", tipoCobranca=" + tipoCobranca +
                ", formaPagamento=" + formaPagamento +
                '}';
    }
}
