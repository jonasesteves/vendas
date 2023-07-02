package br.com.jeweb.vendas.controller;

import br.com.jeweb.core.controller.AbstractController;
import br.com.jeweb.vendas.datamodel.MapaProducaoDataModel;
import br.com.jeweb.vendas.entity.webservice.MapaProducao;
import br.com.jeweb.vendas.service.MapaProducaoService;
import br.com.jeweb.vendas.util.url.URLProviderImpl;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.ws.WebServiceException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;

@Named
@ViewScoped
public class MapaProducaoController extends AbstractController<MapaProducao, MapaProducaoService, MapaProducaoDataModel> {

    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private List<MapaProducao> mapasProducao;

    @Inject
    private MapaProducaoController(MapaProducaoService mapaProducaoService, MapaProducaoDataModel mapaProducaoDataModel) {
        super(mapaProducaoService, mapaProducaoDataModel);
    }

    @Override
    @PostConstruct
    public void init() {
        dataInicial = LocalDate.now().with(firstDayOfMonth());
        dataFinal = LocalDate.now();
        buscaMapasProducao();
    }

    @Override
    public URLProviderImpl getUrlProvider() {
        return (URLProviderImpl) this.getUrlProviderInstance();
    }

    public void limpar() {
        setDataInicial(null);
        setDataFinal(null);
        setMapasProducao(new ArrayList<>());
    }

    public void buscaMapasProducao() {
        try {
            setMapasProducao(getService().buscaMapasProducao(getDataInicial(), getDataFinal()));
        }
        catch (WebServiceException ex) {
            this.getMessagesProvider().reportErrorMessage("Webservice offline.");
        }
    }

    //GETTERS E SETTERS
    public LocalDate getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(LocalDate dataInicial) {
        this.dataInicial = dataInicial;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }

    public List<MapaProducao> getMapasProducao() {
        return mapasProducao;
    }

    public void setMapasProducao(List<MapaProducao> mapasProducao) {
        this.mapasProducao = mapasProducao;
    }
}
