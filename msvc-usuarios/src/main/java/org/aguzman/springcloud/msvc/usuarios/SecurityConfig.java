package org.aguzman.springcloud.msvc.usuarios;


import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


//Not found Annotation - @EnableWebSecurity
@EnableWebSecurity //es una configuracion
public class SecurityConfig {
    //quedara como un componente de bing configurado en el archivo de configuracion de spring security
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //indicar que rutas estaran disponibles en nuestro programa
                .antMatchers("/authorized").permitAll() // pa todos 👌
                .antMatchers(HttpMethod.GET,"/", "/{id}").hasAnyAuthority("SCOPE_read", "SCOPE_write")
                .antMatchers(HttpMethod.POST, "/").hasAnyAuthority("SCOPE_write")
                .antMatchers(HttpMethod.PUT, "/{id}").hasAnyAuthority("SCOPE_write")
                .antMatchers(HttpMethod.DELETE, "/{id}").hasAnyAuthority("SCOPE_write")

                .anyRequest().authenticated()
                .and()
                //Allways para tymelift - seseiones http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //ponen el nombre del servicio a registrar
                .oauth2Login(oauthLogin -> oauthLogin.loginPage("/oauth2/authorization/msvc-usuarios-client")).csrf().disable()
                //se entiende que esata es la agina que msotrara al culminar de acceder
//                .oauth2Client(withDefaults()) // configuracion por defecto
                .oauth2Client(withDefaults()).csrf().disable() //desactibamos este token de seguridad que nos daba el formulario
                .oauth2ResourceServer().jwt(); //supongo que esto es lo que retornara
        return http.build();
    }

}
