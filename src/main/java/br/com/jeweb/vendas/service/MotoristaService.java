package br.com.jeweb.vendas.service;

import br.com.jeweb.core.service.AbstractService;
import br.com.jeweb.vendas.entity.Motorista;
import br.com.jeweb.vendas.filter.MotoristaFilter;
import br.com.jeweb.vendas.repository.MotoristaRepository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class MotoristaService extends AbstractService<Motorista>  {

    @Inject
    private MotoristaRepository motoristaRepository;

    @Inject
    private MotoristaFilter motoristaFilter;

    @Override
    public MotoristaRepository getRepository() {
        return this.motoristaRepository;
    }

    @Override
    public MotoristaFilter getFilter() {
        return this.motoristaFilter;
    }

    public Motorista buscaPorCpf(String cpf) {
        return getRepository().findOneByField("cpf", cpf);
    }
}
