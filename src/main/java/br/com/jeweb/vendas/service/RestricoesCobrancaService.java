package br.com.jeweb.vendas.service;

import br.com.jeweb.vendas.controller.UsuarioLogadoController;
import br.com.jeweb.vendas.entity.webservice.*;
import br.com.jeweb.vendas.repository.WebServiceRepository;
import org.datacontract.schemas._2004._07.incopa_servicos_integracao_utilidade.RestricoesCobrancaET;

import javax.enterprise.context.Dependent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.ws.WebServiceException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RestricoesCobrancaService implements Serializable {

    @Inject
    private WebServiceRepository webServiceRepository;

    @Inject
    private UsuarioLogadoController usuarioLogadoController;

    public RestricoesCobrancaService() {
    }

    public List<RestricoesCobranca> buscaRestricoesCobrancaCliente(Integer idCliente) throws WebServiceException {
        List<RestricoesCobrancaET> vetor = webServiceRepository.restricoesCobranca(idCliente);
        List<RestricoesCobranca> restricoes = new ArrayList<>();
        for(RestricoesCobrancaET r : vetor) {
            RestricoesCobranca restricoesCobranca = new RestricoesCobranca();
            for (Empresa e : usuarioLogadoController.getEmpresas()) {
                if (e.getId().equals(r.getIdEmpresa())) {
                    restricoesCobranca.setEmpresa(e);
                    break;
                }
            }
            for (AgenteCobrador a : usuarioLogadoController.getAgentesCobradores()) {
                if (a.getId().equals(r.getIdAgenteCobrador())) {
                    restricoesCobranca.setAgenteCobrador(a);
                    break;
                }
            }
            for (TipoCobranca t : usuarioLogadoController.getTiposCobranca()) {
                if (t.getId().equals(r.getIdTipoCobranca())) {
                    restricoesCobranca.setTipoCobranca(t);
                    break;
                }
            }
            for (FormaPagamento f : usuarioLogadoController.getFormasPagamento()) {
                if (f.getId().equals(r.getIdFormaPgto())) {
                    restricoesCobranca.setFormaPagamento(f);
                    break;
                }
            }
            restricoes.add(restricoesCobranca);
        }
        return restricoes;
    }
}
