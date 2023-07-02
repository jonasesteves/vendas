package br.com.jeweb.vendas.controller;

import br.com.jeweb.core.controller.AbstractController;
import br.com.jeweb.core.exception.ResourceNotFoundException;
import br.com.jeweb.vendas.datamodel.TipoPessoaDataModel;
import br.com.jeweb.vendas.entity.TipoPessoa;
import br.com.jeweb.vendas.service.TipoPessoaService;
import br.com.jeweb.vendas.util.url.URLProviderImpl;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@ViewScoped
public class TipoPessoaController extends AbstractController<TipoPessoa, TipoPessoaService, TipoPessoaDataModel> {

    @Inject
    public TipoPessoaController(TipoPessoaService tipoPessoaService, TipoPessoaDataModel tipoPessoaDataModel) {
        super(tipoPessoaService, tipoPessoaDataModel);
    }

    @Override
    @PostConstruct
    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            createEntity();
        }
    }

    @Override
    public URLProviderImpl getUrlProvider() {
        return (URLProviderImpl) this.getUrlProviderInstance();
    }

    @Override
    public TipoPessoa getEntidade() {
        return super.getEntidade();
    }

    public void load(String id) {
        try {
            if (!FacesContext.getCurrentInstance().isPostback()) {
                setEntidade(this.getService().findOne(id));
            }
        }
        catch (ResourceNotFoundException ex) {
            this.getUrlProvider().redirectFromContext(this.getUrlProvider().getUrlResourceNotFound());
        }
    }

    public List<TipoPessoa> getTiposPessoa() {
        return getService().findAll();
    }
}
