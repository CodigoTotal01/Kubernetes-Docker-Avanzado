package org.aguzman.springcloud.msvc.usuarios;


import org.springframework.context.annotation.Bean;
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

                .anyRequest().authenticated()
                .and()
                //Allways para tymelift - seseiones http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //ponen el nombre del servicio a registrar
                .oauth2Login(oauthLogin -> oauthLogin.loginPage("/oauth2/authorization/msvc-usuarios-client"))
                //se entiende que esata es la agina que msotrara al culminar de acceder
                .oauth2Client(withDefaults()); // configuracion por defecto
        return http.build();
    }

}
