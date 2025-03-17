package org.jers.parameters.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.jers.parameters.utils.SystemConstants.PARAMETERS_PATH;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(HttpMethod.GET, PARAMETERS_PATH + "/**").permitAll()
                .requestMatchers(HttpMethod.POST, PARAMETERS_PATH + "/**").authenticated()
                .requestMatchers(HttpMethod.PUT, PARAMETERS_PATH + "/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, PARAMETERS_PATH + "/**").authenticated()
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}