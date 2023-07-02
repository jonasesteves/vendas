package br.com.jeweb.vendas.service;

import br.com.jeweb.core.filter.AbstractFilter;
import br.com.jeweb.core.repository.AbstractRepository;
import br.com.jeweb.core.service.AbstractService;
import br.com.jeweb.vendas.entity.webservice.Cliente;
import br.com.jeweb.vendas.entity.webservice.ContatoCliente;
import br.com.jeweb.vendas.repository.WebServiceRepository;
import br.com.jeweb.vendas.util.Util;
import org.datacontract.schemas._2004._07.incopa_servicos_integracao_utilidade.ContatosComClienteET;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Dependent
public class ContatoClienteService extends AbstractService<ContatoCliente> {

    @Inject
    private WebServiceRepository webServiceRepository;

    @Inject
    private ClienteService clienteService;

    @Override
    public AbstractRepository<ContatoCliente> getRepository() {
        return null;
    }

    public List<ContatoCliente> buscaContatosCliente(Integer idCliente) throws WebServiceException {
        List<ContatosComClienteET> vetor = webServiceRepository.buscaContatosCliente(idCliente);
        List<ContatoCliente> contatosCliente = new ArrayList<>();
        Cliente cliente = clienteService.findOne(idCliente.longValue());
        for (ContatosComClienteET c : vetor) {
            ContatoCliente contatoCliente = new ContatoCliente();
            contatoCliente.setId(c.getID());
            contatoCliente.setCliente(cliente);
            contatoCliente.setData(Util.xmlGCToLocalDate(c.getData()));
            contatoCliente.setUsuario(c.getUsuario().getValue());
            contatoCliente.setAssunto(c.getAssunto().getValue());
            contatoCliente.setTexto(c.getTexto().getValue());
            contatoCliente.setFechado(c.getFechado().equals(1));
            contatosCliente.add(contatoCliente);
        }

        //ordena os contatos com cliente por data
        Comparator<ContatoCliente> porData = (o1, o2) -> {
            if (o1.getData().isBefore(o2.getData())) {
                return -1;
            }
            else {
                return 1;
            }
        };
        Collections.sort(contatosCliente, porData);


        return contatosCliente;
    }

    public void enviarContato(ContatoCliente contato) throws WebServiceException {
        Integer idCliente = contato.getCliente().getId();
        XMLGregorianCalendar data = Util.localDateToXMLGC(contato.getData());
        String texto = contato.getTexto();
        String usuario = contato.getUsuario();
        webServiceRepository.enviarContato(idCliente, data, texto, usuario);
    }

    @Override
    public AbstractFilter<ContatoCliente> getFilter() {
        return null;
    }
}
