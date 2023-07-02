package br.com.jeweb.vendas.converter;

import br.com.jeweb.core.converter.AbstractConverter;
import br.com.jeweb.vendas.controller.UsuarioLogadoController;
import br.com.jeweb.vendas.entity.webservice.TipoBonificacao;
import br.com.jeweb.vendas.service.TipoBonificacaoService;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by desenvolvimento on 05/07/17.
 */
@Named
@RequestScoped
public class TipoBonificacaoConverter extends AbstractConverter<TipoBonificacao> {

    @Inject
    private TipoBonificacaoService tipoBonificacaoService;

    @Inject
    private UsuarioLogadoController usuarioLogadoController;

    public TipoBonificacaoConverter() {}

    @Override
    public TipoBonificacaoService getService() {
        return tipoBonificacaoService;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().equalsIgnoreCase("")) {
            return null;
        }
        TipoBonificacao obj = null;
        try {
            for (TipoBonificacao t : usuarioLogadoController.getTiposBonificacao()) {
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
