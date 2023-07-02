/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jeweb.vendas.service;

import br.com.jeweb.core.filter.AbstractFilter;
import br.com.jeweb.core.repository.AbstractRepository;
import br.com.jeweb.core.service.AbstractService;
import br.com.jeweb.vendas.controller.UsuarioLogadoController;
import br.com.jeweb.vendas.entity.webservice.Cliente;
import br.com.jeweb.vendas.repository.WebServiceRepository;
import br.com.jeweb.vendas.util.Util;
import com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfint;
import org.datacontract.schemas._2004._07.incopa_servicos_integracao_utilidade.ClienteET;
import org.datacontract.schemas._2004._07.incopa_servicos_integracao_utilidade.ClienteVendedorET;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.xml.ws.WebServiceException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author desenvolvimento
 */
@Dependent
public class ClienteService extends AbstractService<Cliente> {

    @Inject
    private WebServiceRepository webServiceRepository;

    @Inject
    private UsuarioLogadoController usuarioLogadoController;

    public ClienteService() {
    }

    public List<Cliente> buscaClientesVendedor(Integer idTop) throws WebServiceException {
        List<ClienteVendedorET> vetor =  webServiceRepository.buscaClientesVendedor(idTop);
        List<Cliente> clientes = new ArrayList<Cliente>();
        for (ClienteVendedorET c : vetor) {
            Cliente cliente = new Cliente();
            cliente.setId(c.getID());
            cliente.setNome(c.getNome().getValue());
            cliente.setCpfCnpj(c.getCNPJCPF().getValue());
            cliente.setEndereco(c.getLogradouro().getValue());
            cliente.setCidade(c.getCidade().getValue());
            cliente.setTelefone(c.getTelefone().getValue());
            cliente.setTelefone2(c.getTelefone2().getValue());
            cliente.setTelefone3(c.getTelefone3().getValue());
            cliente.setCelular(c.getCelular().getValue());
            cliente.setCelularTim(c.getCelularTIM().getValue());
            cliente.setCelularOi(c.getCelularOI().getValue());
            cliente.setCelularClaro(c.getCelularClaro().getValue());
            cliente.setFax(c.getFax().getValue());
            cliente.setEmail(c.getEmail().getValue());
            cliente.setContato(c.getContato().getValue());
            cliente.setSituacao(c.getSituacao().getValue());
            cliente.setMotivo(c.getMotivo().getValue());
            if (c.getDataUltimaVenda().getValue() != null) {
                cliente.setDataUltimaVenda(Util.xmlGCToLocalDate(c.getDataUltimaVenda().getValue()));
            }
            cliente.setIdAreaVenda(c.getIdAreaVenda());

            clientes.add(cliente);
        }
        return clientes;
    }

    @Override
    public Cliente findOne(Long id) throws WebServiceException {
//        return webServiceRepository.buscaClientePorId(id.intValue());
        ArrayOfint arrayOfint = new ArrayOfint();
        arrayOfint.getInt().add(id.intValue());
        return buscaListaClientesPorId(arrayOfint).get(0);
    }

    @Override
    public AbstractFilter<Cliente> getFilter() {
        return null;
    }

    @Override
    public AbstractRepository<Cliente> getRepository() {
        return null;
    }

    public List<Cliente> buscaListaClientesPorId(ArrayOfint arrayOfint) throws WebServiceException {
        List<ClienteET> clientesET = webServiceRepository.buscaListaClientesPorId(arrayOfint);
        List<Cliente> clientes = new ArrayList<>();

        for (ClienteET c : clientesET) {
            Cliente cliente = new Cliente();
            cliente.setId(c.getID());
            cliente.setNome(c.getNome().getValue());
            cliente.setCelular(c.getCelular().getValue());
            cliente.setCelularClaro(c.getCelularClaro().getValue());
            cliente.setCelularOi(c.getCelularOI().getValue());
            cliente.setCelularTim(c.getCelularTIM().getValue());
            cliente.setContato(c.getContato().getValue());
            cliente.setEmail(c.getEmail().getValue());
            cliente.setFax(c.getFax().getValue());
            cliente.setTelefone(c.getTelefone().getValue());
            cliente.setTelefone2(c.getTelefone2().getValue());
            cliente.setTelefone3(c.getTelefone3().getValue());
            clientes.add(cliente);
        }
        return clientes;
    }

    public Map<Integer, Cliente> buscaMapaClientesPorId(ArrayOfint arrayOfint) throws WebServiceException {
        List<ClienteET> clientesET = webServiceRepository.buscaListaClientesPorId(arrayOfint);
        Map<Integer, Cliente> clientes = new HashMap<>();
        for (ClienteET c : clientesET) {
            clientes.put(c.getID(), new Cliente(c.getID(), c.getNome().getValue()));
        }
        return clientes;
    }

    public Map<Integer, Cliente> buscaMapaClientesVendedor() throws WebServiceException {
        Map<Integer, Cliente> clientes = new HashMap<>();
        List<Cliente> listaClientes = usuarioLogadoController.getClientesVendedor();
        for (Cliente c : listaClientes) {
            clientes.put(c.getId(), c);
        }
        return clientes;
    }
}
