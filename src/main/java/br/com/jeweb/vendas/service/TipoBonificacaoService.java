package br.com.jeweb.vendas.service;

import br.com.jeweb.core.filter.AbstractFilter;
import br.com.jeweb.core.repository.AbstractRepository;
import br.com.jeweb.core.service.AbstractService;
import br.com.jeweb.vendas.controller.UsuarioLogadoController;
import br.com.jeweb.vendas.entity.webservice.TipoBonificacao;
import br.com.jeweb.vendas.repository.WebServiceRepository;
import org.datacontract.schemas._2004._07.incopa_servicos_integracao_utilidade.TipoBonificacaoET;

import javax.inject.Inject;
import javax.xml.ws.WebServiceException;
import java.util.ArrayList;
import java.util.List;

public class TipoBonificacaoService extends AbstractService<TipoBonificacao> {
    @Inject
    private WebServiceRepository webServiceRepository;

    @Inject
    private UsuarioLogadoController usuarioLogadoController;

    public TipoBonificacaoService() {
    }

    @Override
    public List<TipoBonificacao> findAll() throws WebServiceException {
        List<TipoBonificacao> tiposbonificacao = new ArrayList<TipoBonificacao>();
        List<TipoBonificacaoET> vetor = webServiceRepository.buscaTiposBonificacao();
        for(TipoBonificacaoET t : vetor) {
            tiposbonificacao.add(new TipoBonificacao(t.getID(), t.getNome().getValue()));
        }
        return tiposbonificacao;
    }

    @Override
    public AbstractRepository<TipoBonificacao> getRepository() {
        return null;
    }

    @Override
    public TipoBonificacao findOne(Long id) throws WebServiceException {
        TipoBonificacao tipoBonificacao = new TipoBonificacao();
        for (TipoBonificacao t : findAll()) {
            if(t.getId().equals(id)) {
                tipoBonificacao.setId(t.getId());
                tipoBonificacao.setDescricao(t.getDescricao());
                return tipoBonificacao;
            }
        }
        return null;
    }

    @Override
    public AbstractFilter<TipoBonificacao> getFilter() {
        return null;
    }

    /**
     * Método para diminuir a quantidade de consultas à webservice e consequentemente diminuir o tempo de resposta ao cliente
     * @param id id do objeto
     * @return Objeto solicitado.
     */
    public TipoBonificacao find(Long id) {
        List<TipoBonificacao> agentes = usuarioLogadoController.getTiposBonificacao();
        for (TipoBonificacao t : agentes) {
            if (t.getId().equals(id.intValue())) {
                return t;
            }
        }
        return null;
    }
}
