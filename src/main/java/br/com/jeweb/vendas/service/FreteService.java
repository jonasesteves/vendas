package br.com.jeweb.vendas.service;

import br.com.jeweb.core.service.AbstractService;
import br.com.jeweb.vendas.entity.Frete;
import br.com.jeweb.vendas.filter.FreteFilter;
import br.com.jeweb.vendas.repository.FreteRepository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class FreteService extends AbstractService<Frete>  {

    @Inject
    private FreteRepository freteRepository;

    @Inject
    private FreteFilter freteFilter;

    @Override
    public FreteRepository getRepository() {
        return this.freteRepository;
    }

    @Override
    public FreteFilter getFilter() {
        return this.freteFilter;
    }
}
