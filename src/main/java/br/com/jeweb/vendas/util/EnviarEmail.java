/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jeweb.vendas.util;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author Jonas Esteves
 * @see contato@jonasesteves.com
 */
public class EnviarEmail {

    private final Email email;

    public EnviarEmail() {
        this.email = new SimpleEmail();
        configuracao();
    }

    private void configuracao() {
        this.email.setHostName("mail.incopa.com.br");
        this.email.setSmtpPort(587);
        this.email.setDebug(true);
        this.email.setAuthentication("naoresponda@incopa.com.br", "naoresponda12");
        this.email.setSSLOnConnect(false);
    }

    public boolean enviarEmail(String emailRemetente, String nomeRemetente, String assunto, String mensagem, String destino) throws EmailException {
        try {
            this.email.setFrom(emailRemetente, nomeRemetente);
            this.email.setSubject(assunto);
            this.email.setMsg(mensagem);
            this.email.addTo(destino);
            this.email.addReplyTo(emailRemetente);
            this.email.setCharset("UTF-8");
            this.email.send();
            
            return true;
            
        } catch (EmailException ex) {
            throw new EmailException("Ocorreu um erro ao tentar enviar e-mail. Tente novamente mais tarde.");
        }
    }
}
