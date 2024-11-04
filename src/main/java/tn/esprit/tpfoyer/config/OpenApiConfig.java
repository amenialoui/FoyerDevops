package tn.esprit.tpfoyer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.GroupedOpenApi;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi universiteApi() {
        return GroupedOpenApi.builder()
                .group("universite")
                .pathsToMatch("/universite/**")
                .build();
    }
}
