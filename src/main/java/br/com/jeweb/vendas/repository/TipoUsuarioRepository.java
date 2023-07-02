package br.com.jeweb.vendas.repository;

import br.com.jeweb.core.repository.AbstractRepository;
import br.com.jeweb.vendas.entity.TipoUsuario;

import javax.enterprise.context.Dependent;
import javax.transaction.Transactional;

/**
 * Created by jungle on 03/07/17.
 */
@Dependent
@Transactional
public class TipoUsuarioRepository extends AbstractRepository<TipoUsuario> {

    public TipoUsuarioRepository() {
        super(TipoUsuario.class);
    }
}
