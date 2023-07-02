package br.com.jeweb.vendas.converter;

import br.com.jeweb.core.converter.AbstractConverter;
import br.com.jeweb.vendas.controller.UsuarioLogadoController;
import br.com.jeweb.vendas.entity.webservice.Cliente;
import br.com.jeweb.vendas.service.ClienteService;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Jonas on 05/07/17.
 */
@Named
@RequestScoped
public class ClienteConverter extends AbstractConverter<Cliente> {

    @Inject
    private ClienteService clienteService;

    @Inject
    private UsuarioLogadoController usuarioLogadoController;

    public ClienteConverter() {}

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().equalsIgnoreCase("")) {
            return null;
        }
        Cliente obj = null;
        try {
            for (Cliente c : usuarioLogadoController.getClientesVendedor()) {
                if (c.getId().equals(Integer.parseInt(value))) {
                    obj = c;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (obj == null) {
            throw new ConverterException(new FacesMessage("Identificação nula: " + value));
        }
        return obj;
    }

    @Override
    public ClienteService getService() {
        return clienteService;
    }


}
