package br.com.jeweb.vendas.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.io.Serializable;
import java.util.Map;
import java.util.regex.Pattern;

@FacesValidator("EmailValidator")
public class EmailValidator implements Validator, Serializable {
    private static final long serialVersionUID = 23878360215967156L;

    private Pattern pattern;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if(value == null) {
            return;
        }

        if(!pattern.matcher(value.toString()).matches()) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de validação", "O email digitado não é válido."));
        }
    }

    public Map<String, Object> getMetadata() {
        return null;
    }

    public String getValidatorId() {
        return "custom.emailValidator";
    }
}
