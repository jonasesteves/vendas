package br.com.jeweb.vendas.repository;

import br.com.jeweb.core.repository.AbstractRepository;
import br.com.jeweb.vendas.entity.Pessoa;

import javax.enterprise.context.Dependent;
import javax.transaction.Transactional;

@Dependent
@Transactional
public class PessoaRepository extends AbstractRepository<Pessoa> {

    public PessoaRepository() {
        super(Pessoa.class);
    }
}
