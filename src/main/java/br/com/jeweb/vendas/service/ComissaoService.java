package br.com.jeweb.vendas.service;

import br.com.jeweb.core.filter.AbstractFilter;
import br.com.jeweb.core.repository.AbstractRepository;
import br.com.jeweb.core.service.AbstractService;
import br.com.jeweb.vendas.entity.webservice.Comissao;
import br.com.jeweb.vendas.repository.WebServiceRepository;
import br.com.jeweb.vendas.util.Util;
import org.datacontract.schemas._2004._07.incopa_servicos_integracao_utilidade.ComissoesET;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.xml.ws.WebServiceException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Dependent
public class ComissaoService extends AbstractService<Comissao> {

    @Inject
    private WebServiceRepository webServiceRepository;

    @Override
    public AbstractRepository<Comissao> getRepository() {
        return null;
    }

    public List<Comissao> buscaComissoes (LocalDate dataInicial, LocalDate dataFinal, Integer idVendedor) throws WebServiceException {
        List<ComissoesET> vetor = webServiceRepository.buscaComissoes(Util.localDateToXMLGC(dataInicial), Util.localDateToXMLGC(dataFinal), idVendedor);
        List<Comissao> comissoes = new ArrayList<>();
        for (ComissoesET c : vetor) {
            if (c.getObservacao().getValue().substring(0, 5).equalsIgnoreCase("Saldo")) {
                continue;
            }
            Comissao comissao = new Comissao();
            comissao.setId(c.getID());
            comissao.setData(Util.xmlGCToLocalDate(c.getDataComissao()));
            comissao.setNotaFiscal(c.getNotaFiscal());
            comissao.setObservacao(c.getObservacao().getValue());
            comissao.setValor(c.getValorComissao());
            comissoes.add(comissao);
        }
        return comissoes;
    }

    @Override
    public AbstractFilter<Comissao> getFilter() {
        return null;
    }
}
