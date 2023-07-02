package br.com.jeweb.vendas.service;

import br.com.jeweb.core.filter.AbstractFilter;
import br.com.jeweb.core.repository.AbstractRepository;
import br.com.jeweb.core.service.AbstractService;
import br.com.jeweb.vendas.entity.webservice.MapaProducao;
import br.com.jeweb.vendas.repository.WebServiceRepository;
import br.com.jeweb.vendas.util.Util;
import org.datacontract.schemas._2004._07.incopa_servicos_integracao_utilidade.ConsumoMapaDeProducaoET;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.xml.ws.WebServiceException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Dependent
public class MapaProducaoService extends AbstractService<MapaProducao> {

    @Inject
    private WebServiceRepository webServiceRepository;



    public List<MapaProducao> buscaMapasProducao(LocalDate dataInicial, LocalDate dataFinal) throws WebServiceException {
        List<ConsumoMapaDeProducaoET> vetor = webServiceRepository.buscaMapaProducao(Util.localDateToXMLGC(dataInicial), Util.localDateToXMLGC(dataFinal));
        List<MapaProducao> mapasProducao = new ArrayList<>();
        for (ConsumoMapaDeProducaoET c : vetor) {
            MapaProducao mapaProducao = new MapaProducao();
            mapaProducao.setId(c.getID());
            mapaProducao.setData(Util.xmlGCToLocalDate(c.getData()));
            mapaProducao.setObjeto(c.getObjeto().getValue());
            mapaProducao.setQuantidade(c.getQuantidade());
            mapaProducao.setUnidade(c.getUnidade().getValue());
            mapasProducao.add(mapaProducao);
        }
        return mapasProducao;
    }

    @Override
    public AbstractFilter<MapaProducao> getFilter() {
        return null;
    }

    @Override
    public AbstractRepository<MapaProducao> getRepository() {
        return null;
    }
}
