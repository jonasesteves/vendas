package br.com.jeweb.vendas.controller;

import br.com.jeweb.core.controller.AbstractController;
import br.com.jeweb.core.exception.ApplicationException;
import br.com.jeweb.core.exception.ResourceNotFoundException;
import br.com.jeweb.vendas.datamodel.FreteDataModel;
import br.com.jeweb.vendas.entity.*;
import br.com.jeweb.vendas.service.FreteService;
import br.com.jeweb.vendas.util.url.URLProviderImpl;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Named
@SessionScoped
public class FreteController extends AbstractController<Frete, FreteService, FreteDataModel> {

    private String cpf;
    private Destino destino = new Destino();
    private Cidade cidade = new Cidade();

    @Inject
    private UsuarioLogadoController usuarioLogadoController;

    @Inject
    private RelatorioController relatorioController;

    @Inject
    public FreteController(FreteService freteService, FreteDataModel freteDataModel) {
        super(freteService, freteDataModel);
    }

    @Override
    @PostConstruct
    public void init() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            this.createEntity();
        }
    }

    @Override
    public URLProviderImpl getUrlProvider() {
        return (URLProviderImpl) this.getUrlProviderInstance();
    }

    @Override
    public Frete getEntidade() {
        return super.getEntidade();
    }

    public void load(String id) {
        try {
            if (!FacesContext.getCurrentInstance().isPostback()) {
                setEntidade(this.getService().findOne(id));
            }
        }
        catch (ResourceNotFoundException ex) {
            this.getUrlProvider().redirectFromContext(this.getUrlProvider().getUrlResourceNotFound());
        }
    }

    public String salvar() {
        try {
            if (this.entityIsNew()) {
                getEntidade().setData(LocalDate.now());
                getEntidade().setCriadoPor(usuarioLogadoController.getUsuarioLogado().getNome());
                getEntidade().setCriadoEm(LocalDateTime.now());
                getService().save(this.getEntidade());
                this.createEntity();
                this.getMessagesProvider().reportInfoMessage("Frete cadastrado com sucesso.");
            }
            else {
                getEntidade().setModificadoPor(usuarioLogadoController.getUsuarioLogado().getNome());
                getEntidade().setModificadoEm(LocalDateTime.now());
                getService().update(this.getEntidade());
                this.getMessagesProvider().reportInfoMessage("Frete atualizado com sucesso.");
            }
            return this.getUrlProvider().getUrlIndexFrete();
        }
        catch (ApplicationException ex) {
            this.getMessagesProvider().reportErrorMessage("Ocorreu um problema ao tentar salvar as informações. Tente novamente mais tarde.");
        }
        return null;
    }

    public StreamedContent getFoto() {
        if (getEntidade().getMotorista().getFoto() != null) {
            return new DefaultStreamedContent(new ByteArrayInputStream(getEntidade().getMotorista().getFoto()));
        }
        return null;
    }

    public void novoDestino(Destino destino) {
        if (getEntidade().getDestinos().size() < 3) {
            if (getDestino().getNome() != null && !getDestino().getNome().trim().isEmpty()) {
                getEntidade().getDestinos().add(destino);
                setDestino(new Destino());
            } else {
                this.getMessagesProvider().reportErrorMessage("Por favor, preencha o nome do destino.");
            }
        } else {
            this.getMessagesProvider().reportErrorMessage("O número máximo de destinos é 3.");
        }
    }

    public void excluirDestino(String nome) {
        getEntidade().getDestinos().removeIf(d -> d.getNome().equals(nome));
    }

    public void novaCidade(Cidade cidade) {
        if (getEntidade().getCidades().size() < 6) {
            if (getCidade().getNome() != null && !getCidade().getNome().trim().isEmpty()) {
                getEntidade().getCidades().add(cidade);
                setCidade(new Cidade());
            } else {
                this.getMessagesProvider().reportErrorMessage("Por favor, preencha o nome da cidade.");
            }
        } else {
            this.getMessagesProvider().reportErrorMessage("O número máximo de cidades é 6.");
        }
    }

    public void excluirCidade(String nome) {
        getEntidade().getCidades().removeIf(c -> c.getNome().equals(nome));
    }

    public void imprimirContrato(Long idFrete) {
        relatorioController.gerarContrato(idFrete);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Destino getDestino() {
        return destino;
    }

    public void setDestino(Destino destino) {
        this.destino = destino;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
}
