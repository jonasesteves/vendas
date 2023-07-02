package br.com.jeweb.vendas.util.view;

import br.com.jeweb.vendas.controller.UsuarioLogadoController;
import br.com.jeweb.vendas.util.url.URLProviderImpl;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class MenuController implements Serializable {
    private MenuModel modelMenu;

    @Inject
    private URLProviderImpl urlProviderImpl;

    @Inject
    private UsuarioLogadoController usuarioLogadoController;

    @PostConstruct
    public void init() {
        modelMenu = new DefaultMenuModel();

        boolean isAdministrador = usuarioLogadoController.getUsuarioLogado().getTipoUsuario().getRole().equalsIgnoreCase("ROLE_Administrador");
        boolean isRepresentante = usuarioLogadoController.getUsuarioLogado().getTipoUsuario().getRole().equalsIgnoreCase("ROLE_Representante");
        boolean isFinanceiro = usuarioLogadoController.getUsuarioLogado().getTipoUsuario().getRole().equalsIgnoreCase("ROLE_Financeiro");
        boolean isAlmoxarifado = usuarioLogadoController.getUsuarioLogado().getTipoUsuario().getRole().equalsIgnoreCase("ROLE_Almoxarifado");
        boolean isComercial = usuarioLogadoController.getUsuarioLogado().getTipoUsuario().getRole().equalsIgnoreCase("ROLE_Comercial");

        if (isAdministrador) {
            //CRIA O MENU USUARIOS
            DefaultMenuItem usuarios = itemMenu("Usuários", urlProviderImpl.getUrlIndexUsuario(), "fa fa-users");

            //CRIA O SUBMENU PEDIDOS
            DefaultSubMenu subMenuPedidos = submenuItem("Pedidos", "fa fa-file-text-o");

            //CRIA O SUBMENU ESTOQUE
            DefaultSubMenu subMenuEstoque = submenuItem("Estoque", "fa fa-cubes");

            //CRIA O SUBMENU FINANCEIRO
            DefaultSubMenu subMenuFinanceiro = submenuItem("Financeiro", "fa fa-dollar");

            //CRIA O SUBMENU FRETES
            DefaultSubMenu subMenuFretes = submenuItem("Fretes", "fa fa-truck");


            //adiciona item de menu "Pedidos Pendentes" ao submenu "Pedidos"
            menuItem("p1", "Pedidos Pendentes","Pedidos Pendentes", urlProviderImpl.getUrlIndexPedidoPendente(), "fa fa-pencil-square-o", subMenuPedidos);
            //adiciona item de menu "Todos" ao submenu "Pedidos"
            menuItem("p2", "Todos","Todos", urlProviderImpl.getUrlIndexPedido(), "fa fa-archive", subMenuPedidos);

            //adiciona item de menu "Mapa de Produção" ao submenu "Estoque"
            menuItem("p3", "Mapa de Produção","Mapa de Produção", urlProviderImpl.getUrlIndexMapaProducao(), "fa fa-sitemap", subMenuEstoque);

            //adiciona item de menu "Comissões" ao submenu "Financeiro"
            menuItem("p4", "Comissões","Comissões", urlProviderImpl.getUrlIndexComissao(), "fa fa-money", subMenuFinanceiro);
            //adiciona item de menu "Contas a Receber" ao submenu "Financeiro"
            menuItem("p5", "Contas a Receber","Contas a Receber", urlProviderImpl.getUrlIndexContasReceber(), "fa fa-barcode", subMenuFinanceiro);

            //adiciona item de menu "Motoristas" ao submenu "Fretes"
            menuItem("p6", "Motoristas","Motoristas", urlProviderImpl.getUrlIndexMotorista(), "fa fa-times-circle-o", subMenuFretes);
            //adiciona item de menu "Fretes" ao submenu "Fretes"
            menuItem("p7", "Fretes","Fretes", urlProviderImpl.getUrlIndexFrete(), "fa fa-truck", subMenuFretes);


            //ADICIONA OS ELEMENTOS (MENUS E SUBMENUS) CRIADOS AO MENU LATERAL
            modelMenu.addElement(usuarios);
            modelMenu.addElement(subMenuPedidos);
            modelMenu.addElement(subMenuEstoque);
            modelMenu.addElement(subMenuFinanceiro);
            modelMenu.addElement(subMenuFretes);
        }

        if (isRepresentante) {
            //CRIA O MENU NOVO PEDIDO
            DefaultMenuItem novoPedido = itemMenu("Novo Pedido", urlProviderImpl.getUrlIndexNovoPedido(), "fa fa-cart-plus");

            //CRIA O MENU CLIENTES
            DefaultMenuItem clientes = itemMenu("Clientes", urlProviderImpl.getUrlIndexCliente(), "fa fa-users");

            //CRIA O SUBMENU PEDIDOS
            DefaultSubMenu subMenuPedidos = submenuItem("Pedidos", "fa fa-file-text-o");

            //CRIA O SUBMENU FINANCEIRO
            DefaultSubMenu subMenuFinanceiro = submenuItem("Financeiro", "fa fa-dollar");

            //adiciona item de menu "Em Aberto" ao submenu "Pedidos"
            menuItem("p1", "Em Aberto","Em Aberto", urlProviderImpl.getUrlIndexPedidoAberto(), "fa fa-inbox", subMenuPedidos);
            //adiciona item de menu "Embarcados" ao submenu "Pedidos"
            menuItem("p2", "Embarcados","Embarcados", urlProviderImpl.getUrlIndexPedidoEmbarcado(), "fa fa-truck", subMenuPedidos);
            //adiciona item de menu "Últimos Faturados" ao submenu "Pedidos"
            menuItem("p3", "Últimos Faturados","Últimos Faturados", urlProviderImpl.getUrlIndexPedidoFaturado(), "fa fa-check-square-o", subMenuPedidos);
            //adiciona item de menu "Situação dos Pedidos" ao submenu "Pedidos"
            menuItem("p4", "Situação dos Pedidos","Situação dos Pedidos", urlProviderImpl.getUrlIndexPedido(), "fa fa-archive", subMenuPedidos);

            //adiciona item de menu "Comissões" ao submenu "Financeiro"
            menuItem("p4", "Comissões","Comissões", urlProviderImpl.getUrlIndexComissao(), "fa fa-money", subMenuFinanceiro);
            //adiciona item de menu "Contas a Receber" ao submenu "Financeiro"
            menuItem("p5", "Contas a Receber","Contas a Receber", urlProviderImpl.getUrlIndexContasReceber(), "fa fa-barcode", subMenuFinanceiro);

            //ADICIONA OS ELEMENTOS (MENUS E SUBMENUS) CRIADOS AO MENU LATERAL
            modelMenu.addElement(novoPedido);
            modelMenu.addElement(clientes);
            modelMenu.addElement(subMenuPedidos);
            modelMenu.addElement(subMenuFinanceiro);
        }

        if (isFinanceiro) {
            //CRIA O SUBMENU FINANCEIRO
            DefaultSubMenu subMenuFinanceiro = submenuItem("Financeiro", "fa fa-dollar");

            //adiciona item de menu "Contas a Receber" ao submenu "Financeiro"
            menuItem("p5", "Contas a Receber","Contas a Receber", urlProviderImpl.getUrlIndexContasReceber(), "fa fa-barcode", subMenuFinanceiro);

            //ADICIONA OS ELEMENTOS (MENUS E SUBMENUS) CRIADOS AO MENU LATERAL
            modelMenu.addElement(subMenuFinanceiro);
        }

        if (isAlmoxarifado) {
            //CRIA O SUBMENU ESTOQUE
            DefaultSubMenu subMenuEstoque = submenuItem("Estoque", "fa fa-cubes");

            //adiciona item de menu "Mapa de Produção" ao submenu "Estoque"
            menuItem("p3", "Mapa de Produção","Mapa de Produção", urlProviderImpl.getUrlIndexMapaProducao(), "fa fa-sitemap", subMenuEstoque);

            modelMenu.addElement(subMenuEstoque);
        }

        if (isComercial) {
            //CRIA O SUBMENU PEDIDOS
            DefaultSubMenu subMenuPedidos = submenuItem("Pedidos", "fa fa-file-text-o");

            //CRIA O SUBMENU FRETES
            DefaultSubMenu subMenuFretes = submenuItem("Fretes", "fa fa-truck");

            //adiciona item de menu "Pedidos Pendentes" ao submenu "Pedidos"
            menuItem("p1", "Pedidos Pendentes","Pedidos Pendentes", urlProviderImpl.getUrlIndexPedidoPendente(), "fa fa-pencil-square-o", subMenuPedidos);
            //adiciona item de menu "Todos" ao submenu "Pedidos"
            menuItem("p2", "Todos","Todos", urlProviderImpl.getUrlIndexPedido(), "fa fa-archive", subMenuPedidos);

            //adiciona item de menu "Motoristas" ao submenu "Fretes"
            menuItem("p6", "Motoristas","Motoristas", urlProviderImpl.getUrlIndexMotorista(), "fa fa-times-circle-o", subMenuFretes);
            //adiciona item de menu "Fretes" ao submenu "Fretes"
            menuItem("p7", "Fretes","Fretes", urlProviderImpl.getUrlIndexFrete(), "fa fa-truck", subMenuFretes);

            modelMenu.addElement(subMenuPedidos);
            modelMenu.addElement(subMenuFretes);
        }
    }

    public DefaultMenuItem itemMenu(String value, String url, String icon) {
        DefaultMenuItem itemMenu = new DefaultMenuItem();
        itemMenu.setValue(value);
        itemMenu.setOutcome(url);
        itemMenu.setIcon(icon);

        return itemMenu;
    }

    public DefaultSubMenu submenuItem(String descricao, String icon) {
        DefaultSubMenu submenu = new DefaultSubMenu(descricao);
        submenu.setIcon(icon);

        return submenu;
    }

    public void menuItem(String id, String value, String descricao, String url, String icon, DefaultSubMenu pai) {
        DefaultMenuItem item = new DefaultMenuItem(descricao);
        item.setOutcome(url);
        item.setIcon(icon);
        item.setId(id);
        item.setValue(value);

        pai.addElement(item);
    }

    public MenuModel getModelMenu() {
        return modelMenu;
    }

    public void setModelMenu(MenuModel modelMenu) {
        this.modelMenu = modelMenu;
    }

}
