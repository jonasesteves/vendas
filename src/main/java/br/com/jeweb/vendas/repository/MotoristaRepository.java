package br.com.jeweb.vendas.repository;

import br.com.jeweb.core.repository.AbstractRepository;
import br.com.jeweb.vendas.entity.Motorista;

import javax.enterprise.context.Dependent;
import javax.transaction.Transactional;

@Dependent
@Transactional
public class MotoristaRepository extends AbstractRepository<Motorista> {

    public MotoristaRepository() {
        super(Motorista.class);
    }
}
