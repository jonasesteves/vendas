package br.com.jeweb.vendas.controller;

import br.com.jeweb.core.controller.AbstractController;
import br.com.jeweb.core.exception.ResourceNotFoundException;
import br.com.jeweb.vendas.datamodel.PedidoDataModel;
import br.com.jeweb.vendas.entity.Pedido;
import br.com.jeweb.vendas.entity.Status;
import br.com.jeweb.vendas.exception.ApplicationException;
import br.com.jeweb.vendas.service.PedidoService;
import br.com.jeweb.vendas.util.url.URLProviderImpl;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.ws.WebServiceException;
import java.math.BigInteger;
import java.util.List;

@Named
@ViewScoped
public class PedidoPendenteController extends AbstractController<Pedido, PedidoService, PedidoDataModel> {

    private List<Pedido> pedidosPendentes;
    private Integer totalPedidosPendentes;

    @Inject
    public PedidoPendenteController(PedidoService pedidoService, PedidoDataModel pedidoDataModel) {
        super(pedidoService, pedidoDataModel);
    }

    @PostConstruct
    public void init() {
        setPedidosPendentes(getPendentes());
    }

    @Override
    public URLProviderImpl getUrlProvider() {
        return (URLProviderImpl) this.getUrlProviderInstance();
    }

    @Override
    public PedidoService getService() {
        return super.getService();
    }

    public void load(String id) {
        try {
            if (!FacesContext.getCurrentInstance().isPostback()) {
                setEntidade(this.getService().findOne(id));
            }
        } catch (ResourceNotFoundException ex) {
            this.getUrlProvider().redirectFromContext(this.getUrlProvider().getUrlResourceNotFound());
        }
        catch (WebServiceException ex) {
            this.getMessagesProvider().reportErrorMessage("WebService offline.");
        }
    }

    public List<Pedido> getPendentes() {
        try {
            return this.getService().getPendentes();
        }
        catch (WebServiceException ex) {
            ex.printStackTrace();
            this.getMessagesProvider().reportErrorMessage("WebService offline. Alguns dados podem vir incompletos.");
        }
        return null;
    }

    public String liberar() {
        try {
            this.getEntidade().setStatus(Status.LIBERADO);
            enviarPedido(getEntidade());
            getEntidade().setEnviado(true);
            getService().update(getEntidade());
            this.getMessagesProvider().reportInfoMessage("Alterações realizadas com sucesso. O pedido foi enviado.");
            return this.getUrlProvider().getUrlIndexPedidoPendente();
        }
        catch (WebServiceException ex) {
            ex.printStackTrace();
            try {
                this.getMessagesProvider().reportErrorMessage("Não foi possível enviar o pedido.");
                getService().update(getEntidade());
                return this.getUrlProvider().getUrlIndexPedidoPendente();
            }
            catch (ApplicationException e) {
                this.getMessagesProvider().reportErrorMessage("Não foi possível realizar as alterações. Tente novamente mais tarde.");
                return null;
            }
        }
        catch (ApplicationException ex) {
            this.getMessagesProvider().reportErrorMessage("Não foi possível realizar as alterações. Tente novamente mais tarde.");
        }
        return null;
    }

    public Integer enviarPedido(Pedido pedido) throws WebServiceException {
        return getService().enviarPedido(pedido);
    }

    public String cancelar() {
        try {
            this.getEntidade().setStatus(Status.CANCELADO);
            getService().update(this.getEntidade());
            this.createEntity();
            this.getMessagesProvider().reportInfoMessage("Alterações realizadas com sucesso!");
            return this.getUrlProvider().getUrlIndexPedidoPendente();
        }
        catch (ApplicationException ex) {
            this.getMessagesProvider().reportErrorMessage("Ocorreu um problema ao tentar cancelar o pedido. Tente novamente mais tarde.");
        }
        return null;
    }


    // GETTERS E SETTERS
    public List<Pedido> getPedidosPendentes() {
        return pedidosPendentes;
    }

    public void setPedidosPendentes(List<Pedido> pedidosPendentes) {
        this.pedidosPendentes = pedidosPendentes;
    }

    public BigInteger getTotalPedidosPendentes() {
        return getService().buscaTotalPedidosPendentes();
    }

}
