package br.com.jeweb.vendas.converter;

import br.com.jeweb.core.converter.AbstractConverter;
import br.com.jeweb.core.service.AbstractService;
import br.com.jeweb.vendas.controller.UsuarioLogadoController;
import br.com.jeweb.vendas.entity.webservice.TipoCobranca;
import br.com.jeweb.vendas.service.TipoCobrancaService;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class TipoCobrancaConverter extends AbstractConverter<TipoCobranca> {

    @Inject
    private TipoCobrancaService tipoCobrancaService;

    @Inject
    private UsuarioLogadoController usuarioLogadoController;

    public TipoCobrancaConverter() {
    }

    @Override
    public AbstractService<TipoCobranca> getService() {
        return tipoCobrancaService;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().equalsIgnoreCase("")) {
            return null;
        }
        TipoCobranca obj = null;
        try {
            for (TipoCobranca t : usuarioLogadoController.getTiposCobranca()) {
                if (t.getId().equals(Integer.parseInt(value))) {
                    obj = t;
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
