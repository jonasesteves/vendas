package br.com.jeweb.vendas.converter;

import br.com.jeweb.core.converter.AbstractConverter;
import br.com.jeweb.vendas.controller.UsuarioLogadoController;
import br.com.jeweb.vendas.entity.webservice.FormaPagamento;
import br.com.jeweb.vendas.service.FormaPagamentoService;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class FormaPagamentoConverter extends AbstractConverter<FormaPagamento> {

    @Inject
    private UsuarioLogadoController usuarioLogadoController;

    @Inject
    private FormaPagamentoService formaPagamentoService;

    public FormaPagamentoConverter() {
    }

    @Override
    public FormaPagamentoService getService() {
        return formaPagamentoService;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().equalsIgnoreCase("")) {
            return null;
        }
        FormaPagamento obj = null;
        try {
            for (FormaPagamento f : usuarioLogadoController.getFormasPagamento()) {
                if (f.getId().equals(Integer.parseInt(value))) {
                    obj = f;
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
}
