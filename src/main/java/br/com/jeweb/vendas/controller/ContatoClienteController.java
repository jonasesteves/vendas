package br.com.jeweb.vendas.controller;

import br.com.jeweb.core.controller.AbstractController;
import br.com.jeweb.vendas.datamodel.ContatoClienteDataModel;
import br.com.jeweb.vendas.entity.webservice.Cliente;
import br.com.jeweb.vendas.entity.webservice.ContaReceber;
import br.com.jeweb.vendas.entity.webservice.ContatoCliente;
import br.com.jeweb.vendas.service.ClienteService;
import br.com.jeweb.vendas.service.ContaReceberService;
import br.com.jeweb.vendas.service.ContatoClienteService;
import br.com.jeweb.vendas.util.url.URLProviderImpl;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.ws.WebServiceException;
import java.time.LocalDate;
import java.util.List;

@Named
@ViewScoped
public class ContatoClienteController extends AbstractController<ContatoCliente, ContatoClienteService, ContatoClienteDataModel> {

    private List<ContatoCliente> contatosCliente;
    private List<ContaReceber> contasReceber;
    private Cliente cliente;

    @Inject
    private ClienteService clienteService;

    @Inject
    private ContaReceberService contaReceberService;

    @Inject
    private UsuarioLogadoController usuarioLogadoController;

    @Inject
    public ContatoClienteController(ContatoClienteService contatoClienteService, ContatoClienteDataModel contatoClienteDataModel) {
        super(contatoClienteService, contatoClienteDataModel);
    }

    @Override
    @PostConstruct
    public void init() {
        setEntidade(new ContatoCliente());
    }

    @Override
    public URLProviderImpl getUrlProvider() {
        return (URLProviderImpl) this.getUrlProviderInstance();
    }

    public void load(String ve, String nf, String dt, String cl) {
        try {
            setContasReceber(contaReceberService.buscaContasReceberPorNF(nf, dt, ve));
            setContatosCliente(getService().buscaContatosCliente(Integer.parseInt(cl)));
            setCliente(clienteService.findOne(Long.parseLong(cl)));
            getEntidade().setUsuario(usuarioLogadoController.getUsuarioLogado().getNome());
            getEntidade().setData(LocalDate.now());
            getEntidade().setCliente(getCliente());
        }
        catch (WebServiceException ex) {
            this.getMessagesProvider().reportErrorMessage("WebService offline.");
        }
    }

    public String enviarContato() {
        try {
            getService().enviarContato(getEntidade());
            this.getMessagesProvider().reportInfoMessage("Contato enviado com sucesso!");
            return this.getUrlProvider().getUrlIndexContasReceber();
        }
        catch (WebServiceException ex) {
            this.getMessagesProvider().reportErrorMessage("WebService offline.");
        }
        return null;
    }

    public List<ContatoCliente> getContatosCliente() {
        return contatosCliente;
    }

    public void setContatosCliente(List<ContatoCliente> contatosCliente) {
        this.contatosCliente = contatosCliente;
    }

    public List<ContaReceber> getContasReceber() {
        return contasReceber;
    }

    public void setContasReceber(List<ContaReceber> contasReceber) {
        this.contasReceber = contasReceber;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
