package br.com.jeweb.vendas.converter;

import br.com.jeweb.core.converter.AbstractConverter;
import br.com.jeweb.vendas.entity.Item;
import br.com.jeweb.vendas.service.ItemService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;


@Named
@RequestScoped
public class ItemConverter extends AbstractConverter<Item> {

    @Inject
    private ItemService itemService;

    public ItemConverter() {}

    @Override
    public ItemService getService() {
        return itemService;
    }


}
