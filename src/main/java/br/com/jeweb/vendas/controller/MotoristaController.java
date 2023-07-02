package br.com.jeweb.vendas.controller;

import br.com.jeweb.core.controller.AbstractController;
import br.com.jeweb.core.exception.ApplicationException;
import br.com.jeweb.core.exception.ResourceNotFoundException;
import br.com.jeweb.vendas.datamodel.MotoristaDataModel;
import br.com.jeweb.vendas.entity.*;
import br.com.jeweb.vendas.service.MotoristaService;
import br.com.jeweb.vendas.util.url.URLProviderImpl;
import org.apache.commons.io.FileUtils;
import org.primefaces.event.FileUploadEvent;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;

@Named
@ViewScoped
public class MotoristaController extends AbstractController<Motorista, MotoristaService, MotoristaDataModel> {

    private Telefone telefone = new Telefone();
    private Referencia referencia = new Referencia();
    private Destino destino = new Destino();
    private Cidade cidade = new Cidade();
    private Frete frete = new Frete();
    private File fotoTirada;
    private String nomeFoto;
    private String endFtReduz;
    private String enderecoFoto;

    @Inject
    private UsuarioLogadoController usuarioLogadoController;

    @Inject
    public MotoristaController(MotoristaService motoristaService, MotoristaDataModel motoristaDataModel) {
        super(motoristaService, motoristaDataModel);
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
    public Motorista getEntidade() {
        return super.getEntidade();
    }

    public void load(String id) {
        try {
            if (!FacesContext.getCurrentInstance().isPostback()) {
                setEntidade(this.getService().findOne(id));
            }
        } catch (ResourceNotFoundException ex) {
            getEntidade().setCpf(id);
        }
    }

    public String salvarFrete() {
        try {
            if (this.entityIsNew()) {
                getFrete().setData(LocalDate.now());
                getFrete().setCriadoPor(usuarioLogadoController.getUsuarioLogado().getNome());
                getFrete().setCriadoEm(LocalDateTime.now());
                getEntidade().setCriadoPor(usuarioLogadoController.getUsuarioLogado().getNome());
                getEntidade().setCriadoEm(LocalDateTime.now());
                getFrete().setMotorista(getEntidade());
                getEntidade().getFretes().add(getFrete());
                getService().save(this.getEntidade());
                this.createEntity();
                this.getMessagesProvider().reportInfoMessage("Frete cadastrado com sucesso.");
            } else {
                getFrete().setData(LocalDate.now());
                getFrete().setCriadoPor(usuarioLogadoController.getUsuarioLogado().getNome());
                getFrete().setCriadoEm(LocalDateTime.now());
                getEntidade().setCriadoPor(usuarioLogadoController.getUsuarioLogado().getNome());
                getEntidade().setCriadoEm(LocalDateTime.now());
                getFrete().setMotorista(getEntidade());
                getEntidade().getFretes().add(getFrete());
                getService().update(this.getEntidade());
                this.getMessagesProvider().reportInfoMessage("Frete atualizado com sucesso.");
            }
            deletarFoto();
            return this.getUrlProvider().getUrlIndexFrete();
        } catch (ApplicationException ex) {
            this.getMessagesProvider().reportErrorMessage("Ocorreu um problema ao tentar salvar as informações. Tente novamente mais tarde.");
        }
        return null;
    }

    public String salvar() {
        try {
            if (this.entityIsNew()) {
                getEntidade().setCriadoPor(usuarioLogadoController.getUsuarioLogado().getNome());
                getEntidade().setCriadoEm(LocalDateTime.now());
                getService().save(this.getEntidade());
                this.createEntity();
                this.getMessagesProvider().reportInfoMessage("Motorista cadastrado com sucesso.");
            } else {
                getEntidade().setModificadoPor(usuarioLogadoController.getUsuarioLogado().getNome());
                getEntidade().setModificadoEm(LocalDateTime.now());
                getService().update(this.getEntidade());
                this.getMessagesProvider().reportInfoMessage("Motorista atualizado com sucesso.");
            }
            deletarFoto();
            return this.getUrlProvider().getUrlIndexMotorista();
        } catch (ApplicationException ex) {
            this.getMessagesProvider().reportErrorMessage("Ocorreu um problema ao tentar salvar as informações. Tente novamente mais tarde.");
        }
        return null;
    }

    public void excluir() {
        try {
            if (this.getEntidade().getFretes().isEmpty()) {
                getService().remove(this.getEntidade());
                this.getMessagesProvider().reportInfoMessage("Motorista excluído com sucesso.");
            } else {
                this.getMessagesProvider().reportErrorMessage("Este motorista já possui frete associado e não pode ser excluído.");
            }
        } catch (ApplicationException ex) {
            this.getMessagesProvider().reportErrorMessage("Ocorreu um erro ao tentar excluir as informações. Tente novamente mais tarde.");
        }
    }

    public void novoTelefone(Telefone telefone) {
        if (telefone.getNumero() != null && telefone.getTipoTelefone() != null && !telefone.getNumero().trim().isEmpty()) {
            getEntidade().getTelefones().add(telefone);
            setTelefone(new Telefone());
        } else {
            this.getMessagesProvider().reportErrorMessage("Preencha todos os dados do telefone.");
        }
    }

    public void excluirTelefone(String numeroTelefone) {
        Iterator<Telefone> it = getEntidade().getTelefones().iterator();
        while (it.hasNext()) {
            Telefone t = it.next();
            if (t.getNumero().equals(numeroTelefone)) {
                it.remove();
            }
        }
    }

    public void novaReferencia(Referencia referencia) {
        if (referencia.getTipoReferencia() != null && referencia.getTelefone() != null && !referencia.getTelefone().trim().isEmpty() && referencia.getNome() != null && !referencia.getNome().trim().isEmpty()) {
            getEntidade().getReferencias().add(referencia);
            setReferencia(new Referencia());
        } else {
            this.getMessagesProvider().reportErrorMessage("Por favor, preencha todos os dados da referência.");
        }
    }

    public void excluirReferencia(String nomeReferencia) {
        Iterator<Referencia> it = getEntidade().getReferencias().iterator();
        while (it.hasNext()) {
            Referencia r = it.next();
            if (r.getNome().equals(nomeReferencia)) {
                it.remove();
            }
        }
    }

    public void novoDestino(Destino destino) {
        if (getFrete().getDestinos().size() < 3) {
            if (getDestino().getNome() != null && !getDestino().getNome().trim().isEmpty()) {
                getFrete().getDestinos().add(destino);
                setDestino(new Destino());
            } else {
                this.getMessagesProvider().reportErrorMessage("Por favor, preencha o nome do destino.");
            }
        } else {
            this.getMessagesProvider().reportErrorMessage("O número máximo de destinos é 3.");
        }
    }

    public void excluirDestino(String nome) {
        getFrete().getDestinos().removeIf(d -> d.getNome().equals(nome));
    }

    public void novaCidade(Cidade cidade) {
        if (getFrete().getCidades().size() < 6) {
            if (getCidade().getNome() != null && !getCidade().getNome().trim().isEmpty()) {
                getFrete().getCidades().add(cidade);
                setCidade(new Cidade());
            } else {
                this.getMessagesProvider().reportErrorMessage("Por favor, preencha o nome da cidade.");
            }
        } else {
            this.getMessagesProvider().reportErrorMessage("O número máximo de cidades é 6.");
        }
    }

    public void excluirCidade(String nome) {
        getFrete().getCidades().removeIf(c -> c.getNome().equals(nome));
    }

    public void confirmarFoto(FileUploadEvent fileUploadEvent) {
        setNomeFoto(fileUploadEvent.getFile().getFileName());

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String caminhoFoto = externalContext.getRealPath("")
                + File.separator + "resources"
                + File.separator + "uploads";

        File pastas = new File(caminhoFoto);
        if (!pastas.exists()) {
            pastas.setWritable(true);
            pastas.mkdirs();
        }

        setEnderecoFoto(caminhoFoto + File.separator + getNomeFoto());
        setEndFtReduz("uploads/" + getNomeFoto());

        setFotoTirada(new File(enderecoFoto));
        try {
            getFotoTirada().createNewFile();
            FileUtils.copyInputStreamToFile(fileUploadEvent.getFile().getInputstream(), getFotoTirada());
            byte[] bytesFoto = FileUtils.readFileToByteArray(getFotoTirada());
            getEntidade().setFoto(bytesFoto);
        } catch (IOException e) {
            this.getMessagesProvider().reportErrorMessage("Ocorreu um erro ao tentar capturar a foto. Tente novamente mais tarde.");
        }
    }

    public void deletarFoto() {
        if (getEnderecoFoto() != null && !getEnderecoFoto().trim().isEmpty()) {
            try {
                File file = new File(getEnderecoFoto());
                file.delete();
            }
            catch (NullPointerException ex) {
                this.getMessagesProvider().reportErrorMessage("Ocorreu um erro ao tentar remover uma foto do servidor. Entre em contato com o Administrador do sistema.");
            }
        }
    }

    public String buscaMotorista() {
        if (getService().exists("cpf", getEntidade().getCpf())) {
            setEntidade(getService().buscaPorCpf(getEntidade().getCpf()));
            return this.getUrlProvider().getUrlCadastrarFrete(getEntidade().getId().toString());
        }
        return this.getUrlProvider().getUrlCadastrarFrete(null);
    }

    public Estado[] estados() {
        return Estado.values();
    }

    public Categoria[] categorias() {
        return Categoria.values();
    }

    public TipoReferencia[] tiposReferencia() {
        return TipoReferencia.values();
    }

    public TipoConta[] tiposConta() {
        return TipoConta.values();
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public Referencia getReferencia() {
        return referencia;
    }

    public void setReferencia(Referencia referencia) {
        this.referencia = referencia;
    }

    public Frete getFrete() {
        return frete;
    }

    public void setFrete(Frete frete) {
        this.frete = frete;
    }

    public File getFotoTirada() {
        return fotoTirada;
    }

    public void setFotoTirada(File fotoTirada) {
        this.fotoTirada = fotoTirada;
    }

    public String getNomeFoto() {
        return nomeFoto;
    }

    public void setNomeFoto(String nomeFoto) {
        this.nomeFoto = nomeFoto;
    }

    public String getEnderecoFoto() {
        return enderecoFoto;
    }

    public void setEnderecoFoto(String enderecoFoto) {
        this.enderecoFoto = enderecoFoto;
    }

    public String getEndFtReduz() {
        return endFtReduz;
    }

    public void setEndFtReduz(String endFtReduz) {
        this.endFtReduz = endFtReduz;
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
