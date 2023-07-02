package br.com.jeweb.vendas.converter;

import br.com.jeweb.core.converter.AbstractConverter;
import br.com.jeweb.vendas.entity.Usuario;
import br.com.jeweb.vendas.service.UsuarioService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class UsuarioConverter extends AbstractConverter<Usuario> {

    @Inject
    private UsuarioService usuarioService;

    public UsuarioConverter() {
    }

    @Override
    public UsuarioService getService() {
        return usuarioService;
    }
}
