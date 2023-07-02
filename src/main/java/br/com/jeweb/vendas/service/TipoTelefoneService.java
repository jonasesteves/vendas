package br.com.jeweb.vendas.service;

import br.com.jeweb.core.filter.AbstractFilter;
import br.com.jeweb.core.service.AbstractService;
import br.com.jeweb.vendas.entity.TipoTelefone;
import br.com.jeweb.vendas.repository.TipoTelefoneRepository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class TipoTelefoneService extends AbstractService<TipoTelefone> {

    @Inject
    private TipoTelefoneRepository tipoTelefoneRepository;

    @Override
    public TipoTelefoneRepository getRepository() {
        return this.tipoTelefoneRepository;
    }

    @Override
    public AbstractFilter<TipoTelefone> getFilter() {
        return null;
    }
}
