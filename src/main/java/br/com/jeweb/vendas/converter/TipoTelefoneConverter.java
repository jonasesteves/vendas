package br.com.jeweb.vendas.converter;

import br.com.jeweb.core.converter.AbstractConverter;
import br.com.jeweb.vendas.entity.TipoTelefone;
import br.com.jeweb.vendas.service.TipoTelefoneService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class TipoTelefoneConverter extends AbstractConverter<TipoTelefone> {

    @Inject
    private TipoTelefoneService tipoTelefoneService;

    @Override
    public TipoTelefoneService getService() {
        return tipoTelefoneService;
    }
}
