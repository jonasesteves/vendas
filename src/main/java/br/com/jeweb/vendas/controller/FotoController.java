package br.com.jeweb.vendas.controller;

import br.com.jeweb.core.exception.ResourceNotFoundException;
import br.com.jeweb.vendas.service.MotoristaService;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.ByteArrayInputStream;
import java.io.Serializable;

@Named
@SessionScoped
public class FotoController implements Serializable {

    private static final long serialVersionUID = 6202866549983210854L;

    @Inject
    private MotoristaService motoristaService;

    public StreamedContent getFoto() {
        try {
            String idMotorista = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
            return new DefaultStreamedContent(new ByteArrayInputStream((motoristaService.findOne(idMotorista)).getFoto()));
        } catch (ResourceNotFoundException e) {
            return null;
        }
        catch (NullPointerException e) {
            return null;
        }
    }
}
