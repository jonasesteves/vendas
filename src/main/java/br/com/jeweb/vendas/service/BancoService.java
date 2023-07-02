package br.com.jeweb.vendas.service;

import br.com.jeweb.core.filter.AbstractFilter;
import br.com.jeweb.core.service.AbstractService;
import br.com.jeweb.vendas.entity.Banco;
import br.com.jeweb.vendas.repository.BancoRepository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class BancoService extends AbstractService<Banco>  {

    @Inject
    private BancoRepository bancoRepository;

    @Override
    public BancoRepository getRepository() {
        return this.bancoRepository;
    }

    @Override
    public AbstractFilter<Banco> getFilter() {
        return null;
    }
}
