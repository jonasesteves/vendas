package br.com.jeweb.vendas.entity.webservice;

import br.com.jeweb.core.entity.Objeto;

import java.time.LocalDate;
import java.util.Objects;

public class Cliente extends Objeto<Integer> {
    private Integer id;
    private String nome;
    private String cpfCnpj;
    private String endereco;
    private String cidade;
    private String telefone;
    private String telefone2;
    private String telefone3;
    private String fax;
    private String celular;
    private String celularClaro;
    private String celularOi;
    private String celularTim;
    private String email;
    private String situacao;
    private String motivo;
    private String contato;
    private Integer idAreaVenda;
    private LocalDate dataUltimaVenda;

    public Cliente() {
    }

    public Cliente (Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getTelefone3() {
        return telefone3;
    }

    public void setTelefone3(String telefone3) {
        this.telefone3 = telefone3;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCelularClaro() {
        return celularClaro;
    }

    public void setCelularClaro(String celularClaro) {
        this.celularClaro = celularClaro;
    }

    public String getCelularOi() {
        return celularOi;
    }

    public void setCelularOi(String celularOi) {
        this.celularOi = celularOi;
    }

    public String getCelularTim() {
        return celularTim;
    }

    public void setCelularTim(String celularTim) {
        this.celularTim = celularTim;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public Integer getIdAreaVenda() {
        return idAreaVenda;
    }

    public void setIdAreaVenda(Integer idAreaVenda) {
        this.idAreaVenda = idAreaVenda;
    }

    public LocalDate getDataUltimaVenda() {
        return dataUltimaVenda;
    }

    public void setDataUltimaVenda(LocalDate dataUltimaVenda) {
        this.dataUltimaVenda = dataUltimaVenda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpfCnpj='" + cpfCnpj + '\'' +
                ", endereco='" + endereco + '\'' +
                ", cidade='" + cidade + '\'' +
                ", telefone='" + telefone + '\'' +
                ", telefone2='" + telefone2 + '\'' +
                ", telefone3='" + telefone3 + '\'' +
                ", fax='" + fax + '\'' +
                ", celular='" + celular + '\'' +
                ", celularClaro='" + celularClaro + '\'' +
                ", celularOi='" + celularOi + '\'' +
                ", celularTim='" + celularTim + '\'' +
                ", email='" + email + '\'' +
                ", situacao='" + situacao + '\'' +
                ", motivo='" + motivo + '\'' +
                ", contato='" + contato + '\'' +
                ", idAreaVenda=" + idAreaVenda +
                ", dataUltimaVenda=" + dataUltimaVenda +
                '}';
    }
}
