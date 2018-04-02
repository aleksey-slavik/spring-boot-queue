package com.globallogic.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class ApplicationConfig {

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext context) {
        return args -> {
            System.out.println("Spring inspection:");
            String[] beans = context.getBeanDefinitionNames();
            Arrays.sort(beans);

            for (String bean : beans) {
                System.out.println(bean);
            }
        };
    }
}
