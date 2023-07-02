/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jeweb.vendas.controller;

import br.com.jeweb.core.controller.AbstractController;
import br.com.jeweb.core.exception.ResourceNotFoundException;
import br.com.jeweb.vendas.datamodel.PedidoDataModel;
import br.com.jeweb.vendas.entity.Item;
import br.com.jeweb.vendas.entity.Pedido;
import br.com.jeweb.vendas.entity.Status;
import br.com.jeweb.vendas.entity.Usuario;
import br.com.jeweb.vendas.entity.webservice.Cliente;
import br.com.jeweb.vendas.exception.ApplicationException;
import br.com.jeweb.vendas.filter.PedidoFilter;
import br.com.jeweb.vendas.service.PedidoService;
import br.com.jeweb.vendas.util.url.URLProviderImpl;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.ws.WebServiceException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author desenvolvimento
 */
@Named
@SessionScoped
public class PedidoController extends AbstractController<Pedido, PedidoService, PedidoDataModel> {

    private List<Pedido> pedidos;
    private Cliente cliente;
    private Usuario usuario;
    private LocalDate dataInicial;
    private LocalDate dataFinal;

    @Inject
    private PedidoFilter pedidoFilter;

    @Inject
    private UsuarioLogadoController usuarioLogadoController;

    @Inject
    public PedidoController(PedidoService pedidoService, PedidoDataModel pedidoDataModel) {
        super(pedidoService, pedidoDataModel);
    }

    @PostConstruct
    public void init() {
        if (usuarioLogadoController.isRepresentante()) {
            setUsuario(this.usuarioLogadoController.getUsuarioLogado());
        }
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

    public void buscarPedidos() {
        setPedidos(getService().buscarPedidos(getUsuario().getIdTop(), getDataInicial(), getDataFinal()));
    }

    public void limpar() {
        setUsuario(new Usuario());
        setDataInicial(null);
        setDataFinal(null);
        setPedidos(new ArrayList<>());
    }

    public String repetirPedido() {
        try {
            getEntidade().setId(null);
            getEntidade().setEnviado(false);
            getEntidade().setStatus(Status.EM_ESPERA);
            getEntidade().setDataEmissao(LocalDate.now());
            getEntidade().setHoraEmissao(LocalTime.now());

            for (Item i : getEntidade().getItens()) {
                i.setId(null);
            }

            getService().save(getEntidade());
            this.getMessagesProvider().reportInfoMessage("Pedido enviado com sucesso.");
            return this.getUrlProvider().getUrlIndex();
        }
        catch (ApplicationException ex) {
            this.getMessagesProvider().reportErrorMessage("Ocorreu um problema ao tentar salvar o Pedido.");
        }
        return null;
    }

    public boolean repetirPedido(Integer idCliente) {
        if (usuarioLogadoController.isRepresentante()) {
            for (Cliente c : usuarioLogadoController.getClientesVendedor()) {
                if (c.getId().equals(idCliente)) {
                    setCliente(c);
                    break;
                }
            }
            if (getCliente().getSituacao().equals("Ativo")) {
                return true;
            }
        }
        return false;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
