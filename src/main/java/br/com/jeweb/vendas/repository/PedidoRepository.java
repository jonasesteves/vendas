/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jeweb.vendas.repository;

import br.com.jeweb.core.repository.AbstractRepository;
import br.com.jeweb.vendas.entity.Pedido;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author desenvolvimento
 */
@Dependent
@Transactional(rollbackOn = Pedido.class)
public class PedidoRepository extends AbstractRepository<Pedido> {

    @PersistenceContext
    private EntityManager entityManager;

    public PedidoRepository() {
        super(Pedido.class);
    }

    public List<Pedido> getPendentes() {
        try {
            TypedQuery<Pedido> query = entityManager.createQuery("from Pedido where enviado = 0 and status <> 'CANCELADO'", Pedido.class);
            return query.getResultList();
        }
        catch (NoResultException ex) {
            return null;
        }
    }

    public List<Pedido> buscaPedidos(Integer idVendedor, LocalDate dataInicial, LocalDate dataFinal) {
        try {
            TypedQuery<Pedido> query = entityManager.createQuery("from Pedido where usuario.idTop = :idVendedor and dataEmissao between :dataInicial and :dataFinal", Pedido.class)
                    .setParameter("idVendedor", idVendedor)
                    .setParameter("dataInicial", dataInicial)
                    .setParameter("dataFinal", dataFinal);
            return query.getResultList();
        }
        catch (NoResultException ex) {
            return null;
        }
    }

    public BigInteger buscaTotalPedidosPendentes() {
        return (BigInteger) entityManager.createNativeQuery("select count(id) from pedido where enviado = 0 and status <> 'CANCELADO'").getSingleResult();
    }

    public List<Pedido> buscaUltimosPedidos(Integer idVendedor, Integer idCliente) {
        try {
            TypedQuery<Pedido> query = entityManager.createQuery("from Pedido where usuario.idTop = :idVendedor and idCliente = :idCliente and status <> 'CANCELADO' order by id desc", Pedido.class)
                    .setParameter("idVendedor", idVendedor)
                    .setParameter("idCliente", idCliente)
                    .setMaxResults(3);
            return query.getResultList();
        }
        catch (NoResultException ex) {
            return null;
        }
    }
}
