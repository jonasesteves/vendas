package br.com.jeweb.vendas.repository;

import br.com.jeweb.core.repository.AbstractRepository;
import br.com.jeweb.vendas.entity.TipoPessoa;

import javax.enterprise.context.Dependent;

@Dependent
public class TipoPessoaRepository extends AbstractRepository<TipoPessoa> {

    public TipoPessoaRepository() {
        super(TipoPessoa.class);
    }
}
