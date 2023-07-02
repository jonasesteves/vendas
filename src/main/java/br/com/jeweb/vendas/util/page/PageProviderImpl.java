package br.com.jeweb.vendas.util.page;

import br.com.jeweb.core.util.page.PageProvider;

public enum PageProviderImpl implements PageProvider {

    PAGINA_INDEX("index"),
    PAGINA_RESOURCE_NOT_FOUND("resource-not-found"),

    PAGINA_INDEX_PESSOA("pessoa/index"),
    PAGINA_CADASTRAR_PESSOA("pessoa/cadastrar"),
    PAGINA_EDITAR_PESSOA("pessoa/editar"),
    PAGINA_DETALHAR_PESSOA("pessoa/detalhar"),

    //USUARIO
    PAGINA_INDEX_USUARIO("usuario/index"),
    PAGINA_EDITAR_USUARIO("usuario/editar"),
    PAGINA_EDITAR_CONTA_USUARIO("usuario/editar-conta"),

    //PEDIDO PENDENTE
    PAGINA_INDEX_PEDIDO_PENDENTE("pedido-pendente/index"),
    PAGINA_EDITAR_PEDIDO_PENDENTE("pedido-pendente/editar"),

    //NOVO PEDIDO
    PAGINA_INDEX_NOVO_PEDIDO("novo-pedido/index"),
    PAGINA_INDEX_OUTRO_PEDIDO("novo-pedido/novo"),

    //PEDIDO (GERAL)
    PAGINA_INDEX_PEDIDO("pedido/index"),
    PAGINA_DETALHAR_PEDIDO("pedido/detalhar"),

    //ESTOQUE
    PAGINA_INDEX_MAPA_PRODUCAO("mapa-producao/index"),

    //COMISSAO
    PAGINA_INDEX_COMISSAO("comissao/index"),

    //CONTAS A RECEBER
    PAGINA_INDEX_CONTAS_RECEBER("conta-receber/index"),

    //CONTATO CLIENTE
    PAGINA_EDITAR_CONTATO_CLIENTE("contato-cliente/index"),

    //CLIENTE
    PAGINA_INDEX_CLIENTE("cliente/index"),
    PAGINA_DETALHAR_CLIENTE("cliente/detalhar"),

    //PEDIDOS EM ABERTO
    PAGINA_INDEX_PEDIDO_ABERTO("pedido-aberto/index"),
    PAGINA_DETALHAR_PEDIDO_ABERTO("pedido-aberto/detalhar"),

    //PEDIDOS EMBARCADOS
    PAGINA_INDEX_PEDIDO_EMBARCADO("pedido-embarcado/index"),
    PAGINA_DETALHAR_PEDIDO_EMBARCADO("pedido-embarcado/detalhar"),

    //PEDIDOS FATURADOS
    PAGINA_INDEX_PEDIDO_FATURADO("pedido-faturado/index"),
    PAGINA_DETALHAR_PEDIDO_FATURADO("pedido-faturado/detalhar"),

    //FRETE
    PAGINA_INDEX_FRETE("frete/index"),
    PAGINA_CADASTRAR_FRETE("frete/cadastrar"),
    PAGINA_DETALHAR_FRETE("frete/detalhar"),
    PAGINA_EDITAR_FRETE("frete/editar"),

    //MOTORISTA
    PAGINA_INDEX_MOTORISTA("motorista/index"),
    PAGINA_CADASTRAR_MOTORISTA("motorista/cadastrar"),
    PAGINA_DETALHAR_MOTORISTA("motorista/detalhar"),
    PAGINA_EDITAR_MOTORISTA("motorista/editar");

    private String page;

    PageProviderImpl(String page) {
        this.page = page;
    }

    @Override
    public String getPage() {
        return page;
    }
}
