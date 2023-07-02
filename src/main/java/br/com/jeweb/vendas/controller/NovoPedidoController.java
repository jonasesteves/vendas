package br.com.jeweb.vendas.controller;

import br.com.jeweb.core.controller.AbstractController;
import br.com.jeweb.vendas.datamodel.PedidoDataModel;
import br.com.jeweb.vendas.entity.Item;
import br.com.jeweb.vendas.entity.Pedido;
import br.com.jeweb.vendas.entity.Status;
import br.com.jeweb.vendas.entity.webservice.*;
import br.com.jeweb.vendas.exception.ApplicationException;
import br.com.jeweb.vendas.exception.PedidoSemItemException;
import br.com.jeweb.vendas.exception.ResourceNotFoundException;
import br.com.jeweb.vendas.service.ClienteService;
import br.com.jeweb.vendas.service.ItemService;
import br.com.jeweb.vendas.service.PedidoService;
import br.com.jeweb.vendas.service.RestricoesCobrancaService;
import br.com.jeweb.vendas.util.url.URLProviderImpl;
import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.ws.WebServiceException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Named
@ViewScoped
public class NovoPedidoController extends AbstractController<Pedido, PedidoService, PedidoDataModel> {

    private List<Cliente> clientes;
    private List<Empresa> empresas;
    private List<TipoBonificacao> tiposBonificacao;
    private Set<AgenteCobrador> agentesCobradores;
    private Set<TipoCobranca> tiposCobranca;
    private Set<FormaPagamento> formasPagamento;
    private List<RestricoesCobranca> restricoesCobrancas;
    private Item item = new Item();
    private List<Item> itens = new ArrayList<>();
    private Cliente cliente;
    private boolean exibeCampoBuscarCliente = true;
    private boolean exibePainelPedido = false;
    private boolean bloqueiaTipoPedido = true;
    private boolean venda = true;
    private boolean bonificacao = true;
    private boolean liberarNovoItem = false;
    private Pedido pedido = new Pedido();

    @Inject
    private RestricoesCobrancaService restricoesCobrancaService;

    @Inject
    private UsuarioLogadoController usuarioLogadoController;

    @Inject
    private ItemService itemService;

    @Inject
    private ClienteService clienteService;

    @Inject
    public NovoPedidoController(PedidoService pedidoService, PedidoDataModel pedidoDataModel) {
        super(pedidoService, pedidoDataModel);
    }

    @Override
    @PostConstruct
    public void init() {
        try {
            this.clientes = usuarioLogadoController.getClientesVendedor();
            this.empresas = usuarioLogadoController.getEmpresas();
            this.tiposBonificacao = usuarioLogadoController.getTiposBonificacao();
        }
        catch (WebServiceException ex) {
            this.getMessagesProvider().reportErrorMessage("Web Service Offline. Entre em contato com o TI.");
        }
    }

    @Override
    public URLProviderImpl getUrlProvider() {
        return (URLProviderImpl) this.getUrlProviderInstance();
    }

    public String cancelar() {
        try {
            this.setPedido(this.pedido.getClass().newInstance());
            exibeCampoBuscarCliente = true;
            exibePainelPedido = false;
            return this.getUrlProvider().getUrlIndexNovoPedido();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Cliente> listarClientes(String str) {
        try {
            List<Cliente> clientesFiltrados = new ArrayList<Cliente>();
            for (Cliente c : clientes) {
                if ((c.getNome().toLowerCase().startsWith(str.toLowerCase()) || c.getId().toString().startsWith(str.trim())) && !c.getSituacao().equalsIgnoreCase("Bloqueado")) {
                    clientesFiltrados.add(c);
                }
            }
            return clientesFiltrados;
        }
        catch (WebServiceException ex) {
            this.getMessagesProvider().reportErrorMessage("Web Service Offline. Entre em contato com o TI.");
        }
        return null;
    }

    public List<String> listarItensNome(String str) {
        limparItem();
        try {
            List<String> estoques = new ArrayList<>();
            for (Item i : getItens()) {
                if (i.getNome().toLowerCase().contains(str.toLowerCase())) {
                    estoques.add(i.getNome());
                }
            }
            return estoques;
        }
        catch (WebServiceException ex) {
            this.getMessagesProvider().reportErrorMessage("Web Service Offline. Entre em contato com o TI.");
        }
        catch (NullPointerException ex) {
            this.getMessagesProvider().reportErrorMessage("Por favor, selecione uma Empresa e um Tipo de Pedido.");
        }
        return null;
    }

    public List<Integer> listarItensId(Integer id) {
        limparItem();
        try {
            List<Integer> estoques = new ArrayList<>();
            for (Item i : getItens()) {
                if (i.getIdEstoque().toString().startsWith(id.toString())) {
                    estoques.add(i.getIdEstoque());
                }
            }
            return estoques;
        }
        catch (WebServiceException ex) {
            this.getMessagesProvider().reportErrorMessage("Web Service Offline. Entre em contato com o TI.");
        }
        catch (NullPointerException ex) {
            this.getMessagesProvider().reportErrorMessage("Por favor, selecione uma Empresa e um Tipo de Pedido.");
        }
        return null;
    }

    public void liberarAddItem() {
        setLiberarNovoItem(true);
    }

    public void calculaValores() {
        BigDecimal valorTabelado = item.getValorTabelado();
        BigDecimal valorVenda = item.getValorUnitario();
//        BigDecimal valorDescontado = valorTabelado - valorVenda;
        BigDecimal valorDescontado = valorTabelado.subtract(valorVenda);
//        BigDecimal porcentagem = (valorDescontado * 100) / valorTabelado;
        BigDecimal porcentagem = valorDescontado.multiply(BigDecimal.valueOf(100.00)).divide(valorTabelado, RoundingMode.HALF_UP);

        item.setDesconto(porcentagem);
//        item.setValorTotal(item.getQuantidade() * valorVenda);
        item.setValorTotal(item.getQuantidade().multiply(valorVenda));
    }

    public void calculaTotalPedido() {
        BigDecimal quantidadeItens = BigDecimal.valueOf(pedido.getItens().size());

        BigDecimal valorTotal = BigDecimal.valueOf(0);
        BigDecimal somaDesconto = BigDecimal.valueOf(0);
        for (Item i : pedido.getItens()) {
            somaDesconto = somaDesconto.add(i.getDesconto());
            valorTotal = valorTotal.add(i.getValorTotal());
        }

        if (!quantidadeItens.equals(BigDecimal.valueOf(0))) {
            pedido.setDescontoMedio(somaDesconto.divide(quantidadeItens, 2, RoundingMode.HALF_UP));
            pedido.setValorTotal(valorTotal);
        }
        else {
            pedido.setDescontoMedio(BigDecimal.valueOf(0));
            pedido.setValorTotal(BigDecimal.valueOf(0));
        }

    }

    public void limparItem() {
        setItem(new Item());
    }

    public void adicionarItem() {
        if (item.getIdEstoque() != null
                && item.getNome() != null
                && item.getQuantidade() != null
                && item.getValorTabelado() != null
                && item.getValorUnitario() != null
                && item.getDesconto() != null
                && item.getValorTotal()!= null) {
            boolean itemExiste = false;
            for (Item i : this.pedido.getItens()) {
                if (i.getIdEstoque().equals(item.getIdEstoque())) {
                    itemExiste = true;
                    break;
                }
            }
            if (!itemExiste) {
                this.pedido.getItens().add(item);
                calculaTotalPedido();
                limparItem();
            } else {
                this.getMessagesProvider().reportErrorMessage("O pedido já contém esse item.");
            }
        }
        else {
            this.getMessagesProvider().reportErrorMessage("Por favor, preencha todos os dados do novo item.");
        }
    }

    public void removerItem(Integer idEstoque) {
        Iterator<Item> it = this.pedido.getItens().iterator();
        while (it.hasNext()) {
            Item i = it.next();
            if (i.getIdEstoque().equals(idEstoque)) {
                it.remove();
            }
        }
        calculaTotalPedido();
    }

    public void completaCampos(SelectEvent event) {
        for(Item i : getItens()) {
            if (i.getNome().equalsIgnoreCase(item.getNome()) || i.getIdEstoque().equals(item.getIdEstoque())) {
                item.setIdEstoque(i.getIdEstoque());
                item.setNome(i.getNome());
                item.setValorTabelado(i.getValorTabelado());
                item.setValorUnitario(i.getValorTabelado());
                break;
            }
        }
    }

    public String salvar() {
        String idCliente = null;
        try {
            if (this.pedido.getId() == null) {
                this.pedido.setIdCliente(this.pedido.getCliente().getId());
                this.pedido.setIdEmpresa(this.pedido.getEmpresa().getId());
                this.pedido.setUsuario(usuarioLogadoController.getUsuarioLogado());
                if (pedido.getIdOperacao().equals(133)) {
                    this.pedido.setIdTipoBonificacao(this.pedido.getTipoBonificacao().getId());
                }
                if (pedido.getIdOperacao().equals(97)) {
                    this.pedido.setIdAgenteCobrador(this.pedido.getAgenteCobrador().getId());
                    this.pedido.setIdTipoCobranca(this.pedido.getTipoCobranca().getId());
                    this.pedido.setIdFormaPagamento(this.pedido.getFormaPagamento().getId());
                }
                this.pedido.setDataEmissao(LocalDate.now());
                this.pedido.setHoraEmissao(LocalTime.now());
                this.pedido.setStatus(Status.EM_ESPERA);
                this.pedido.setEnviado(false);
                getService().salvarPedido(this.pedido);
                idCliente = this.pedido.getCliente().getId().toString();
                this.pedido = new Pedido();
                this.getMessagesProvider().reportInfoMessage("Pedido enviado com sucesso.");
            }
            else {
                getService().update(this.pedido);
                this.getMessagesProvider().reportInfoMessage("Pedido atualizado com sucesso.");
            }
            return this.getUrlProvider().getUrlOutroPedidoCliente(idCliente);
        }
        catch (PedidoSemItemException ex) {
            this.getMessagesProvider().reportErrorMessage("A lista de itens não pode estar vazia.");
        }
        catch (ApplicationException ex) {
            this.getMessagesProvider().reportErrorMessage("Ocorreu um problema ao tentar salvar o Pedido.");
        }
        return null;
    }

    public void carregarCliente(String idCliente) {
        if (idCliente != null && !idCliente.isEmpty()) {
            for (Cliente c : getClientes()) {
                if (c.getId().equals(Integer.parseInt(idCliente))) {
                    setCliente(c);
                    break;
                }
            }
            try {
                iniciarPedido();
            }
            catch (NullPointerException | ResourceNotFoundException ex) {
                this.getUrlProvider().redirectFromContext(this.getUrlProvider().getUrlResourceNotFound());
            }
        }
    }

    public void iniciarPedido() throws ResourceNotFoundException {
        if (cliente.getSituacao().equals("Bloqueado")) {
            throw new ResourceNotFoundException();
        }
        try {
            getPedido().setIdCliente(cliente.getId());
            getPedido().setCliente(cliente);
            this.restricoesCobrancas = restricoesCobrancaService.buscaRestricoesCobrancaCliente(cliente.getId());
            exibeCampoBuscarCliente = false;
            exibePainelPedido = true;
        }
        catch (WebServiceException ex) {
            this.getMessagesProvider().reportErrorMessage("Webservice offline.");
        }
    }

    public void vendaBonificacao() {
        if (getPedido().getIdOperacao().equals(97)) {
            setVenda(true);
            setBonificacao(false);
        }
        if (getPedido().getIdOperacao().equals(133)) {
            setVenda(false);
            setBonificacao(true);
        }
        setItens(itemService.itensVendedor(cliente.getId(), usuarioLogadoController.getUsuarioLogado().getIdTop(), cliente.getIdAreaVenda(), getPedido().getEmpresa().getId(), getPedido().getIdOperacao()));
    }

    public Set<AgenteCobrador> buscaAgentesCobradores() {
        Set<AgenteCobrador> agentesCobradores = new HashSet<>();
        for (RestricoesCobranca r : this.restricoesCobrancas) {
            agentesCobradores.add(r.getAgenteCobrador());
        }
        return agentesCobradores;
    }

    public void buscaTiposCobranca() {
        for (RestricoesCobranca r : this.restricoesCobrancas) {
            if (r.getEmpresa() != null) {
                if (r.getAgenteCobrador().equals(getPedido().getAgenteCobrador()) && r.getEmpresa().equals(getPedido().getEmpresa())) {
                    this.tiposCobranca.add(r.getTipoCobranca());
                }
            }
        }
    }

    public void buscaFormasPagamento() {
        for (RestricoesCobranca r : this.restricoesCobrancas) {
            if (r.getAgenteCobrador().equals(getPedido().getAgenteCobrador()) && r.getTipoCobranca().equals(getPedido().getTipoCobranca())) {
                this.formasPagamento.add(r.getFormaPagamento());
            }
        }
    }

    public void limparCamposPagamento() {
        setBloqueiaTipoPedido(false);
        this.agentesCobradores = buscaAgentesCobradores();
        this.tiposCobranca = new HashSet<>();
        this.formasPagamento = new HashSet<>();
    }

    public boolean isExibeCampoBuscarCliente() {
        return exibeCampoBuscarCliente;
    }

    public void setExibeCampoBuscarCliente(boolean exibeCampoBuscarCliente) {
        this.exibeCampoBuscarCliente = exibeCampoBuscarCliente;
    }

    public boolean isExibePainelPedido() {
        return exibePainelPedido;
    }

    public void setExibePainelPedido(boolean exibePainelPedido) {
        this.exibePainelPedido = exibePainelPedido;
    }

    public boolean isBloqueiaTipoPedido() {
        return bloqueiaTipoPedido;
    }

    public void setBloqueiaTipoPedido(boolean bloqueiaTipoPedido) {
        this.bloqueiaTipoPedido = bloqueiaTipoPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<Empresa> empresas) {
        this.empresas = empresas;
    }

    public List<TipoBonificacao> getTiposBonificacao() {
        return tiposBonificacao;
    }

    public void setTiposBonificacao(List<TipoBonificacao> tiposBonificacao) {
        this.tiposBonificacao = tiposBonificacao;
    }

    public Set<AgenteCobrador> getAgentesCobradores() {
        return agentesCobradores;
    }

    public void setAgentesCobradores(Set<AgenteCobrador> agentesCobradores) {
        this.agentesCobradores = agentesCobradores;
    }

    public Set<TipoCobranca> getTiposCobranca() {
        return tiposCobranca;
    }

    public void setTiposCobranca(Set<TipoCobranca> tiposCobranca) {
        this.tiposCobranca = tiposCobranca;
    }

    public boolean isVenda() {
        return venda;
    }

    public void setVenda(boolean venda) {
        this.venda = venda;
    }

    public boolean isBonificacao() {
        return bonificacao;
    }

    public void setBonificacao(boolean bonificacao) {
        this.bonificacao = bonificacao;
    }

    public Set<FormaPagamento> getFormasPagamento() {
        return formasPagamento;
    }

    public void setFormasPagamento(Set<FormaPagamento> formasPagamento) {
        this.formasPagamento = formasPagamento;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    public boolean isLiberarNovoItem() {
        return liberarNovoItem;
    }

    public void setLiberarNovoItem(boolean liberarNovoItem) {
        this.liberarNovoItem = liberarNovoItem;
    }

}
