package br.com.jeweb.vendas.converter;

import br.com.jeweb.core.converter.AbstractConverter;
import br.com.jeweb.vendas.entity.TipoUsuario;
import br.com.jeweb.vendas.service.TipoUsuarioService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class TipoUsuarioConverter extends AbstractConverter<TipoUsuario> {

    @Inject
    private TipoUsuarioService tipoUsuarioService;

    @Override
    public TipoUsuarioService getService() {
        return tipoUsuarioService;
    }
}
