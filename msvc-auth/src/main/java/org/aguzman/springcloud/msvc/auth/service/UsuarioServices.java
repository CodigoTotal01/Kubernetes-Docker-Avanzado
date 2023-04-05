package org.aguzman.springcloud.msvc.auth.service;

import org.aguzman.springcloud.msvc.auth.models.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Service
public class UsuarioServices implements UserDetailsService {

    //recuerda haber generado la condgiruacion
    @Autowired
    private WebClient.Builder client;

    private Logger log = LoggerFactory.getLogger(UsuarioServices.class);

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        try{
            Usuario usuario = client
                    .build()
                    .get()
                    //se le pone el nombre de la propeidad d del microservicio a registrar
                    .uri("http://msvc-usuarios/login", uri-> uri.queryParam("email", email).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve() //obtendremos un objeto reactivo
                    .bodyToMono(Usuario.class).block();

            log.info("Usuario Email: " + usuario.getEmail());

            // de manera automatica el password de desencripta y se genera el usuario
            return new User(email, usuario.getPassword(), true, true, true, true,
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

        }catch (RuntimeException  e){
            String error = "Error en el login, no existe el usuario " + email + " en el sistema";
            throw new UsernameNotFoundException(error);
        }
    }
}
