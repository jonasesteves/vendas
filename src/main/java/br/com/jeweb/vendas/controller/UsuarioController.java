package br.com.jeweb.vendas.controller;

import br.com.jeweb.core.controller.AbstractController;
import br.com.jeweb.core.exception.ResourceNotFoundException;
import br.com.jeweb.vendas.datamodel.UsuarioDataModel;
import br.com.jeweb.vendas.entity.Usuario;
import br.com.jeweb.vendas.service.UsuarioService;
import br.com.jeweb.vendas.util.url.URLProviderImpl;
import org.apache.commons.mail.EmailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.ws.WebServiceException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author desenvolvimento
 */
@Named
@ViewScoped
public class UsuarioController extends AbstractController<Usuario, UsuarioService, UsuarioDataModel> {

    private String senha;
    private String novaSenha;
    private Usuario usuario = new Usuario();
    private List<Usuario> usuarios;

    @Inject
    private UsuarioLogadoController usuarioLogadoController;

    @Inject
    public UsuarioController(UsuarioService usuarioService, UsuarioDataModel usuarioDataModel) {
        super(usuarioService, usuarioDataModel);
    }

    @PostConstruct
    public void init() {
        this.usuarios = this.getService().findAll();
    }

    @Override
    public URLProviderImpl getUrlProvider() {
        return (URLProviderImpl) this.getUrlProviderInstance();
    }

    @Override
    public UsuarioService getService() {
        return super.getService();
    }

    public void load(String id) {
        try {
            if (!FacesContext.getCurrentInstance().isPostback()) {
                setUsuario(this.getService().findOne(id));
            }
        } catch (ResourceNotFoundException ex) {
            this.getUrlProvider().redirectFromContext(this.getUrlProvider().getUrlResourceNotFound());
        }
    }

    public String update() {

        try {
            usuario.setModificadoPor(usuarioLogadoController.getUsuarioLogado().getNome());
            usuario.setModificadoEm(LocalDateTime.now());
            this.getService().updateUsuario(this.usuario);

            try {
                this.setUsuario(this.usuario.getClass().newInstance());
            } catch (IllegalAccessException | InstantiationException e) {
                this.getMessagesProvider().reportErrorMessage("Ocorreu um erro ao tentar salvar o usuário.");
            }
            this.getMessagesProvider().reportInfoMessage("Usuário atualizado com sucesso.");
            return this.getUrlProvider().getUrlIndexUsuario();
        } catch (EmailException e) {
            this.getMessagesProvider().reportErrorMessage(e.getMessage());
        } catch (NullPointerException e) {
            this.getMessagesProvider().reportErrorMessage("Ocorreu um erro interno. Por favor, efetue o login novamente.");
        }
        return null;
    }

    public void sincronizar() {
        try {
            this.getService().sincronizar();
            setUsuarios(getService().findAll());
            this.getMessagesProvider().reportInfoMessage("Usuários sincronizados com sucesso.");
        } catch (WebServiceException e) {
            this.getMessagesProvider().reportErrorMessage(e.getMessage());
        }
    }

    public String atualizaSenha() {
        if (new BCryptPasswordEncoder().matches(this.senha, usuarioLogadoController.getUsuarioLogado().getSenha())) {
            setUsuario(usuarioLogadoController.getUsuarioLogado());
            usuario.setModificadoPor(usuarioLogadoController.getUsuarioLogado().getNome());
            usuario.setModificadoEm(LocalDateTime.now());
            usuario.setSenha(new BCryptPasswordEncoder().encode(this.getNovaSenha()));

            this.getService().update(this.usuario);
            try {
                this.setUsuario(this.usuario.getClass().newInstance());
            } catch (IllegalAccessException | InstantiationException e) {
                this.getMessagesProvider().reportErrorMessage("Ocorreu um erro ao tentar salvar o usuário.");
            }

            this.getMessagesProvider().reportInfoMessage("Senha atualizada com sucesso.");
            return this.getUrlProvider().getUrlIndex();
        }
        this.getMessagesProvider().reportErrorMessage("A senha atual está incorreta.");
        return null;
    }

    public String removerCredenciais() {
        try {
            usuario.setEmail(null);
            usuario.setSenha(null);
            usuario.setTipoUsuario(null);
            usuario.setAtivo(false);
            usuario.setModificadoPor(usuarioLogadoController.getUsuarioLogado().getNome());
            usuario.setModificadoEm(LocalDateTime.now());
            this.getService().updateUsuario(this.usuario);

            try {
                this.setUsuario(this.usuario.getClass().newInstance());
            } catch (IllegalAccessException | InstantiationException e) {
                this.getMessagesProvider().reportErrorMessage("Ocorreu um erro ao tentar salvar o usuário.");
            }
            this.getMessagesProvider().reportInfoMessage("Usuário atualizado com sucesso.");
            return this.getUrlProvider().getUrlIndexUsuario();
        } catch (EmailException e) {
            this.getMessagesProvider().reportErrorMessage(e.getMessage());
        } catch (NullPointerException e) {
            this.getMessagesProvider().reportErrorMessage("Ocorreu um erro interno. Por favor, efetue o login novamente.");
        }
        return null;
    }

    public List<Usuario> getRepresentantes() {
        return getService().buscaRepresentantes();
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogadoController.getUsuarioLogado();
    }
}
