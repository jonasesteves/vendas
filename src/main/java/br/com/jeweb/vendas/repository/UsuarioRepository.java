package br.com.jeweb.vendas.repository;

import br.com.jeweb.core.repository.AbstractRepository;
import br.com.jeweb.vendas.entity.Usuario;

import javax.enterprise.context.Dependent;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jungle on 03/07/17.
 */
@Dependent
@Transactional(rollbackOn = Usuario.class)
public class UsuarioRepository extends AbstractRepository<Usuario> {

    public UsuarioRepository() {
        super(Usuario.class);
    }

    public Usuario getByEmail(String email) {
        try {
            return this.getEntityManager().createQuery("from Usuario where email = :email", Usuario.class)
                    .setParameter("email", email)
                    .getSingleResult();
        }
        catch (NoResultException ex) {
            return null;
        }
    }

    public boolean isNew(Integer id) {
        try {
            this.getEntityManager().createQuery("from Usuario where idTop = :id", Usuario.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }
        catch (NoResultException ex) {
            return true;
        }
        return false;
    }

    public List<Usuario> buscaRepresentantes() {
        try {
            return this.getEntityManager().createQuery("from Usuario where tipoUsuario.id = 2", Usuario.class).getResultList();
        }
        catch (NoResultException ex) {
            return null;
        }
    }

    public Usuario getIdTop(Integer idTop) {
        try {
            return this.getEntityManager().createQuery("from Usuario where idTop = :idTop", Usuario.class)
                    .setParameter("idTop", idTop)
                    .getSingleResult();
        }
        catch (NoResultException ex) {
            return null;
        }
    }
}
