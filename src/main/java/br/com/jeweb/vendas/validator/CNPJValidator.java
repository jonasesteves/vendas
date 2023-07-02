package br.com.jeweb.vendas.validator;

import br.com.jeweb.vendas.util.ValidaCpfCnpj;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.io.Serializable;

@FacesValidator("CNPJValidator")
public class CNPJValidator implements Validator, Serializable {
    private static final long serialVersionUID = 23878360215967156L;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String valor = context.getExternalContext().getRequestParameterMap().get(component.getClientId(context));
        if (valor == null || valor.isEmpty() || value == null) {
            return;
        }
        if (!ValidaCpfCnpj.isCNPJ(value.toString())) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de validação.", "O CNPJ digitado não é válido."));
        }
    }
}
