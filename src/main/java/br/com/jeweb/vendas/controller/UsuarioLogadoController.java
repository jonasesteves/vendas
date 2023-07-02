package br.com.jeweb.vendas.controller;

import br.com.jeweb.core.controller.AbstractController;
import br.com.jeweb.core.util.url.URLProvider;
import br.com.jeweb.vendas.datamodel.UsuarioDataModel;
import br.com.jeweb.vendas.entity.Usuario;
import br.com.jeweb.vendas.entity.webservice.*;
import br.com.jeweb.vendas.service.*;
import br.com.jeweb.vendas.util.url.URLProviderImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceException;
import java.time.LocalDateTime;
import java.util.List;

@Named
@SessionScoped
public class UsuarioLogadoController extends AbstractController<Usuario, UsuarioService, UsuarioDataModel> {

    private Usuario usuarioLogado = new Usuario();
    private boolean isRepresentante;
    private boolean isAdmin;
    private boolean isComercial;
    private List<Cliente> clientesVendedor;
    private List<Empresa> empresas;
    private List<TipoBonificacao> tiposBonificacao;
    private List<AgenteCobrador> agentesCobradores;
    private List<TipoCobranca> tiposCobranca;
    private List<FormaPagamento> formasPagamento;

    @Inject
    private UsuarioService usuarioService;

    @Inject
    private ClienteService clienteService;

    @Inject
    private EmpresaService empresaService;

    @Inject
    private TipoBonificacaoService tipoBonificacaoService;

    @Inject
    private AgenteCobradorService agenteCobradorService;

    @Inject
    private TipoCobrancaService tipoCobrancaService;

    @Inject
    private FormaPagamentoService formaPagamentoService;

    /**
     * Carrega os dados constantemente usados para evitar repetição de consulta ao webservice.
     */
    @PostConstruct
    public void init() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.usuarioLogado = usuarioService.getByEmail(user.getUsername());
        this.usuarioLogado.setUltimoLogin(LocalDateTime.now());
        usuarioService.update(this.usuarioLogado);
        isRepresentante = this.usuarioLogado.getTipoUsuario().getRole().equalsIgnoreCase("ROLE_Representante");
        isAdmin = this.usuarioLogado.getTipoUsuario().getRole().equalsIgnoreCase("ROLE_Administrador");
        isComercial = this.usuarioLogado.getTipoUsuario().getRole().equalsIgnoreCase("ROLE_Comercial");

        try {
            setEmpresas(empresaService.findAll());
            setTiposBonificacao(tipoBonificacaoService.findAll());
            setAgentesCobradores(agenteCobradorService.findAll());
            setTiposCobranca(tipoCobrancaService.findAll());
            setFormasPagamento(formaPagamentoService.findAll());

            if (this.isRepresentante) {
                setClientesVendedor(clienteService.buscaClientesVendedor(this.usuarioLogado.getIdTop()));
            }
        }
        catch (WebServiceException ex) {
            this.getMessagesProvider().reportErrorMessage("Webservice offline. Alguns dados podem não funcionar corretamente.");
        }
    }

    @Override
    public URLProvider getUrlProvider() {
        return (URLProviderImpl) this.getUrlProviderInstance();
    }

    public void deslogar() {
        SecurityContextHolder.getContext().setAuthentication(null);
        invalidaSessao();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(this.getUrlProvider().getUrlLogout());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void invalidaSessao() {
        FacesContext f = FacesContext.getCurrentInstance();
        HttpSession sessao = (HttpSession) f.getExternalContext().getSession(false);
        sessao.invalidate();
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public boolean isRepresentante() {
        return isRepresentante;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isComercial() {
        return isComercial;
    }

    public List<Cliente> getClientesVendedor() {
        return clientesVendedor;
    }

    public void setClientesVendedor(List<Cliente> clientesVendedor) {
        this.clientesVendedor = clientesVendedor;
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

    public List<AgenteCobrador> getAgentesCobradores() {
        return agentesCobradores;
    }

    public void setAgentesCobradores(List<AgenteCobrador> agentesCobradores) {
        this.agentesCobradores = agentesCobradores;
    }

    public List<TipoCobranca> getTiposCobranca() {
        return tiposCobranca;
    }

    public void setTiposCobranca(List<TipoCobranca> tiposCobranca) {
        this.tiposCobranca = tiposCobranca;
    }

    public List<FormaPagamento> getFormasPagamento() {
        return formasPagamento;
    }

    public void setFormasPagamento(List<FormaPagamento> formasPagamento) {
        this.formasPagamento = formasPagamento;
    }
}
