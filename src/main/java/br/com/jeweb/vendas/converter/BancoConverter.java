package br.com.jeweb.vendas.converter;

import br.com.jeweb.core.converter.AbstractConverter;
import br.com.jeweb.vendas.entity.Banco;
import br.com.jeweb.vendas.service.BancoService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class BancoConverter extends AbstractConverter<Banco> {

    @Inject
    private BancoService bancoService;

    @Override
    public BancoService getService() {
        return bancoService;
    }
}
