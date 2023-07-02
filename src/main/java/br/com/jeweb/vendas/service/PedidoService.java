/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jeweb.vendas.service;

import br.com.jeweb.core.exception.ResourceNotFoundException;
import br.com.jeweb.core.filter.AbstractFilter;
import br.com.jeweb.core.service.AbstractService;
import br.com.jeweb.vendas.controller.UsuarioLogadoController;
import br.com.jeweb.vendas.entity.Item;
import br.com.jeweb.vendas.entity.Pedido;
import br.com.jeweb.vendas.entity.webservice.Cliente;

import br.com.jeweb.vendas.exception.PedidoSemItemException;
import br.com.jeweb.vendas.repository.PedidoRepository;
import br.com.jeweb.vendas.repository.WebServiceRepository;
import br.com.jeweb.core.util.StringUtils;
import br.com.jeweb.vendas.util.Util;
import com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfint;
import org.datacontract.schemas._2004._07.incopa_servicos_integracao_utilidade.*;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;

@Dependent
public class PedidoService extends AbstractService<Pedido> {

    @Inject
    private PedidoRepository pedidoRepository;

    @Inject
    private EmpresaService empresaService;

    @Inject
    private ClienteService clienteService;

    @Inject
    private TipoBonificacaoService tipoBonificacaoService;

    @Inject
    private AgenteCobradorService agenteCobradorService;

    @Inject
    private TipoCobrancaService tipoCobrancaService;

    @Inject
    private FormaPagamentoService formaPagamentoService;

    @Inject
    private ItemService itemService;

    @Inject
    private WebServiceRepository webServiceRepository;

    @Inject
    private UsuarioLogadoController usuarioLogadoController;

    public PedidoService() {
    }

    @Override
    public PedidoRepository getRepository() {
        return this.pedidoRepository;
    }

    /**
     * Busca um pedido por id, monta o objeto completo para a camada de visualização.
     * @param id Id do pedido.
     * @return pedido montado.
     * @throws ResourceNotFoundException
     * @throws WebServiceException
     */
    public Pedido findOne(String id) throws ResourceNotFoundException, WebServiceException {
        Pedido pedido;
        try {
            if (StringUtils.isValid(id)) {
                pedido = this.getRepository().findOne(Long.parseLong(id));
                if ((pedido == null) || (!pedido.getUsuario().getIdTop().equals(usuarioLogadoController.getUsuarioLogado().getIdTop()) && (!usuarioLogadoController.isAdmin() && !usuarioLogadoController.isComercial()) )) {
                    throw new ResourceNotFoundException();
                }
                else {
                    if (pedido.getIdOperacao().equals(97)) {
                        pedido.setOperacao("Venda");
                        pedido.setAgenteCobrador(agenteCobradorService.find(pedido.getIdAgenteCobrador().longValue()));
                        pedido.setTipoCobranca(tipoCobrancaService.find(pedido.getIdTipoCobranca().longValue()));
                        pedido.setFormaPagamento(formaPagamentoService.find(pedido.getIdFormaPagamento().longValue()));
                    } else if (pedido.getIdOperacao().equals(133)) {
                        pedido.setOperacao("Bonificação");
                        pedido.setTipoBonificacao(tipoBonificacaoService.find(pedido.getIdTipoBonificacao().longValue()));
                    }

                    pedido.setCliente(clienteService.findOne(pedido.getIdCliente().longValue()));
                    pedido.setEmpresa(empresaService.find(pedido.getIdEmpresa().longValue()));

                    ArrayOfint arrayOfint = new ArrayOfint();
                    for (Item i : pedido.getItens()) {
                        arrayOfint.getInt().add(i.getIdEstoque());
                    }

                    Map<Integer, Item> itens = itemService.buscaMapaItensPedido(arrayOfint);
                    for (Item i : pedido.getItens()) {
                        i.setNome(itens.get(i.getIdEstoque()).getNome());
                    }
                }
            } else {
                throw new ResourceNotFoundException();
            }
        } catch (NumberFormatException e) {
            throw new ResourceNotFoundException();
        }
        return pedido;
    }

    @Override
    public AbstractFilter<Pedido> getFilter() {
        return null;
    }


    public void salvarPedido(Pedido pedido) throws PedidoSemItemException {
        if (pedido.getItens().size() < 1) {
            throw new PedidoSemItemException("Houve uma tentativa de envio de pedido sem item.");
        }
        else {
            save(pedido);
        }
    }

    public List<Pedido> buscarPedidosAbertos(Integer idVendedor) throws WebServiceException {
        List<PedidosPendenteDeEmbarqueET> vetor = webServiceRepository.buscaPedidosAbertos(idVendedor);
        Set<Pedido> pedidos = new HashSet<>();

        Map<Integer, Cliente> clientes = clienteService.buscaMapaClientesVendedor();

        for (PedidosPendenteDeEmbarqueET p : vetor) {
            Pedido pedido = new Pedido();
            pedido.setId(p.getPedidoId().longValue());
            pedido.setDataEmissao(Util.xmlGCToLocalDate(p.getDataPedido()));
            pedido.setCliente(clientes.get(p.getClienteId()));
            pedido.setValorTotal(p.getValorTotalPedido());
            pedidos.add(pedido);
        }

        return new ArrayList<>(pedidos);
    }

    public Pedido buscarPedidoAberto(String idTop) throws WebServiceException, ResourceNotFoundException {
        List<PedidosPendenteDeEmbarqueET> vetor = webServiceRepository.buscaPedidoAbertoPorId(Integer.parseInt(idTop));

        if (vetor.size() != 0) {

            ArrayOfint arrayOfint = new ArrayOfint();
            for (PedidosPendenteDeEmbarqueET p : vetor) {
                arrayOfint.getInt().add(p.getObjetoId());
            }
            Map<Integer, Item> itens = itemService.buscaMapaItensPedido(arrayOfint);

            Map<Integer, Cliente> clientes = clienteService.buscaMapaClientesVendedor();
            Pedido pedido = new Pedido();

            //seta o cabeçalho do pedido
            pedido.setId(vetor.get(0).getPedidoId().longValue());
            pedido.setDataEmissao(Util.xmlGCToLocalDate(vetor.get(0).getDataPedido()));
            pedido.setCliente(clientes.get(vetor.get(0).getClienteId()));

            //seta os itens do pedido
            for (PedidosPendenteDeEmbarqueET p : vetor) {
                itens.get(p.getObjetoId()).setQuantidade(p.getQuantidade());
                itens.get(p.getObjetoId()).setValorUnitario(p.getValorUnitario());
                itens.get(p.getObjetoId()).setValorTotal(p.getValorTotalItem());

                pedido.getItens().add(itens.get(p.getObjetoId()));
            }
            return pedido;
        }
        else {
            throw new ResourceNotFoundException();
        }
    }

    public List<Pedido> buscarPedidosEmbarcados(Integer idVendedor) throws WebServiceException {
        List<PedidosEmbarcadosPendentesET> vetor = webServiceRepository.buscaPedidosEmbarcados(idVendedor);
        Set<Pedido> pedidos = new HashSet<>();

        Map<Integer, Cliente> clientes = clienteService.buscaMapaClientesVendedor();

        for (PedidosEmbarcadosPendentesET p : vetor) {
            Pedido pedido = new Pedido();
            pedido.setId(p.getPedidoId().longValue());
            pedido.setDataEmissao(Util.xmlGCToLocalDate(p.getDataPedido()));
            pedido.setCliente(clientes.get(p.getClienteId()));
            pedido.setValorTotal(p.getValorTotalPedido());
            pedidos.add(pedido);
        }

        return new ArrayList<>(pedidos);
    }

    public Pedido buscarPedidoEmbarcado(String idTop) throws WebServiceException, ResourceNotFoundException {
        List<PedidosEmbarcadosPendentesET> vetor = webServiceRepository.buscaPedidoEmbarcadoPorId(Integer.parseInt(idTop));

        if (vetor.size() != 0) {

            ArrayOfint arrayOfint = new ArrayOfint();
            for (PedidosEmbarcadosPendentesET p : vetor) {
                arrayOfint.getInt().add(p.getObjetoId());
            }
            Map<Integer, Item> itens = itemService.buscaMapaItensPedido(arrayOfint);

            Map<Integer, Cliente> clientes = clienteService.buscaMapaClientesVendedor();
            Pedido pedido = new Pedido();

            //seta o cabeçalho do pedido
            pedido.setId(vetor.get(0).getPedidoId().longValue());
            pedido.setCodEmbarque(vetor.get(0).getEmbarqueId());
            pedido.setDataEmissao(Util.xmlGCToLocalDate(vetor.get(0).getDataPedido()));
            pedido.setCliente(clientes.get(vetor.get(0).getClienteId()));

            //seta os itens do pedido
            for (PedidosEmbarcadosPendentesET p : vetor) {
                itens.get(p.getObjetoId()).setQuantidade(p.getQuantidade());
                itens.get(p.getObjetoId()).setValorUnitario(p.getValorUnitario());
                itens.get(p.getObjetoId()).setValorTotal(p.getValorTotalItem());

                pedido.getItens().add(itens.get(p.getObjetoId()));
            }
            return pedido;
        }
        else {
            throw new ResourceNotFoundException();
        }
    }

    public List<Pedido> buscarPedidosFaturados(LocalDate dataInicial, LocalDate dataFinal, Integer idVendedor) throws WebServiceException {
        XMLGregorianCalendar xmlDataInicial = Util.localDateToXMLGC(dataInicial);
        XMLGregorianCalendar xmlDataFinal = Util.localDateToXMLGC(dataFinal);

        List<FaturamentoET> vetor = webServiceRepository.buscaPedidosFaturados(xmlDataInicial, xmlDataFinal, idVendedor);
        Set<Pedido> pedidos = new HashSet<>();

        Map<Integer, Cliente> clientes = clienteService.buscaMapaClientesVendedor();

        for (FaturamentoET p : vetor) {
            Pedido pedido = new Pedido();
            pedido.setId(p.getPedidoId().longValue());
            pedido.setDataEmissao(Util.xmlGCToLocalDate(p.getDataFaturamento()));
            pedido.setNotaFiscal(p.getNotaFiscal());
            pedido.setCliente(clientes.get(p.getClienteId()));
            pedido.setValorTotal(p.getValorTotalNf());
            pedidos.add(pedido);
        }

        return new ArrayList<>(pedidos);
    }

    public Pedido buscarPedidoFaturado(String notaFiscal) throws WebServiceException, ResourceNotFoundException {
        List<FaturamentoET> vetor = webServiceRepository.buscaPedidoFaturadoPorNF(Integer.parseInt(notaFiscal));

        if (vetor.size() != 0) {

            ArrayOfint arrayOfint = new ArrayOfint();
            for (FaturamentoET p : vetor) {
                arrayOfint.getInt().add(p.getObjetoId());
            }
            Map<Integer, Item> itens = itemService.buscaMapaItensPedido(arrayOfint);

            Map<Integer, Cliente> clientes = clienteService.buscaMapaClientesVendedor();
            Pedido pedido = new Pedido();

            //seta o cabeçalho do pedido
            pedido.setId(vetor.get(0).getPedidoId().longValue());
            pedido.setCodEmbarque(vetor.get(0).getCodigoEmbarque());
            pedido.setDataEmissao(Util.xmlGCToLocalDate(vetor.get(0).getDataFaturamento()));
            pedido.setNotaFiscal(vetor.get(0).getNotaFiscal());
            pedido.setCliente(clientes.get(vetor.get(0).getClienteId()));

            //seta os itens do pedido
            for (FaturamentoET p : vetor) {
                itens.get(p.getObjetoId()).setQuantidade(p.getQuantidade());
                itens.get(p.getObjetoId()).setValorUnitario(p.getValorUnitario());
                itens.get(p.getObjetoId()).setValorTotal(p.getValorTotalItem());

                pedido.getItens().add(itens.get(p.getObjetoId()));
            }
            return pedido;
        }
        else {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Preenche os clientes dos pedidos passados por parâmetro
     * @param pedidos Lista de pedidos para preencher os clientes.
     * @return Pedidos com clientes preenchidos.
     */
    public List<Pedido> preencherClientes(List<Pedido> pedidos) {
        if (pedidos != null) {
            ArrayOfint arrayOfint = new ArrayOfint();
            for (Pedido p : pedidos) {
                arrayOfint.getInt().add(p.getIdCliente());
            }
            List<Cliente> clientes = clienteService.buscaListaClientesPorId(arrayOfint);
            for (Pedido p : pedidos) {
                for (Cliente c : clientes) {
                    if (p.getIdCliente().equals(c.getId())) {
                        p.setCliente(c);
                        break;
                    }
                }
            }
        }
        return pedidos;
    }

    public Integer enviarPedido(Pedido pedido) throws WebServiceException {

        PedidoET pedidoET;
        ObjectFactory objectFactory2004 = new ObjectFactory();
        pedidoET = objectFactory2004.createPedidoET();

        pedidoET.setNumeroDoPedido(pedido.getId().intValue());
        pedidoET.setVendedorID(pedido.getUsuario().getIdTop());
        pedidoET.setClienteID(pedido.getIdCliente());
        pedidoET.setEmpresaID(pedido.getIdEmpresa().shortValue());
        pedidoET.setOperacaoID(pedido.getIdOperacao().shortValue());
        if (pedido.getIdOperacao().equals(97)) {
            pedidoET.setAgenteCobradorID(pedido.getIdAgenteCobrador());
            pedidoET.setTipoDeCobrancaID(pedido.getIdTipoCobranca().shortValue());
            pedidoET.setFormaDePagamentoID(pedido.getIdFormaPagamento().shortValue());
            pedidoET.setTipoDeBonificacaoID((short) 0);
        }
        else {
            pedidoET.setTipoDeBonificacaoID(pedido.getIdTipoBonificacao().shortValue());
            pedidoET.setAgenteCobradorID(0);
            pedidoET.setTipoDeCobrancaID((short) 0);
            pedidoET.setFormaDePagamentoID((short) 0);
        }
        pedidoET.setObservacao(objectFactory2004.createPedidoETObservacao(pedido.getObservacao()));
        pedidoET.setData(Util.localDateToXMLGC(pedido.getDataEmissao()));
        pedidoET.setDataEntrega(Util.localDateToXMLGC(pedido.getDataEmissao()));

        List<Item> itens = pedido.getItens();
        ArrayOfItemPedidoET itensPedido = objectFactory2004.createArrayOfItemPedidoET();
        for (Item i : itens) {
            ItemPedidoET itemPedidoET = new ItemPedidoET();
            itemPedidoET.setObjetoID(i.getIdEstoque());
            itemPedidoET.setPreco(i.getValorTotal());
            itemPedidoET.setQuantidade(i.getQuantidade());
            itensPedido.getItemPedidoET().add(itemPedidoET);
        }

        pedidoET.setItensPedido(new JAXBElement<>(new QName("http://schemas.datacontract.org/2004/07/Incopa.Servicos.Integracao.Utilidade.EntidadeDeTransferencia", "ItensPedido"), ArrayOfItemPedidoET.class, itensPedido ));

        return webServiceRepository.enviarPedido(pedidoET);
    }

    /**
     * * Busca a lista de pedidosPendentes pendentes de aprovação.
     * @return Lista de pedidosPendentes.@return
     * @throws WebServiceException
     */
    public List<Pedido> getPendentes() throws WebServiceException {
        return preencherClientes(this.getRepository().getPendentes());
    }

    public List<Pedido> buscarPedidos(Integer idVendedor, LocalDate dataInicial, LocalDate dataFinal) {
        return preencherClientes(getRepository().buscaPedidos(idVendedor, dataInicial, dataFinal));
    }

    public List<Pedido> buscarUltimosPedidos(Integer idVendedor, Integer idCliente) {
        return getRepository().buscaUltimosPedidos(idVendedor, idCliente);
    }

    public BigInteger buscaTotalPedidosPendentes() {
        return getRepository().buscaTotalPedidosPendentes();
    }

}
