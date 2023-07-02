package br.com.jeweb.vendas.controller;

import br.com.jeweb.core.controller.AbstractController;
import br.com.jeweb.core.exception.ApplicationException;
import br.com.jeweb.core.exception.ResourceNotFoundException;
import br.com.jeweb.vendas.datamodel.BancoDataModel;
import br.com.jeweb.vendas.entity.Banco;
import br.com.jeweb.vendas.service.BancoService;
import br.com.jeweb.vendas.util.url.URLProviderImpl;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@ViewScoped
public class BancoController extends AbstractController<Banco, BancoService, BancoDataModel> {

    @Inject
    public BancoController(BancoService bancoService, BancoDataModel bancoDataModel) {
        super(bancoService, bancoDataModel);
    }

    @Override
    @PostConstruct
    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            this.createEntity();
        }
    }

    @Override
    public URLProviderImpl getUrlProvider() {
        return (URLProviderImpl) this.getUrlProviderInstance();
    }

    @Override
    public Banco getEntidade() {
        return super.getEntidade();
    }

    public List<Banco> bancos() {
        return getService().findAll();
    }
}
