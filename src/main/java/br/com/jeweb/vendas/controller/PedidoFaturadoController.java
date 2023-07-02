package br.com.jeweb.vendas.controller;

import br.com.jeweb.core.controller.AbstractController;
import br.com.jeweb.core.exception.ResourceNotFoundException;
import br.com.jeweb.vendas.datamodel.PedidoDataModel;
import br.com.jeweb.vendas.entity.Pedido;
import br.com.jeweb.vendas.service.PedidoService;
import br.com.jeweb.vendas.util.url.URLProviderImpl;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.ws.WebServiceException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class PedidoFaturadoController extends AbstractController<Pedido, PedidoService, PedidoDataModel> {

    private List<Pedido> pedidos;
    private LocalDate dataInicial;
    private LocalDate dataFinal;

    @Inject
    private UsuarioLogadoController usuarioLogadoController;

    @Inject
    public PedidoFaturadoController(PedidoService pedidoService, PedidoDataModel pedidoDataModel) {
        super(pedidoService, pedidoDataModel);
    }

    @Override
    @PostConstruct
    public void init() {

    }

    @Override
    public URLProviderImpl getUrlProvider() {
        return (URLProviderImpl) this.getUrlProviderInstance();
    }

    public void load(String notaFiscal) {
        try {
            setEntidade(getService().buscarPedidoFaturado(notaFiscal));
        }
        catch (WebServiceException ex) {
            this.getMessagesProvider().reportErrorMessage("Webservice offline.");
        }
        catch (ResourceNotFoundException ex) {
            this.getUrlProvider().redirectFromContext(this.getUrlProvider().getUrlResourceNotFound());
        }
    }

    public void buscarPedidos() {
        setPedidos(getService().buscarPedidosFaturados(getDataInicial(), getDataFinal(), usuarioLogadoController.getUsuarioLogado().getIdTop()));
    }

    public void limpar() {
        setDataInicial(null);
        setDataFinal(null);
        setPedidos(new ArrayList<>());
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

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
}
