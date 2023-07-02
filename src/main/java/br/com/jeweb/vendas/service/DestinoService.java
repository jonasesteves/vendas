package br.com.jeweb.vendas.service;

import br.com.jeweb.core.filter.AbstractFilter;
import br.com.jeweb.core.service.AbstractService;
import br.com.jeweb.vendas.entity.Destino;
import br.com.jeweb.vendas.repository.DestinoRepository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DestinoService extends AbstractService<Destino>  {

    @Inject
    private DestinoRepository destinoRepository;

    @Override
    public DestinoRepository getRepository() {
        return this.destinoRepository;
    }

    @Override
    public AbstractFilter<Destino> getFilter() {
        return null;
    }
}
