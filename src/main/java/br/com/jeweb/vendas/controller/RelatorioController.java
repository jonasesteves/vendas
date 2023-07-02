package br.com.jeweb.vendas.controller;

import br.com.jeweb.vendas.entity.Frete;
import br.com.jeweb.vendas.service.FreteService;
import br.com.jeweb.vendas.util.Relatorio;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.io.Serializable;

@Dependent
public class RelatorioController implements Serializable {

    private static final long serialVersionUID = -373279563099303741L;

    @Inject
    private FreteService freteService;

    @Inject
    private Relatorio relatorio;

    public Frete frete(Long idFrete) {
        return freteService.findOne(idFrete);
    }

    public void gerarContrato(Long idFrete) {
        relatorio.gerarContrato(frete(idFrete));
    }
}
