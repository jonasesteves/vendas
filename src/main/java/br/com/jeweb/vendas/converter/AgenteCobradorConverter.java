package br.com.jeweb.vendas.converter;

import br.com.jeweb.core.converter.AbstractConverter;
import br.com.jeweb.core.service.AbstractService;
import br.com.jeweb.vendas.controller.UsuarioLogadoController;
import br.com.jeweb.vendas.entity.webservice.AgenteCobrador;
import br.com.jeweb.vendas.service.AgenteCobradorService;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class AgenteCobradorConverter extends AbstractConverter<AgenteCobrador> {

    @Inject
    private AgenteCobradorService agenteCobradorService;

    @Inject
    private UsuarioLogadoController usuarioLogadoController;

    public AgenteCobradorConverter() {
    }

    @Override
    public AbstractService<AgenteCobrador> getService() {
        return agenteCobradorService;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().equalsIgnoreCase("")) {
            return null;
        }
        AgenteCobrador obj = null;
        try {
            for (AgenteCobrador a : usuarioLogadoController.getAgentesCobradores()) {
                if (a.getId().equals(Integer.parseInt(value))) {
                    obj = a;
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
