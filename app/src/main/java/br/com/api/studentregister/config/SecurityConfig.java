package br.com.api.studentregister.config;
//No spring precisamos ter um bean
//O Spring funciona por filtros de segurança

//Tipos de filtros:
//Basic authenticator
//Base64
//User and Name
//DefaultLoginFilter
//Filter Security Interceptor

import br.com.api.studentregister.service.StudentRegisterUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

//Basicamente security trabalha com dois critérios:
//Autenticação e Autorização
@EnableWebSecurity // é uma configuração e um bean
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true) //Serve para habilitar a anotação @PreAuthorize
@RequiredArgsConstructor
@SuppressWarnings("java:S5344")
public class SecurityConfig extends WebSecurityConfigurerAdapter { //Essa classe pode ter qualquer nome (É indiferente)

    private final StudentRegisterUserDetailsService studentRegisterUserDetailsService;

    @Override //Aqui é configurado o que é protegido através do protocolo HTTP
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) //Para as aplicações conseguirem pegar o valor do cookie
                .authorizeRequests() //Isso significa que qualquer requisição tem que ser autenticada
                //O mais restritivo tem que vir primeiro:
//                .antMatchers("/students/admin/**").hasRole("ADMIN")
//                .antMatchers("/students/**").hasRole("USER") //Substitui o pré autorize
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()//para autenticar via um formulário de front
                .and()
                .httpBasic();  //Forma de autenticação
    }

    @Override //Configura o gerenciador de autenticação
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Para decodificar uma senha
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        log.info("Password encoded {}", passwordEncoder.encode("root"));

        //Usuário em memória
        auth.inMemoryAuthentication()
                .withUser("kevin2")
                .password(passwordEncoder.encode("root"))
                .roles("ADMIN", "USER")
                .and()
                .withUser("rafael2")
                .password(passwordEncoder.encode("root"))
                .roles("USER");

        //Usuário no banco de dados
        auth.userDetailsService(studentRegisterUserDetailsService).passwordEncoder(passwordEncoder);


    }
}
