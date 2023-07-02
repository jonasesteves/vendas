package br.com.jeweb.vendas.converter;

import br.com.jeweb.core.converter.AbstractConverter;
import br.com.jeweb.vendas.controller.UsuarioLogadoController;
import br.com.jeweb.vendas.entity.webservice.Empresa;
import br.com.jeweb.vendas.service.EmpresaService;

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
public class EmpresaConverter extends AbstractConverter<Empresa> {

    @Inject
    private EmpresaService empresaService;

    @Inject
    private UsuarioLogadoController usuarioLogadoController;

    public EmpresaConverter() {}

    @Override
    public EmpresaService getService() {
        return empresaService;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().equalsIgnoreCase("")) {
            return null;
        }
        Empresa obj = null;
        try {
            for (Empresa e : usuarioLogadoController.getEmpresas()) {
                if (e.getId().equals(Integer.parseInt(value))) {
                    obj = e;
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
