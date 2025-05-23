package com.driving_tester.backend.accounts.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // @Override
    // public void addCorsMappings(CorsRegistry registry) {
    //     registry.addMapping("/**")
    //             .allowedOrigins("*")
    //             .allowedMethods("GET", "POST", "PUT", "DELETE")
    //             .allowedHeaders("*")
    //             .exposedHeaders("Authorization")
    //             .allowCredentials(true);
    // }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}