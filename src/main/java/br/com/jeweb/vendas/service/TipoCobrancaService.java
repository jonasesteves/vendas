package br.com.jeweb.vendas.service;

import br.com.jeweb.core.filter.AbstractFilter;
import br.com.jeweb.core.repository.AbstractRepository;
import br.com.jeweb.core.service.AbstractService;
import br.com.jeweb.vendas.controller.UsuarioLogadoController;
import br.com.jeweb.vendas.entity.webservice.TipoCobranca;
import br.com.jeweb.vendas.repository.WebServiceRepository;
import org.datacontract.schemas._2004._07.incopa_servicos_integracao_utilidade.TipoCobrancaET;

import javax.inject.Inject;
import javax.xml.ws.WebServiceException;
import java.util.ArrayList;
import java.util.List;

public class TipoCobrancaService extends AbstractService<TipoCobranca> {

    @Inject
    private WebServiceRepository webServiceRepository;

    @Inject
    private UsuarioLogadoController usuarioLogadoController;

    public TipoCobrancaService() {
    }

    @Override
    public AbstractRepository<TipoCobranca> getRepository() {
        return null;
    }

    @Override
    public TipoCobranca findOne(Long id) throws WebServiceException {
        for (TipoCobranca t : findAll()) {
            if (t.getId().equals(id)) {
                return t;
            }
        }
        return null;
    }

    @Override
    public AbstractFilter<TipoCobranca> getFilter() {
        return null;
    }

    /**
     * Método para diminuir a quantidade de consultas à webservice e consequentemente diminuir o tempo de resposta ao cliente
     * @param id id do objeto
     * @return Objeto solicitado.
     */
    public TipoCobranca find(Long id) {
        List<TipoCobranca> agentes = usuarioLogadoController.getTiposCobranca();
        for (TipoCobranca t : agentes) {
            if (t.getId().equals(id.intValue())) {
                return t;
            }
        }
        return null;
    }

    @Override
    public List<TipoCobranca> findAll() throws WebServiceException {
        List<TipoCobranca> tiposCobranca = new ArrayList<>();
        List<TipoCobrancaET> vetor = webServiceRepository.buscaTiposCobranca();
        for (TipoCobrancaET t : vetor) {
            tiposCobranca.add(new TipoCobranca(t.getID(), t.getNome().getValue()));
        }
        return tiposCobranca;
    }
}
