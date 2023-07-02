
package br.com.jeweb.vendas.service;

import br.com.jeweb.core.service.AbstractService;
import br.com.jeweb.vendas.entity.Usuario;
import br.com.jeweb.vendas.filter.UsuarioFilter;
import br.com.jeweb.vendas.repository.UsuarioRepository;
import br.com.jeweb.vendas.repository.WebServiceRepository;
import br.com.jeweb.vendas.util.EnviarEmail;
import br.com.jeweb.vendas.util.Senha;
import org.apache.commons.mail.EmailException;
import org.datacontract.schemas._2004._07.incopa_servicos_integracao_utilidade.VendedorET;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.xml.ws.WebServiceException;
import java.time.LocalDateTime;
import java.util.List;


@Dependent
public class UsuarioService extends AbstractService<Usuario> {

    @Inject
    private UsuarioRepository usuarioRepository;

    @Inject
    private WebServiceRepository webServiceRepository;

    @Inject UsuarioFilter usuarioFilter;

    public UsuarioService() {
    }

    @Override
    public UsuarioFilter getFilter() {
        return this.usuarioFilter;
    }

    public Usuario getByEmail(String email) {
        return this.getRepository().getByEmail(email);
    }

    public Usuario getByIdTop(Integer idTop) {
        return this.getRepository().getIdTop(idTop);
    }

    public void updateUsuario(Usuario usuario) throws EmailException {
        if (usuario.isEnviarSenha()) {

            Senha s = new Senha();
            String senha = s.getSenha(8);

            EnviarEmail email = new EnviarEmail();
            String emailRemetente = "naoresponda@incopa.com.br";
            String nomeRemetente = "INCOPA";
            String assunto = "Sua senha de acesso ao Sistema RH";
            String mensagem = "Prezado(a),\n"
                    + "Segue abaixo seu nome de usuário e senha para acesso ao Sistema de Vendas.\n\n"
                    + "Usuário: " + usuario.getEmail() + "\n"
                    + "Senha: " + senha + "\n\n"
                    + "O link para acesso ao Sistema de RH é: http://vendas.incopa.com.br:8080\n"
                    + "Este é um e-mail automático. Por favor, não responda.\n\n"
                    + "Atenciosamente,\n"
                    + "Administração.";
            String destino = usuario.getEmail();

            usuario.setSenha(new BCryptPasswordEncoder().encode(senha));

            try {
                email.enviarEmail(emailRemetente, nomeRemetente, assunto, mensagem, destino);
            } catch (EmailException e) {
                throw new EmailException("Não foi possível enviar um e-mail com a senha para o usuário. Por favor, tente mais tarde.");

            }
        }
        this.getRepository().update(usuario);
    }


    @Override
    public UsuarioRepository getRepository() {
        return this.usuarioRepository;
    }

    public void sincronizar() throws WebServiceException {
        try {
            List<VendedorET> vendedores = webServiceRepository.buscaVendedores();
            for (VendedorET vendedorET : vendedores) {
                if (this.getRepository().isNew(vendedorET.getID())) {
                    Usuario usuario = new Usuario();
                    usuario.setIdTop(vendedorET.getID());
                    usuario.setNome(vendedorET.getNome().getValue());
                    usuario.setAtivo(false);
                    usuario.setCriadoPor("importador");
                    usuario.setCriadoEm(LocalDateTime.now());
                    usuario.setModificadoPor("importador");
                    usuario.setModificadoEm(LocalDateTime.now());

                    this.getRepository().save(usuario);
                }
            }
        }
        catch (Exception e) {
            throw new WebServiceException("Não foi possível estabelecer uma conexão com o Web Service. Tente novamente mais tarde.");
        }
    }

    public List<Usuario> buscaRepresentantes() {
        return getRepository().buscaRepresentantes();
    }
}
