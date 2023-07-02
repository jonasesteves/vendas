package br.com.jeweb.vendas.controller;

import br.com.jeweb.core.controller.AbstractController;
import br.com.jeweb.vendas.datamodel.ContaReceberDataModel;
import br.com.jeweb.vendas.entity.webservice.ContaReceber;
import br.com.jeweb.vendas.service.ContaReceberService;
import br.com.jeweb.vendas.util.url.URLProviderImpl;
import org.springframework.remoting.soap.SoapFaultException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.ws.WebServiceException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class ContaReceberController extends AbstractController<ContaReceber, ContaReceberService, ContaReceberDataModel> {

    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private String opcao = "";
    private boolean bloqueiaDatas = true;
    private List<ContaReceber> contasReceber;
    private Integer idVendedor;

    @Inject
    private UsuarioLogadoController usuarioLogadoController;

    @Inject
    public ContaReceberController(ContaReceberService contaReceberService, ContaReceberDataModel contaReceberDataModel) {
        super(contaReceberService, contaReceberDataModel);
    }

    @Override
    @PostConstruct
    public void init() {
        idVendedor = usuarioLogadoController.isRepresentante() ? usuarioLogadoController.getUsuarioLogado().getIdTop() : 0;
    }

    public void opcao() {
        if (getOpcao().equalsIgnoreCase("1")) {
            setDataInicial(null);
            setDataFinal(null);
            setBloqueiaDatas(true);
        }
        if (getOpcao().equalsIgnoreCase("2")) {
            setDataInicial(LocalDate.of(2010, 1, 1));
            setDataFinal(LocalDate.now().minusDays(1));
            setBloqueiaDatas(true);
        }
        if (getOpcao().equalsIgnoreCase("3")) {
            setDataInicial(null);
            setDataFinal(null);
            setBloqueiaDatas(false);
        }
    }

    public void limpar() {
        setDataInicial(null);
        setDataFinal(null);
        setOpcao("");
        setBloqueiaDatas(true);
        setContasReceber(new ArrayList<>());
    }

    @Override
    public URLProviderImpl getUrlProvider() {
        return (URLProviderImpl) this.getUrlProviderInstance();
    }

    public void buscaContasReceber() {
        try {
            if (getOpcao().equalsIgnoreCase("1")) {
                setContasReceber(getService().buscaTodasContasReceber(idVendedor));
            }
            //data setada manualmente (fixo) por conta do bug do primefaces de não executar o converter em campos disabled
            if (getOpcao().equalsIgnoreCase("2")){
                setContasReceber(getService().buscaContasReceberVencimento(LocalDate.of(2010, 1, 1), LocalDate.now().minusDays(1), idVendedor));
            }
            if (getOpcao().equalsIgnoreCase("3")){
                setContasReceber(getService().buscaContasReceberVencimento(getDataInicial(), getDataFinal(), idVendedor));
            }
        } catch (WebServiceException | SoapFaultException ex) {
            this.getMessagesProvider().reportErrorMessage("WebService offline.");
        }
    }


    //GETTERS E SETTERS
    public LocalDate getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(LocalDate dataInicial) {
        this.dataInicial = dataInicial;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getOpcao() {
        return opcao;
    }

    public void setOpcao(String opcao) {
        this.opcao = opcao;
    }

    public List<ContaReceber> getContasReceber() {
        return contasReceber;
    }

    public void setContasReceber(List<ContaReceber> contasReceber) {
        this.contasReceber = contasReceber;
    }

    public boolean isBloqueiaDatas() {
        return bloqueiaDatas;
    }

    public void setBloqueiaDatas(boolean bloqueiaDatas) {
        this.bloqueiaDatas = bloqueiaDatas;
    }
}
