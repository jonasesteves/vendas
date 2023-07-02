package br.com.jeweb.vendas.util.url;

import br.com.jeweb.core.util.url.URLProvider;
import br.com.jeweb.core.util.url.URLUtils;
import br.com.jeweb.vendas.util.page.PageProviderImpl;

import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


@Dependent
public class URLProviderImpl implements URLProvider, Serializable {

    private static final ResourceBundle rs = ResourceBundle.getBundle("application");

    public URLProviderImpl() {
    }

    public void redirectFromContext(String url) {
        URLUtils.redirect(url);
    }

    @Override
    public String getUrlIndex() {
        return URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_INDEX);
    }

    @Override
    public String getUrlResourceNotFound() {
        return URLUtils.getDefaultURL(PageProviderImpl.PAGINA_RESOURCE_NOT_FOUND);
    }

    @Override
    public String getUrlLogout() {
        return rs.getString("app.context.login");
    }


    //PESSOAS
    public String getUrlIndexPessoa() {
        return URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_INDEX_PESSOA);
    }

    public String getUrlCadastrarPessoa() {
        return URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_CADASTRAR_PESSOA);
    }

    public String getUrlEditarPessoa(String id) {
        return URLUtils.getURLWithParam(URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_EDITAR_PESSOA), "id", id);
    }

    public String getUrlDetalharPessoa(String id) {
        return URLUtils.getURLWithParam(URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_DETALHAR_PESSOA), "id", id);
    }
    //FIM PESSOAS


    //USUARIOS
    public String getUrlIndexUsuario() {
        return URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_INDEX_USUARIO);
    }

    public String getUrlEditarUsuario(String id) {
        return URLUtils.getURLWithParam(URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_EDITAR_USUARIO), "id", id);
    }
    //FIM USUARIOS


    //PEDIDOS PENDENTES
    public String getUrlIndexPedidoPendente() {
        return URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_INDEX_PEDIDO_PENDENTE);
    }

    public String getUrlEditarPedidoPendente(String id) {
        return URLUtils.getURLWithParam(URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_EDITAR_PEDIDO_PENDENTE), "id", id);
    }
    //FIM PEDIDOS PENDENTES


    //NOVO PEDIDO
    public String getUrlIndexNovoPedido() {
        return URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_INDEX_NOVO_PEDIDO);
    }

    public String getUrlIndexNovoPedidoCliente(String id) {
        return URLUtils.getURLWithParam(URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_INDEX_NOVO_PEDIDO), "c", id);
    }

    public String getUrlOutroPedidoCliente(String id) {
        return URLUtils.getURLWithParam(URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_INDEX_OUTRO_PEDIDO), "c", id);
    }
    //FIM NOVO PEDIDO


    //TODOS OS PEDIDOS
    public String getUrlIndexPedido() {
        return URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_INDEX_PEDIDO);
    }

    public String getUrlDetalharPedido(String id) {
        return URLUtils.getURLWithParam(URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_DETALHAR_PEDIDO), "id", id);
    }
    //FIM TODOS OS PEDIDOS


    //PEDIDOS EM ABERTO
    public String getUrlIndexPedidoAberto() {
        return URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_INDEX_PEDIDO_ABERTO);
    }

    public String getUrlDetalharPedidoAberto(String id) {
        return URLUtils.getURLWithParam(URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_DETALHAR_PEDIDO_ABERTO), "id", id);
    }
    //FIM PEDIDOS EM ABERTO


    //PEDIDOS EMBARCADOS
    public String getUrlIndexPedidoEmbarcado() {
        return URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_INDEX_PEDIDO_EMBARCADO);
    }

    public String getUrlDetalharPedidoEmbarcado(String id) {
        return URLUtils.getURLWithParam(URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_DETALHAR_PEDIDO_EMBARCADO), "id", id);
    }
    //FIM PEDIDOS EMBARCADOS


    //PEDIDOS FATURADOS
    public String getUrlIndexPedidoFaturado() {
        return URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_INDEX_PEDIDO_FATURADO);
    }

    public String getUrlDetalharPedidoFaturado(String id) {
        return URLUtils.getURLWithParam(URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_DETALHAR_PEDIDO_FATURADO), "id", id);
    }
    //FIM PEDIDOS FATURADOS


    //ESTOQUE
    public String getUrlIndexMapaProducao() {
        return URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_INDEX_MAPA_PRODUCAO);
    }
    //FIM ESTOQUE


    //COMISSÃO
    public String getUrlIndexComissao() {
        return URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_INDEX_COMISSAO);
    }
    //FIM COMISSÃO


    //CONTAS A RECEBER
    public String getUrlIndexContasReceber() {
        return URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_INDEX_CONTAS_RECEBER);
    }
    //FIM CONTAS A RECEBER


    //CONTATOS COM CLIENTE
    public String getUrlEditarContatoCliente(String param1, String param2, String param3, String param4) {
        Map<String, String> map = new HashMap<>();
        map.put("ve", param1);
        map.put("nf", param2);
        map.put("dt", param3);
        map.put("cl", param4);
        return URLUtils.getURLWithParams(URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_EDITAR_CONTATO_CLIENTE), map);
    }
    //FIM CONTATOS COM CLIENTE


    //CLIENTES
    public String getUrlIndexCliente() {
        return URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_INDEX_CLIENTE);
    }

    public String getUrlDetalharCliente(String id) {
        return URLUtils.getURLWithParam(URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_DETALHAR_CLIENTE), "id", id);
    }
    //FIM CLIENTES

    //FRETE
    public String getUrlIndexFrete() {
        return URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_INDEX_FRETE);
    }

    public String getUrlDetalharFrete(String id, String idm) {
        FacesContext f = FacesContext.getCurrentInstance();
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("idm", idm);
        return URLUtils.getURLWithParams(URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_DETALHAR_FRETE), map);
    }

    public String getUrlCadastrarFrete(String id) {
        String s;
        if (id != null) {
            s = id;
        } else {
            s = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cpf");
        }
        return URLUtils.getURLWithParam(URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_CADASTRAR_FRETE), "id", s);
    }

    public String getUrlEditarFrete(String id) {
        return URLUtils.getURLWithParam(URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_EDITAR_FRETE), "id", id);
    }

//    public String getUrlNovoFreteComMotorista(String id) {
//        return URLUtils.getURLWithParam(URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_NOVO_FRETE_COM_MOTORISTA), "id", id);
//    }
    //FIM FRETE

    //MOTORISTA
    public String getUrlIndexMotorista() {
        return URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_INDEX_MOTORISTA);
    }

    public String getUrlCadastrarMotorista() {
        return URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_CADASTRAR_MOTORISTA);
    }

    public String getUrlDetalharMotorista(String id) {
        return URLUtils.getURLWithParam(URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_DETALHAR_MOTORISTA), "id", id);
    }

    public String getUrlEditarMotorista(String id) {
        return URLUtils.getURLWithParam(URLUtils.getAppRedirectURL(PageProviderImpl.PAGINA_EDITAR_MOTORISTA), "id", id);
    }
    //FIM MOTORISTA
}
