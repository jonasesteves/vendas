package br.com.jeweb.vendas.converter;

import br.com.jeweb.core.converter.AbstractConverter;
import br.com.jeweb.vendas.entity.TipoPessoa;
import br.com.jeweb.vendas.service.TipoPessoaService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class TipoPessoaConverter extends AbstractConverter<TipoPessoa> {

    @Inject
    private TipoPessoaService tipoPessoaService;

    @Override
    public TipoPessoaService getService() {
        return tipoPessoaService;
    }
}
