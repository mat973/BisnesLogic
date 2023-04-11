package com.example.bisneslogic.config;

import com.example.bisneslogic.security.AuthProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
public class SecurityConfig{

    private AuthProviderImpl authProvider;

    @Autowired
    public SecurityConfig(AuthProviderImpl authProvider) {
        this.authProvider = authProvider;
    }
    @Bean
    public void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(authProvider);
    }


    //настраивает аунтификацию

}
