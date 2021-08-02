//package com.amiconsult.topsecretschnupperdevchallenge.swaggerconfig;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//@Configuration
//class SwaggerConfiguration {
//    @Bean
//    public Docket produceApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .host("localhost:8080")
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build();
//    }
//}