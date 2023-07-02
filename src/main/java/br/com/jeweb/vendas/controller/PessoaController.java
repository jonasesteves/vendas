package br.com.jeweb.vendas.controller;

import br.com.jeweb.core.controller.AbstractController;
import br.com.jeweb.core.exception.ApplicationException;
import br.com.jeweb.core.exception.ResourceNotFoundException;
import br.com.jeweb.vendas.datamodel.PessoaDataModel;
import br.com.jeweb.vendas.entity.Pessoa;
import br.com.jeweb.vendas.service.PessoaService;
import br.com.jeweb.vendas.util.url.URLProviderImpl;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class PessoaController extends AbstractController<Pessoa, PessoaService, PessoaDataModel> {

    @Inject
    public PessoaController(PessoaService pessoaService, PessoaDataModel pessoaDataModel) {
        super(pessoaService, pessoaDataModel);
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
    public Pessoa getEntidade() {
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

    public String salvar() {
        try {
            if (this.entityIsNew()) {
                getService().save(this.getEntidade());
                this.createEntity();
                this.getMessagesProvider().reportInfoMessage("Pessoa cadastrada com sucesso.");
            }
            else {
                getService().update(this.getEntidade());
                this.getMessagesProvider().reportInfoMessage("Pessoa atualizada com sucesso.");
            }
            return this.getUrlProvider().getUrlIndexPessoa();
        }
        catch (ApplicationException ex) {
            this.getMessagesProvider().reportErrorMessage("Ocorreu um problema ao tentar salvar as informações. Tente novamente mais tarde.");
        }
        return null;
    }

    public void excluir() {
        try {
            getService().remove(this.getEntidade());
            this.getMessagesProvider().reportInfoMessage("Pessoa excluída com sucesso.");
        }
        catch (ApplicationException ex) {
            this.getMessagesProvider().reportErrorMessage("Ocorreu um erro ao tentar excluir as informações. Tente novamente mais tarde.");
        }
    }
}
