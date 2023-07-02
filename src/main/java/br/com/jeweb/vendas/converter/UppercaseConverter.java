package br.com.jeweb.vendas.converter;

import br.com.jeweb.core.converter.AbstractConverter;
import br.com.jeweb.vendas.entity.Item;
import br.com.jeweb.vendas.service.ItemService;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;


@Named
@RequestScoped
public class UppercaseConverter implements Converter, Serializable {

    private static final long serialVersionUID = 2592208780826965030L;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return s.toUpperCase();
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o == null) {
            return "";
        }
        return o.toString().toUpperCase();
    }
}
