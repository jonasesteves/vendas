package br.com.jeweb.vendas.controller;

import br.com.jeweb.core.controller.AbstractController;
import br.com.jeweb.vendas.datamodel.ClienteDataModel;
import br.com.jeweb.vendas.entity.Pedido;
import br.com.jeweb.vendas.entity.webservice.Cliente;
import br.com.jeweb.vendas.service.ClienteService;
import br.com.jeweb.vendas.service.PedidoService;
import br.com.jeweb.vendas.util.url.URLProviderImpl;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Null;
import java.util.List;

@Named
@ViewScoped
public class ClienteController extends AbstractController<Cliente, ClienteService, ClienteDataModel> {

    private List<Cliente> clientes;

    @Inject
    private PedidoService pedidoService;

    @Inject
    private UsuarioLogadoController usuarioLogadoController;

    @Inject
    public ClienteController(ClienteService clienteService, ClienteDataModel clienteDataModel) {
        super(clienteService, clienteDataModel);
    }

    @Override
    @PostConstruct
    public void init() {
        setClientes(usuarioLogadoController.getClientesVendedor());
    }

    @Override
    public URLProviderImpl getUrlProvider() {
        return (URLProviderImpl) this.getUrlProviderInstance();
    }

    public void load(String id) {
        for (Cliente c : getClientes()) {
            if (c.getId().equals(Integer.parseInt(id))) {
                setEntidade(c);
                break;
            }
        }
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Pedido> buscarUltimosPedidos() {
        try {
            return pedidoService.buscarUltimosPedidos(usuarioLogadoController.getUsuarioLogado().getIdTop(), getEntidade().getId());
        }
        catch (NullPointerException ex) {
            this.getUrlProvider().redirectFromContext(this.getUrlProvider().getUrlResourceNotFound());
        }
        return null;
    }
}
