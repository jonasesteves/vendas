package br.com.jeweb.vendas.controller;

import br.com.jeweb.core.controller.AbstractController;
import br.com.jeweb.vendas.datamodel.ComissaoDataModel;
import br.com.jeweb.vendas.entity.Usuario;
import br.com.jeweb.vendas.entity.webservice.Comissao;
import br.com.jeweb.vendas.service.ComissaoService;
import br.com.jeweb.vendas.util.url.URLProviderImpl;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.ws.WebServiceException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class ComissaoController extends AbstractController<Comissao, ComissaoService, ComissaoDataModel> {

    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private Usuario usuario;
    private List<Comissao> comissoes;

    @Inject
    private UsuarioLogadoController usuarioLogadoController;

    @Inject
    public ComissaoController(ComissaoService comissaoService, ComissaoDataModel comissaoDataModel) {
        super(comissaoService, comissaoDataModel);
    }

    @Override
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

    public void buscaComissoes() {
        try {
            setComissoes(getService().buscaComissoes(dataInicial, dataFinal, usuario.getIdTop()));
        }
        catch (WebServiceException ex) {
            this.getMessagesProvider().reportErrorMessage("Webservice Offline.");
        }
    }

    public void limpar() {
        if (!usuarioLogadoController.isRepresentante()) {
            setUsuario(new Usuario());
        }
        setDataInicial(null);
        setDataFinal(null);
        setComissoes(new ArrayList<>());
    }


    //GETTERS E SETTERS
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

    public List<Comissao> getComissoes() {
        return comissoes;
    }

    public void setComissoes(List<Comissao> comissoes) {
        this.comissoes = comissoes;
    }
}
