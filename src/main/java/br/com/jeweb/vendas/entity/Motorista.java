package br.com.jeweb.vendas.entity;

import br.com.jeweb.core.entity.Objeto;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "motorista")
public class Motorista extends Objeto<Long> implements Serializable {

    private static final long serialVersionUID = 2196357183550617833L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_dados_bancarios")
    private DadosBancarios dadosBancarios;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_motorista", nullable = false, referencedColumnName = "id")
    private List<Referencia> referencias;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_motorista", nullable = false, referencedColumnName = "id")
    private List<Telefone> telefones;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            mappedBy = "motorista",
            targetEntity = Frete.class)
    private List<Frete> fretes = new ArrayList<>();

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 14)
    private String cpf;

    @Column(nullable = false)
    private String identidade;

    @Column(nullable = false, name = "org_exp_ident")
    private String orgaoExpedIdent;

    @Column(nullable = false, name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(nullable = false)
    private String habilitacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 2)
    private Categoria categoria;

    @Column(nullable = false, name = "form_habilitacao")
    private String formHabilitacao;

    @Column(name = "nome_pai")
    private String nomePai;

    @Column(nullable = false, name = "nome_mae")
    private String nomeMae;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private String cidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado;

    @Column(nullable = false)
    private String cep;

    @Lob
    @Column
    private byte[] foto;

    @Column(name = "criado_por")
    private String criadoPor;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @Column(name = "modificado_por")
    private String modificadoPor;

    @Column(name = "modificado_em")
    private LocalDateTime modificadoEm;

    public Motorista() {
        this.dadosBancarios = new DadosBancarios();
        this.telefones = new ArrayList<Telefone>();
        this.referencias = new ArrayList<Referencia>();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public DadosBancarios getDadosBancarios() {
        return dadosBancarios;
    }

    public void setDadosBancarios(DadosBancarios dadosBancarios) {
        this.dadosBancarios = dadosBancarios;
    }

    public List<Referencia> getReferencias() {
        return referencias;
    }

    public void setReferencias(List<Referencia> referencias) {
        this.referencias = referencias;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    public List<Frete> getFretes() {
        return fretes;
    }

    public void setFretes(List<Frete> fretes) {
        this.fretes = fretes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getIdentidade() {
        return identidade;
    }

    public void setIdentidade(String identidade) {
        this.identidade = identidade;
    }

    public String getOrgaoExpedIdent() {
        return orgaoExpedIdent;
    }

    public void setOrgaoExpedIdent(String orgaoExpedIdent) {
        this.orgaoExpedIdent = orgaoExpedIdent;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getHabilitacao() {
        return habilitacao;
    }

    public void setHabilitacao(String habilitacao) {
        this.habilitacao = habilitacao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getFormHabilitacao() {
        return formHabilitacao;
    }

    public void setFormHabilitacao(String formHabilitacao) {
        this.formHabilitacao = formHabilitacao;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Motorista motorista = (Motorista) o;
        return Objects.equals(id, motorista.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Motorista{" +
                "id=" + id +
                ", dadosBancarios=" + dadosBancarios +
                ", referencias=" + referencias +
                ", telefones=" + telefones +
                ", fretes=" + fretes +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", identidade='" + identidade + '\'' +
                ", orgaoExpedIdent='" + orgaoExpedIdent + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", habilitacao='" + habilitacao + '\'' +
                ", categoria=" + categoria +
                ", formHabilitacao='" + formHabilitacao + '\'' +
                ", nomePai='" + nomePai + '\'' +
                ", nomeMae='" + nomeMae + '\'' +
                ", endereco='" + endereco + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado=" + estado +
                ", cep='" + cep + '\'' +
                ", foto=" + Arrays.toString(foto) +
                ", criadoPor='" + criadoPor + '\'' +
                ", criadoEm=" + criadoEm +
                ", modificadoPor='" + modificadoPor + '\'' +
                ", modificadoEm=" + modificadoEm +
                '}';
    }
}
