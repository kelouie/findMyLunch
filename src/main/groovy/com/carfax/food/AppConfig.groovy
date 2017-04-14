package com.carfax.food

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class AppConfig {

    @Bean(name = 'restTemplate')
    RestTemplate restTemplate() {
        new RestTemplate()
    }
}
