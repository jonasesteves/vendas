package br.com.jeweb.vendas.teste;

import br.com.jeweb.vendas.repository.WebServiceRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.datacontract.schemas._2004._07.incopa_servicos_integracao_utilidade.ClienteVendedorET;

import java.util.List;

public class Teste {

    private static Logger logger = LogManager.getLogger(Teste.class.getName());

    public static void main(String[] args) {

        logger.error("Teste do log4j2 obteve sucesso! Teste do log4j2 obteve sucesso! Teste do log4j2 obteve sucesso! Teste do log4j2 obteve sucesso! Teste do log4j2 obteve sucesso!");

//        WebServiceRepository w = new WebServiceRepository();
//        List<ClienteVendedorET> c = w.buscaClientesVendedor(19890);
//
//        for (ClienteVendedorET cv : c) {
//            System.out.println(cv.getNome().getValue());
//        }

    }
}
