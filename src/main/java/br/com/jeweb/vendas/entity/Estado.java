package br.com.jeweb.vendas.entity;

public enum Estado {
    AC("Acre"),
    AL("Alagoas"),
    AM("Amazonas"),
    AP("Amapá"),
    BA("Bahia"),
    CE("Ceará"),
    DF("Distrito Federal"),
    ES("Espírito Santo"),
    GO("Goiás"),
    MA("Maranhão"),
    MG("Minas Gerais"),
    MS("Mato Grosso do Sul"),
    MT("Mato Grosso"),
    PA("Pará"),
    PB("Paraíba"),
    PE("Pernanbuco"),
    PI("Piauí"),
    PR("Paraná"),
    RJ("Rio de Janeiro"),
    RN("Rio Grande do Norte"),
    RS("Rio Grande do Sul"),
    RO("Rondônia"),
    RR("Roraima"),
    SC("Santa Catarina"),
    SE("Sergipe"),
    SP("São Paulo"),
    TO("Tocantins");

    private String estado;

    Estado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return this.estado;
    }
}
