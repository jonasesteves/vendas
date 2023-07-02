/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jeweb.vendas.controller;

import br.com.jeweb.core.controller.AbstractController;
import br.com.jeweb.core.util.url.URLProvider;
import br.com.jeweb.vendas.datamodel.TipoUsuarioDataModel;
import br.com.jeweb.vendas.entity.TipoUsuario;
import br.com.jeweb.vendas.service.TipoUsuarioService;
import br.com.jeweb.vendas.util.url.URLProviderImpl;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@ViewScoped
public class TipoUsuarioController extends AbstractController<TipoUsuario, TipoUsuarioService, TipoUsuarioDataModel> {
    
    @Inject
    private TipoUsuarioService tipoUsuarioService;

    private TipoUsuario tipoUsuario;

    private List<TipoUsuario> tiposUsuario;

    @PostConstruct
    public void init() {
        this.tiposUsuario = this.tipoUsuarioService.findAll();
    }

    public List<TipoUsuario> tiposUsuario() {
        return this.tiposUsuario;
    }


    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @Override
    public URLProviderImpl getUrlProvider() {
        return (URLProviderImpl) this.getUrlProviderInstance();
    }

}
