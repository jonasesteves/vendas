package br.com.jeweb.vendas.service;

import br.com.jeweb.core.service.AbstractService;
import br.com.jeweb.vendas.entity.Pessoa;
import br.com.jeweb.vendas.filter.PessoaFilter;
import br.com.jeweb.vendas.repository.PessoaRepository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class PessoaService extends AbstractService<Pessoa>  {

    @Inject
    private PessoaRepository pessoaRepository;

    @Inject
    private PessoaFilter pessoaFilter;

    @Override
    public PessoaRepository getRepository() {
        return this.pessoaRepository;
    }

    @Override
    public PessoaFilter getFilter() {
        return this.pessoaFilter;
    }
}
