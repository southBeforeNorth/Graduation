package com.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class AuditorConfig {

    @Bean
    public AuditorAware<String> auditorAware(){
        return this::getRequester;
    }

    private Optional<String> getRequester(){
        return Optional.of("王德略");
    }
}

