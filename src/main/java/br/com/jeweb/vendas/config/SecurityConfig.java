package br.com.jeweb.vendas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${app.context.data-source}")
    private String dataSource;

    public SecurityConfig() {
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource())
                .usersByUsernameQuery("select email as username, senha as password, ativo as enabled from usuario where email = ?")
                .authoritiesByUsernameQuery("select u.email as username, t.role from usuario u inner join tipo_usuario t where u.id_tipo_usuario = t.id and u.email = ?")
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.csrf().disable();

        http
            .authorizeRequests()
                .antMatchers("/javax.faces.resource/**").permitAll()
                .antMatchers("/default/**").permitAll()
                .antMatchers("/app/index.xhtml").hasAnyRole("Administrador","Representante", "Financeiro", "Almoxarifado", "Comercial")

                //USUARIO
                .antMatchers("/app/usuario/editar-conta.xhtml").permitAll()
                .antMatchers("/app/usuario/**").hasAnyRole("Administrador")

                //PEDIDOS PENDENTES
                .antMatchers("/app/pedido-pendente/**").hasAnyRole("Administrador", "Comercial")

                //NOVO PEDIDO
                .antMatchers("/app/novo-pedido/**").hasAnyRole("Representante")

                //PEDIDO (GERAL)
                .antMatchers("/app/pedido/**").hasAnyRole("Administrador", "Representante", "Comercial")

                //PEDIDO ABERTO
                .antMatchers("/app/pedido-aberto/**").hasAnyRole("Representante")

                //PEDIDO EMBARCADO
                .antMatchers("/app/pedido-embarcado/**").hasAnyRole("Representante")

                //PEDIDO FATURADO
                .antMatchers("/app/pedido-faturado/**").hasAnyRole("Representante")

                //ESTOQUE
                .antMatchers("/app/mapa-producao/**").hasAnyRole("Administrador", "Almoxarifado")

                //FINANCEIRO
                .antMatchers("/app/comissao/**").hasAnyRole("Administrador", "Representante")
                .antMatchers("/app/conta-receber/**").hasAnyRole("Administrador", "Representante", "Financeiro")
                .antMatchers("/app/contato-cliente/**").hasAnyRole("Administrador", "Financeiro")

                //CLIENTES
                .antMatchers("/app/cliente/**").hasAnyRole("Representante")

                //FRETES
                .antMatchers("/app/frete/**").hasAnyRole("Administrador", "Comercial")
                .antMatchers("/app/motorista/**").hasAnyRole("Administrador", "Comercial")

                //APP
                .antMatchers("/app/**").hasRole("Administrador")
//                .antMatchers("/app/**", "/cadastro-empresas.jsf").hasRole("ADMINISTRADOR")
//                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login.xhtml")
//                .successForwardUrl("/app/index.xhtml")
//                .failureForwardUrl("/default/access.xhtml")
                .usernameParameter("username").passwordParameter("password")
                .failureUrl("/login.xhtml?login=false").permitAll()
                .and()
            .logout()
                .logoutSuccessUrl("/login.xhtml").permitAll()
                .and()
            .exceptionHandling()
                .accessDeniedPage("/default/access.xhtml");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Bean
    public DataSource dataSource() {
        JndiDataSourceLookup jndiDataSourceLookup = new JndiDataSourceLookup();
        return jndiDataSourceLookup.getDataSource(dataSource);
    }
}
