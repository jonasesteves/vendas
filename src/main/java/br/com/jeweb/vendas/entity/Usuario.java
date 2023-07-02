/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jeweb.vendas.entity;

import br.com.jeweb.core.entity.Objeto;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Jonas Esteves
 */
@Entity
@Table(name = "usuario")
public class Usuario extends Objeto<Long> implements Serializable{
    
    private static final long serialVersionUID = -5161329352241937322L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "id_tipo_usuario")
    private TipoUsuario tipoUsuario;
    
    @Column(name = "id_top")
    private Integer idTop;
    
    @Column(nullable = false)
    private String nome;
    
    @Column
    private String email;
    
    @Column
    private String senha;
    
    @Column(nullable = false)
    private boolean ativo;

    @Column(name = "ultimo_login")
    private LocalDateTime ultimoLogin;
    
    @Column(name = "criado_por")
    private String criadoPor;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
    
    @Column(name = "modificado_por")
    private String modificadoPor;

    @Column(name = "modificado_em")
    private LocalDateTime modificadoEm;

    @Transient
    private boolean enviarSenha;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Integer getIdTop() {
        return idTop;
    }

    public void setIdTop(Integer idTop) {
        this.idTop = idTop;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin(LocalDateTime ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

    public String getCriadoPor() {
        return criadoPor;
    }

    public void setCriadoPor(String criadoPor) {
        this.criadoPor = criadoPor;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public String getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(String modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    public LocalDateTime getModificadoEm() {
        return modificadoEm;
    }

    public void setModificadoEm(LocalDateTime modificadoEm) {
        this.modificadoEm = modificadoEm;
    }

    public boolean isEnviarSenha() {
        return enviarSenha;
    }

    public void setEnviarSenha(boolean enviarSenha) {
        this.enviarSenha = enviarSenha;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", tipoUsuario=" + tipoUsuario +
                ", idTop=" + idTop +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", ativo=" + ativo +
                ", ultimoLogin=" + ultimoLogin +
                ", criadoPor='" + criadoPor + '\'' +
                ", criadoEm=" + criadoEm +
                ", modificadoPor='" + modificadoPor + '\'' +
                ", modificadoEm=" + modificadoEm +
                ", enviarSenha=" + enviarSenha +
                '}';
    }
}
