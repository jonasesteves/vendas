package br.com.jeweb.vendas.service;

import br.com.jeweb.core.filter.AbstractFilter;
import br.com.jeweb.core.service.AbstractService;
import br.com.jeweb.vendas.entity.TipoPessoa;
import br.com.jeweb.vendas.repository.TipoPessoaRepository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class TipoPessoaService extends AbstractService<TipoPessoa> {

    @Inject
    private TipoPessoaRepository tipoPessoaRepository;

    @Override
    public TipoPessoaRepository getRepository() {
        return this.tipoPessoaRepository;
    }

    @Override
    public AbstractFilter<TipoPessoa> getFilter() {
        return null;
    }
}
