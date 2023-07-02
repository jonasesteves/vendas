package br.com.jeweb.vendas.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Created by desenvolvimento on 10/07/17.
 */
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
    public SecurityWebApplicationInitializer() {
        super(SecurityConfig.class);
    }
}
