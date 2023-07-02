package br.com.jeweb.vendas.validator;

import br.com.jeweb.vendas.util.ValidaCpfCnpj;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.io.Serializable;

@FacesValidator("CPFValidator")
public class CPFValidator implements Validator, Serializable {

    private static final long serialVersionUID = 2350112241716869972L;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String valor = context.getExternalContext().getRequestParameterMap().get(component.getClientId(context));
        if (valor == null || valor.isEmpty() || value == null) {
            return;
        }
        if (!ValidaCpfCnpj.isCPF(value.toString())) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de validação.", "O CPF digitado não é válido."));
        }
    }
}
