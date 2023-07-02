package br.com.jeweb.vendas.repository;

import br.com.jeweb.core.repository.AbstractRepository;
import br.com.jeweb.vendas.entity.Item;

import javax.enterprise.context.Dependent;
import javax.transaction.Transactional;

@Dependent
@Transactional
public class ItemRepository extends AbstractRepository<Item> {

    public ItemRepository() {
        super(Item.class);
    }
}
