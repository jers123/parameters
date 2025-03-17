package org.jers.parameters.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.jers.parameters.utils.SystemConstants.LOCAL_ORIGIN_PATH;
import static org.jers.parameters.utils.SystemConstants.LOCAL_ORIGIN_ANGULAR_PATH;
import static org.jers.parameters.utils.SystemConstants.LOCAL_ORIGIN_PATH2;
import static org.jers.parameters.utils.SystemConstants.PUBLIC_ORIGIN_ANGULAR__PATH;
import static org.jers.parameters.utils.SystemConstants.PUBLIC_ORIGIN_PATH;
import static org.jers.parameters.utils.SystemConstants.PUBLIC_ORIGIN_PATH2;

@Configuration
public class ParametersConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(LOCAL_ORIGIN_PATH,
                        PUBLIC_ORIGIN_PATH,
                        LOCAL_ORIGIN_PATH2,
                        PUBLIC_ORIGIN_PATH2,
                        LOCAL_ORIGIN_ANGULAR_PATH,
                        PUBLIC_ORIGIN_ANGULAR__PATH
                        //,"*"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600)
        ;
    }
}