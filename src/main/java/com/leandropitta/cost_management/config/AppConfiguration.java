package com.leandropitta.cost_management.config;

import com.leandropitta.cost_management.dto.response.AuthResponseDto;
import com.leandropitta.cost_management.entity.User;
import com.leandropitta.cost_management.interceptor.OriginCheckInterceptor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfiguration implements WebMvcConfigurer {

    private OriginCheckInterceptor originCheckInterceptor;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<User, AuthResponseDto>() {
            @Override
            protected void configure() {
                map().setBackgroundColor(source.getBackgroundColor().getDescription());
                map().setTitleColor(source.getTitleColor().getDescription());
                map().setFontFamily(source.getFontFamily().getDescription());
            }
        });
        return modelMapper;
    }

    // Coment this in development
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(originCheckInterceptor);
//    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://livia.leandropitta.com.br", "https://15.228.14.114", "http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}