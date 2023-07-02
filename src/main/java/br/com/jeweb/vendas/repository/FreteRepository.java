package br.com.jeweb.vendas.repository;

import br.com.jeweb.core.repository.AbstractRepository;
import br.com.jeweb.vendas.entity.Frete;

import javax.enterprise.context.Dependent;
import javax.transaction.Transactional;

@Dependent
@Transactional
public class FreteRepository extends AbstractRepository<Frete> {

    public FreteRepository() {
        super(Frete.class);
    }
}
