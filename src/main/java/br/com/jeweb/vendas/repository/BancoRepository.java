package br.com.jeweb.vendas.repository;

import br.com.jeweb.core.repository.AbstractRepository;
import br.com.jeweb.vendas.entity.Banco;

import javax.enterprise.context.Dependent;
import javax.transaction.Transactional;

@Dependent
@Transactional
public class BancoRepository extends AbstractRepository<Banco> {

    public BancoRepository() {
        super(Banco.class);
    }
}
