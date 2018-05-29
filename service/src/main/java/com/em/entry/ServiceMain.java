package com.em.entry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by guchong on 5/22/2018.
 */
@EntityScan(value = "com.em.model")
@ComponentScan(basePackages = {"com.em.entry","com.em.controller", "com.em.service"}, excludeFilters = {})
@EnableJpaRepositories(basePackages = {"com.em.service"})
@SpringBootApplication
public class ServiceMain {
    @Value("${service.port:8080}")
    private int port;

    public static void main(String[] args) {
        new SpringApplicationBuilder(ServiceMain.class).run(args);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer pspc() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return (container -> {
            container.setPort(port);
        });
    }
}
