package br.com.jeweb.vendas.repository;

import br.com.jeweb.core.repository.AbstractRepository;
import br.com.jeweb.vendas.entity.TipoTelefone;

import javax.enterprise.context.Dependent;

@Dependent
public class TipoTelefoneRepository extends AbstractRepository<TipoTelefone> {

    public TipoTelefoneRepository() {
        super(TipoTelefone.class);
    }
}
