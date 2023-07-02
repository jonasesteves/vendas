package br.com.jeweb.vendas.repository;

import br.com.jeweb.core.repository.AbstractRepository;
import br.com.jeweb.vendas.entity.Destino;

import javax.enterprise.context.Dependent;
import javax.transaction.Transactional;

@Dependent
@Transactional
public class DestinoRepository extends AbstractRepository<Destino> {

    public DestinoRepository() {
        super(Destino.class);
    }
}
