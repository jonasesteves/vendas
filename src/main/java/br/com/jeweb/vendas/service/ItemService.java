package br.com.jeweb.vendas.service;

import br.com.jeweb.core.filter.AbstractFilter;
import br.com.jeweb.core.service.AbstractService;
import br.com.jeweb.vendas.entity.Item;
import br.com.jeweb.vendas.repository.ItemRepository;
import br.com.jeweb.vendas.repository.WebServiceRepository;
import com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfint;
import org.datacontract.schemas._2004._07.incopa_servicos_integracao_utilidade.EstoquePrecoTabelaET;
import org.datacontract.schemas._2004._07.incopa_servicos_integracao_utilidade.ProdutoET;

import javax.inject.Inject;
import javax.xml.ws.WebServiceException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemService extends AbstractService<Item> {

    @Inject
    private ItemRepository itemRepository;

    @Inject
    private WebServiceRepository webServiceRepository;

    @Override
    public ItemRepository getRepository() {
        return this.itemRepository;
    }

    public List<Item> itensVendedor(Integer idCliente, Integer idVendedor, Integer idAreaVenda, Integer idEmpresa, Integer idOperacao) throws WebServiceException {
        List<EstoquePrecoTabelaET> vetor = webServiceRepository.buscaItensVendedor(idCliente, idVendedor, idAreaVenda);
        List<Item> itensVenda = new ArrayList<>();
        for (EstoquePrecoTabelaET e : vetor) {
            if (e.getIdOperacao().equals(idOperacao)) {
                if (e.getIdEmpresa().equals(idEmpresa) && e.getIdCliente().equals(idCliente)) {
                    itensVenda.add(new Item(e.getIdEstoque(), e.getNome().getValue(), e.getValor()));
                }
                if (e.getIdEmpresa().equals(0) && e.getIdCliente().equals(idCliente)) {
                    itensVenda.add(new Item(e.getIdEstoque(), e.getNome().getValue(), e.getValor()));
                }
                if (e.getIdEmpresa().equals(idEmpresa) && e.getIdCliente().equals(0)) {
                    itensVenda.add(new Item(e.getIdEstoque(), e.getNome().getValue(), e.getValor()));
                }
                if (e.getIdEmpresa().equals(0) && e.getIdCliente().equals(0)) {
                    itensVenda.add(new Item(e.getIdEstoque(), e.getNome().getValue(), e.getValor()));
                }
            }
        }
        return itensVenda;
    }

    public Map<Integer, Item> buscaMapaItensPedido(ArrayOfint arrayOfint) throws WebServiceException {
        List<ProdutoET> produtos = webServiceRepository.buscaItensPedido(arrayOfint);
        Map<Integer, Item> itens = new HashMap<>();

        for (ProdutoET p : produtos) {
            itens.put(p.getID(), new Item(p.getID(), p.getNome().getValue(), null));
        }
        return itens;
    }

    @Override
    public AbstractFilter<Item> getFilter() {
        return null;
    }
}
