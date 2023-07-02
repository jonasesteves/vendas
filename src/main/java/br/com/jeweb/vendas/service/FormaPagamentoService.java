package br.com.jeweb.vendas.service;

import br.com.jeweb.core.filter.AbstractFilter;
import br.com.jeweb.core.repository.AbstractRepository;
import br.com.jeweb.core.service.AbstractService;
import br.com.jeweb.vendas.controller.UsuarioLogadoController;
import br.com.jeweb.vendas.entity.webservice.FormaPagamento;
import br.com.jeweb.vendas.repository.WebServiceRepository;
import org.datacontract.schemas._2004._07.incopa_servicos_integracao_utilidade.FormaPagamentoET;

import javax.inject.Inject;
import javax.xml.ws.WebServiceException;
import java.util.ArrayList;
import java.util.List;

public class FormaPagamentoService extends AbstractService<FormaPagamento> {

    @Inject
    private WebServiceRepository webServiceRepository;

    @Inject
    private UsuarioLogadoController usuarioLogadoController;

    public FormaPagamentoService() {
    }

    @Override
    public AbstractRepository<FormaPagamento> getRepository() {
        return null;
    }

    @Override
    public FormaPagamento findOne(Long id) throws WebServiceException {
        for (FormaPagamento f : findAll()) {
            if (f.getId().equals(id)) {
                return f;
            }
        }
        return null;
    }

    @Override
    public AbstractFilter<FormaPagamento> getFilter() {
        return null;
    }

    /**
     * Método para diminuir a quantidade de consultas à webservice e consequentemente diminuir o tempo de resposta ao cliente
     * @param id id do objeto
     * @return Objeto solicitado.
     */
    public FormaPagamento find(Long id) {
        List<FormaPagamento> agentes = usuarioLogadoController.getFormasPagamento();
        for (FormaPagamento f : agentes) {
            if (f.getId().equals(id.intValue())) {
                return f;
            }
        }
        return null;
    }

    @Override
    public List<FormaPagamento> findAll() throws WebServiceException {
        List<FormaPagamento> formasPagamento = new ArrayList<>();
        List<FormaPagamentoET> vetor = webServiceRepository.buscaFormasPagamento();
        for (FormaPagamentoET f : vetor) {
            formasPagamento.add(new FormaPagamento(f.getID(), f.getNome().getValue()));
        }
        return formasPagamento;
    }
}
