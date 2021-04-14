package com.salesmicroservices.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(getApiInfo())
            .globalOperationParameters(getRequiredParameters())
            .select()
            .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
            .paths(PathSelectors.any())
            .build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
            .title("Auth API")
            .description("Auth API for Sales Microservices Project")
            .version("1.0.0")
            .licenseUrl("http://localhost:8080/auth")
            .build();
    }

    private List<Parameter> getRequiredParameters() {
        return Collections.singletonList(new ParameterBuilder()
            .name("Authorization")
            .modelRef(new ModelRef("string"))
            .parameterType("header")
            .required(false)
            .build()
        );
    }
}
