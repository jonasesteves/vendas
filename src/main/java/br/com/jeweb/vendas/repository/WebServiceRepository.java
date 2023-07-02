package br.com.jeweb.vendas.repository;

import br.com.jeweb.vendas.util.WebServiceInstance;
import com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfint;
import org.datacontract.schemas._2004._07.incopa_servicos_integracao_utilidade.*;
import org.springframework.remoting.soap.SoapFaultException;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPFaultException;
import java.util.List;

public class WebServiceRepository {

    public List<ClienteVendedorET> buscaClientesVendedor(Integer idVendedor) throws WebServiceException {
        return WebServiceInstance.getInstance().getPort().consultarClientesVendedor(idVendedor).getClienteVendedorET();
    }

    public List<ClienteET> buscaListaClientesPorId(ArrayOfint arrayOfint) {
        return WebServiceInstance.getInstance().getPort().consultarClientes(arrayOfint).getClienteET();
    }

    public List<TipoBonificacaoET> buscaTiposBonificacao() throws WebServiceException {
        return WebServiceInstance.getInstance().getPort().consultarTipoBonificacao().getTipoBonificacaoET();
    }

    public List<EmpresaET> buscaEmpresas() throws WebServiceException {
        return WebServiceInstance.getInstance().getPort().consultaEmpresa().getEmpresaET();
    }

    public List<AgenteCobradorET> buscaAgentesCobradores() throws WebServiceException {
        return WebServiceInstance.getInstance().getPort().consultarAgenteCobrador().getAgenteCobradorET();
    }

    public List<RestricoesCobrancaET> restricoesCobranca(Integer idCliente) throws WebServiceException {
        return WebServiceInstance.getInstance().getPort().consultarRestricoesCobranca(idCliente).getRestricoesCobrancaET();
    }

    public List<TipoCobrancaET> buscaTiposCobranca() throws WebServiceException {
        return WebServiceInstance.getInstance().getPort().consultarTipoCobranca().getTipoCobrancaET();
    }

    public List<FormaPagamentoET> buscaFormasPagamento() throws WebServiceException {
        return WebServiceInstance.getInstance().getPort().consultarFormaPagamento().getFormaPagamentoET();
    }

    public List<EstoquePrecoTabelaET> buscaItensVendedor(Integer idCliente, Integer idVendedor, Integer idAreaVenda) throws WebServiceException {
        return WebServiceInstance.getInstance().getPort().consultarEstoquePrecoTabela(idCliente, idVendedor, idAreaVenda).getEstoquePrecoTabelaET();
    }

    public List<ProdutoET> buscaItensPedido(ArrayOfint arrayOfint) throws WebServiceException {
        return WebServiceInstance.getInstance().getPort().consultarProdutos(arrayOfint).getProdutoET();
    }

    public List<VendedorET> buscaVendedores() throws WebServiceException {
        return WebServiceInstance.getInstance().getPort().consultarVendedores().getVendedorET();
    }

    public Integer enviarPedido(PedidoET pedidoET) throws WebServiceException {
        return WebServiceInstance.getInstance().getPort().incluirPedido(pedidoET);
    }

    public List<ConsumoMapaDeProducaoET> buscaMapaProducao(XMLGregorianCalendar dataInicial, XMLGregorianCalendar dataFinal) throws WebServiceException {
        return WebServiceInstance.getInstance().getPort().consultaConsumoMapaDeProducao(dataInicial, dataFinal).getConsumoMapaDeProducaoET();
    }

    public List<ComissoesET> buscaComissoes(XMLGregorianCalendar dataInicial, XMLGregorianCalendar dataFinal, Integer idVendedor) throws WebServiceException {
        return WebServiceInstance.getInstance().getPort().consultaComissoes(dataInicial, dataFinal, idVendedor).getComissoesET();
    }

    public List<ContasAReceberET> buscaContasReceberVendedor(Integer idVendedor) throws WebServiceException, SOAPFaultException {
        return WebServiceInstance.getInstance().getPort().consultaContasAReceberPorVendedor(idVendedor).getContasAReceberET();
    }

    public List<ContasAReceberET> buscaContasReceberData(XMLGregorianCalendar dataInicial, XMLGregorianCalendar dataFinal, Integer idVendedor) throws WebServiceException, SOAPFaultException {
        return WebServiceInstance.getInstance().getPort().consultaContasAReceberPorDataEVendedor(dataInicial, dataFinal, idVendedor).getContasAReceberET();
    }

    public List<ContatosComClienteET> buscaContatosCliente(Integer idCliente) throws WebServiceException, SoapFaultException {
        return WebServiceInstance.getInstance().getPort().consultarContatosComCliente(idCliente).getContatosComClienteET();
    }

    public void enviarContato(Integer idCliente, XMLGregorianCalendar data, String texto, String usuario) throws WebServiceException, SoapFaultException {
        WebServiceInstance.getInstance().getPort().incluirContatoComCliente(idCliente, data, texto, usuario);
    }

    public List<PedidosPendenteDeEmbarqueET> buscaPedidosAbertos(Integer idVendedor) throws WebServiceException {
        return WebServiceInstance.getInstance().getPort().consultaPedidosPendenteDeEmbarque(idVendedor).getPedidosPendenteDeEmbarqueET();
    }

    public List<PedidosPendenteDeEmbarqueET> buscaPedidoAbertoPorId(Integer idTop) throws WebServiceException {
        return WebServiceInstance.getInstance().getPort().consultaPedidosPendenteDeEmbarquePorPedido(idTop).getPedidosPendenteDeEmbarqueET();
    }

    public List<PedidosEmbarcadosPendentesET> buscaPedidosEmbarcados(Integer idVendedor) throws WebServiceException {
        return WebServiceInstance.getInstance().getPort().consultaPedidosEmbarcadosPendentes(idVendedor).getPedidosEmbarcadosPendentesET();
    }

    public List<PedidosEmbarcadosPendentesET> buscaPedidoEmbarcadoPorId(Integer idTop) throws WebServiceException {
        return WebServiceInstance.getInstance().getPort().consultaPedidosEmbarcadosPendentesPorPedido(idTop).getPedidosEmbarcadosPendentesET();
    }

    public List<FaturamentoET> buscaPedidosFaturados(XMLGregorianCalendar dataInicial, XMLGregorianCalendar dataFinal, Integer idVendedor) throws WebServiceException {
        return WebServiceInstance.getInstance().getPort().consultaFaturamentoPorData(dataInicial, dataFinal, idVendedor).getFaturamentoET();
    }

    public List<FaturamentoET> buscaPedidoFaturadoPorNF(Integer notaFiscal) throws WebServiceException {
        return WebServiceInstance.getInstance().getPort().consultaFaturamentoPorNF(notaFiscal).getFaturamentoET();
    }

}
