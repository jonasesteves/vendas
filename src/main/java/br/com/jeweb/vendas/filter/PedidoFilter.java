package br.com.jeweb.vendas.filter;

import br.com.jeweb.vendas.entity.Pedido;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class PedidoFilter implements Serializable {

    private PedidoFiltro pedidoFiltro;

    public PedidoFilter() {
        this.reset();
    }

    public void fill (List<Pedido> pedidos, Map<String, String> map) {

    }


    public void reset() {
        this.pedidoFiltro = new PedidoFiltro();
    }

    public static class PedidoFiltro implements Serializable {

        private static final long serialVersionUID = -1011683576718580261L;

        private String cliente;

        public String getCliente() {
            return cliente;
        }

        public void setCliente(String cliente) {
            this.cliente = cliente;
        }
    }
}
