package org.aguzman.springcloud.msvc.auth;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WenClientConfig {
//Metodo que devuelve componente, mas facuil de hacer con webflux
    @LoadBalanced
    @Bean
    WebClient webClient(){
        return WebClient.builder().build();
    }



}
