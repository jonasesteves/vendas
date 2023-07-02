package br.com.jeweb.vendas.service;

import br.com.jeweb.core.filter.AbstractFilter;
import br.com.jeweb.core.repository.AbstractRepository;
import br.com.jeweb.core.service.AbstractService;
import br.com.jeweb.vendas.controller.UsuarioLogadoController;
import br.com.jeweb.vendas.entity.webservice.Empresa;
import br.com.jeweb.vendas.repository.WebServiceRepository;
import org.datacontract.schemas._2004._07.incopa_servicos_integracao_utilidade.EmpresaET;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.xml.ws.WebServiceException;
import java.util.ArrayList;
import java.util.List;

@Dependent
public class EmpresaService extends AbstractService<Empresa> {
    @Inject
    private WebServiceRepository webServiceRepository;

    @Inject
    private UsuarioLogadoController usuarioLogadoController;

    @Override
    public AbstractRepository<Empresa> getRepository() {
        return null;
    }

    @Override
    public List<Empresa> findAll() throws WebServiceException {
        List<Empresa> empresas = new ArrayList<>();
        List<EmpresaET> vetor = webServiceRepository.buscaEmpresas();
        for (EmpresaET e : vetor) {
            empresas.add(new Empresa(e.getId(), e.getNome().getValue()));
        }
        return empresas;
    }

    @Override
    public Empresa findOne(Long id) throws WebServiceException {
        for (Empresa e : findAll()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public AbstractFilter<Empresa> getFilter() {
        return null;
    }

    /**
     * Método para diminuir a quantidade de consultas à webservice e consequentemente diminuir o tempo de resposta ao cliente
     * @param id id do objeto
     * @return Objeto solicitado.
     */
    public Empresa find(Long id) {
        List<Empresa> agentes = usuarioLogadoController.getEmpresas();
        for (Empresa e : agentes) {
            if (e.getId().equals(id.intValue())) {
                return e;
            }
        }
        return null;
    }
}
