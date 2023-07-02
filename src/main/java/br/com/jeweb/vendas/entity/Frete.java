package br.com.jeweb.vendas.entity;

import br.com.jeweb.core.entity.Objeto;

import javax.persistence.*;
import java.awt.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "frete")
public class Frete extends Objeto<Long> implements Serializable {

    private static final long serialVersionUID = -2207700243853428747L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_motorista")
    private Motorista motorista;

    @Column
    private BigDecimal valor;

    @Column
    private BigDecimal adiantamento;

    @Column
    private BigDecimal saldo;

    @Column(nullable = false, name = "quant_fardos")
    private BigDecimal quantidadeFardos;

    @Column(nullable = false, name = "valor_frete")
    private BigDecimal valorFrete;

    @Column(name = "media_fardos")
    private BigDecimal mediaFardos;

    @Column
    private BigDecimal pamcary;

    @Column(nullable = false)
    private String agenciador;

    @Column(nullable = false)
    private String placa;

    @Column(name = "capacidade_veiculo")
    private BigDecimal capacidadeVeiculo;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_frete", nullable = false, referencedColumnName = "id")
    private List<Destino> destinos;

    @Column(nullable = false, name = "valor_Carga")
    private BigDecimal valorCarga;

    @Column
    private BigDecimal km;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_frete", nullable = false, referencedColumnName = "id")
    private List<Cidade> cidades;

    @Column(nullable = false)
    private String observacao;

    @Column(nullable = false)
    private LocalDate data;

    @Column(name = "criado_por")
    private String criadoPor;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @Column(name = "modificado_por")
    private String modificadoPor;

    @Column(name = "modificado_em")
    private LocalDateTime modificadoEm;

    @Transient
    private Image foto;

    public Frete () {
        this.motorista = new Motorista();
        this.destinos = new ArrayList<>();
        this.cidades = new ArrayList<>();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getAdiantamento() {
        return adiantamento;
    }

    public void setAdiantamento(BigDecimal adiantamento) {
        this.adiantamento = adiantamento;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getQuantidadeFardos() {
        return quantidadeFardos;
    }

    public void setQuantidadeFardos(BigDecimal quantidadeFardos) {
        this.quantidadeFardos = quantidadeFardos;
    }

    public BigDecimal getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }

    public BigDecimal getMediaFardos() {
        return mediaFardos;
    }

    public void setMediaFardos(BigDecimal mediaFardos) {
        this.mediaFardos = mediaFardos;
    }

    public BigDecimal getPamcary() {
        return pamcary;
    }

    public void setPamcary(BigDecimal pamcary) {
        this.pamcary = pamcary;
    }

    public String getAgenciador() {
        return agenciador;
    }

    public void setAgenciador(String agenciador) {
        this.agenciador = agenciador;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public BigDecimal getCapacidadeVeiculo() {
        return capacidadeVeiculo;
    }

    public void setCapacidadeVeiculo(BigDecimal capacidadeVeiculo) {
        this.capacidadeVeiculo = capacidadeVeiculo;
    }

    public List<Destino> getDestinos() {
        return destinos;
    }

    public void setDestinos(List<Destino> destinos) {
        this.destinos = destinos;
    }

    public BigDecimal getValorCarga() {
        return valorCarga;
    }

    public void setValorCarga(BigDecimal valorCarga) {
        this.valorCarga = valorCarga;
    }

    public BigDecimal getKm() {
        return km;
    }

    public void setKm(BigDecimal km) {
        this.km = km;
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
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

    public Image getFoto() {
        return foto;
    }

    public void setFoto(Image foto) {
        this.foto = foto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Frete frete = (Frete) o;
        return Objects.equals(id, frete.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Frete{" +
                "id=" + id +
                ", motorista=" + motorista +
                ", valor=" + valor +
                ", adiantamento=" + adiantamento +
                ", saldo=" + saldo +
                ", quantidadeFardos=" + quantidadeFardos +
                ", valorFrete=" + valorFrete +
                ", mediaFardos=" + mediaFardos +
                ", pamcary=" + pamcary +
                ", agenciador='" + agenciador + '\'' +
                ", placa='" + placa + '\'' +
                ", capacidadeVeiculo=" + capacidadeVeiculo +
                ", destinos=" + destinos +
                ", valorCarga=" + valorCarga +
                ", km=" + km +
                ", cidades=" + cidades +
                ", observacao='" + observacao + '\'' +
                ", data=" + data +
                ", criadoPor='" + criadoPor + '\'' +
                ", criadoEm=" + criadoEm +
                ", modificadoPor='" + modificadoPor + '\'' +
                ", modificadoEm=" + modificadoEm +
                ", foto=" + foto +
                '}';
    }
}
