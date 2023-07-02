package br.com.jeweb.vendas.controller;

import br.com.jeweb.core.controller.AbstractController;
import br.com.jeweb.core.exception.ResourceNotFoundException;
import br.com.jeweb.vendas.datamodel.PedidoDataModel;
import br.com.jeweb.vendas.entity.Pedido;
import br.com.jeweb.vendas.service.PedidoService;
import br.com.jeweb.vendas.util.url.URLProviderImpl;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.ws.WebServiceException;
import java.util.List;

@Named
@ViewScoped
public class PedidoAbertoController extends AbstractController<Pedido, PedidoService, PedidoDataModel> {

    private List<Pedido> pedidos;

    @Inject
    private UsuarioLogadoController usuarioLogadoController;

    @Inject
    private PedidoAbertoController(PedidoService pedidoService, PedidoDataModel pedidoDataModel) {
        super(pedidoService, pedidoDataModel);
    }

    @Override
    @PostConstruct
    public void init() {
        try {
            setPedidos(getService().buscarPedidosAbertos(usuarioLogadoController.getUsuarioLogado().getIdTop()));
        }
        catch (WebServiceException ex) {
            this.getMessagesProvider().reportErrorMessage("Webservice offline.");
        }
    }

    public void load(String id) {
        try {
            setEntidade(getService().buscarPedidoAberto(id));
        }
        catch (WebServiceException ex) {
            this.getMessagesProvider().reportErrorMessage("Webservice offline.");
        }
        catch (ResourceNotFoundException ex) {
            this.getUrlProvider().redirectFromContext(this.getUrlProvider().getUrlResourceNotFound());
        }
    }

    @Override
    public URLProviderImpl getUrlProvider() {
        return (URLProviderImpl) this.getUrlProviderInstance();
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}
