package br.com.jeweb.vendas.service;

import br.com.jeweb.core.filter.AbstractFilter;
import br.com.jeweb.core.repository.AbstractRepository;
import br.com.jeweb.core.service.AbstractService;
import br.com.jeweb.vendas.entity.webservice.Cliente;
import br.com.jeweb.vendas.entity.webservice.ContaReceber;
import br.com.jeweb.vendas.repository.WebServiceRepository;
import br.com.jeweb.vendas.util.Util;
import com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfint;
import org.datacontract.schemas._2004._07.incopa_servicos_integracao_utilidade.ContasAReceberET;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPFaultException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Dependent
public class ContaReceberService extends AbstractService<ContaReceber> {

    @Inject
    private UsuarioService usuarioService;

    @Inject
    private WebServiceRepository webServiceRepository;

    @Inject
    private ClienteService clienteService;

    @Override
    public AbstractRepository<ContaReceber> getRepository() {
        return null;
    }

    public List<ContaReceber> buscaContasReceberVencimento(LocalDate dataInicial, LocalDate dataFinal, Integer idVendedor) throws WebServiceException, SOAPFaultException {
        List<ContasAReceberET> vetor = webServiceRepository.buscaContasReceberVendedor(idVendedor);
        List<ContaReceber> contasReceber = new ArrayList<>();

        //corrige 1 dia nas datas para usar no after e before do GregorianCalendar
        XMLGregorianCalendar xmlDataInicial = Util.localDateToXMLGC(dataInicial.minusDays(1));
        XMLGregorianCalendar xmlDataFinal = Util.localDateToXMLGC(dataFinal.plusDays(1));

        ArrayOfint arrayOfint = new ArrayOfint();
        for (ContasAReceberET c : vetor) {
            if (c.getVencimento().toGregorianCalendar().after(xmlDataInicial.toGregorianCalendar()) && c.getVencimento().toGregorianCalendar().before(xmlDataFinal.toGregorianCalendar())) {
                arrayOfint.getInt().add(c.getClienteId());
            }
        }
        Map<Integer, Cliente> clientes = clienteService.buscaMapaClientesPorId(arrayOfint);

        for (ContasAReceberET c : vetor) {
            if (c.getVencimento().toGregorianCalendar().after(xmlDataInicial.toGregorianCalendar()) && c.getVencimento().toGregorianCalendar().before(xmlDataFinal.toGregorianCalendar())) {
                contasReceber.add(criaContaReceber(c, clientes));
            }
        }
        return contasReceber;
    }

    public List<ContaReceber> buscaContasReceberData(LocalDate dataInicial, LocalDate dataFinal, Integer idVendedor) throws WebServiceException, SOAPFaultException {
        List<ContasAReceberET> vetor = webServiceRepository.buscaContasReceberData(Util.localDateToXMLGC(dataInicial), Util.localDateToXMLGC(dataFinal), idVendedor);
        List<ContaReceber> contasReceber = new ArrayList<>();

        ArrayOfint arrayOfint = new ArrayOfint();
        for (ContasAReceberET c : vetor) {
            arrayOfint.getInt().add(c.getClienteId());
        }
        Map<Integer, Cliente> clientes = clienteService.buscaMapaClientesPorId(arrayOfint);

        for (ContasAReceberET c : vetor) {
            contasReceber.add(criaContaReceber(c, clientes));
        }
        return contasReceber;
    }

    public List<ContaReceber> buscaTodasContasReceber(Integer idVendedor) throws WebServiceException {
        List<ContasAReceberET> vetor = webServiceRepository.buscaContasReceberVendedor(idVendedor);
        List<ContaReceber> contasReceber = new ArrayList<>();

        ArrayOfint arrayOfint = new ArrayOfint();
        for (ContasAReceberET c : vetor) {
            arrayOfint.getInt().add(c.getClienteId());
        }
        Map<Integer, Cliente> clientes = clienteService.buscaMapaClientesPorId(arrayOfint);

        for (ContasAReceberET c : vetor) {
            contasReceber.add(criaContaReceber(c, clientes));
        }
        return contasReceber;
    }

    public List<ContaReceber> buscaContasReceberPorNF(String nf, String dt, String ve) throws WebServiceException {
        LocalDate data = LocalDate.parse(dt);
        List<ContaReceber> contasReceber = buscaContasReceberData(data, data, Integer.parseInt(ve));
        contasReceber.removeIf(c -> !c.getNotaFiscal().equalsIgnoreCase(nf));
        return contasReceber;
    }

    private ContaReceber criaContaReceber(ContasAReceberET c, Map<Integer, Cliente> clientes) {
        ContaReceber conta = new ContaReceber();
        conta.setCliente(clientes.get(c.getClienteId()));
        conta.setId(c.getID());
        conta.setUsuario(usuarioService.getByIdTop(c.getVendedorId()));
        conta.setSaldo(c.getSaldo());
        conta.setTitulo(c.getTitulo().getValue());
        conta.setValor(c.getValor());
        conta.setEmissao(Util.xmlGCToLocalDate(c.getEmissao()));
        conta.setVencimento(Util.xmlGCToLocalDate(c.getVencimento()));
        conta.setNotaFiscal(c.getNf().getValue());
        if (conta.getVencimento().isBefore(LocalDate.now())) {
            conta.setVencido(true);
        }
        else {
            conta.setVencido(false);
        }
        return conta;
    }

    @Override
    public AbstractFilter<ContaReceber> getFilter() {
        return null;
    }
}
