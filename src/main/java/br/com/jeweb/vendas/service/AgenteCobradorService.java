package br.com.jeweb.vendas.service;

import br.com.jeweb.core.filter.AbstractFilter;
import br.com.jeweb.core.repository.AbstractRepository;
import br.com.jeweb.core.service.AbstractService;
import br.com.jeweb.vendas.controller.UsuarioLogadoController;
import br.com.jeweb.vendas.entity.webservice.AgenteCobrador;
import br.com.jeweb.vendas.repository.WebServiceRepository;
import org.datacontract.schemas._2004._07.incopa_servicos_integracao_utilidade.AgenteCobradorET;

import javax.inject.Inject;
import javax.xml.ws.WebServiceException;
import java.util.ArrayList;
import java.util.List;

public class AgenteCobradorService extends AbstractService<AgenteCobrador> {

    @Inject
    private WebServiceRepository webServiceRepository;

    @Inject
    private UsuarioLogadoController usuarioLogadoController;

    public AgenteCobradorService() {
    }

    @Override
    public AbstractRepository<AgenteCobrador> getRepository() {
        return null;
    }

    @Override
    public AgenteCobrador findOne(Long id) throws WebServiceException {
        for (AgenteCobrador a : findAll()) {
            if (a.getId().equals(id)) {
                return a;
            }
        }
        return null;
    }

    @Override
    public AbstractFilter<AgenteCobrador> getFilter() {
        return null;
    }

    /**
     * Método para diminuir a quantidade de consultas à webservice e consequentemente diminuir o tempo de resposta ao cliente
     * @param id id do objeto
     * @return Objeto solicitado.
     */
    public AgenteCobrador find(Long id) {
        List<AgenteCobrador> agentes = usuarioLogadoController.getAgentesCobradores();
        for (AgenteCobrador a : agentes) {
            if (a.getId().equals(id.intValue())) {
                return a;
            }
        }
        return null;
    }

    @Override
    public List<AgenteCobrador> findAll() {
        List<AgenteCobrador> agentes = new ArrayList<>();
        List<AgenteCobradorET> vetor = webServiceRepository.buscaAgentesCobradores();
        for (AgenteCobradorET a : vetor) {
            agentes.add(new AgenteCobrador(a.getID(), a.getNome().getValue()));
        }
        return agentes;
    }

}
