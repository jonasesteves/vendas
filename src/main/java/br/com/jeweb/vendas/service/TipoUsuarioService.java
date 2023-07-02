/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jeweb.vendas.service;

import br.com.jeweb.core.filter.AbstractFilter;
import br.com.jeweb.core.service.AbstractService;
import br.com.jeweb.vendas.entity.TipoUsuario;
import br.com.jeweb.vendas.repository.TipoUsuarioRepository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 *
 * @author desenvolvimento
 */
@Dependent
public class TipoUsuarioService extends AbstractService<TipoUsuario> {
    
    @Inject
    private TipoUsuarioRepository tipoUsuarioRepository;

    public TipoUsuarioService() {
    }

    @Override
    public AbstractFilter<TipoUsuario> getFilter() {
        return null;
    }

    @Override
    public TipoUsuarioRepository getRepository() {
        return this.tipoUsuarioRepository;
    }

}
