package com.abidsaleh.employeemanagement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import com.google.common.base.Predicate;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket swaggerConfiguration() {
        //return a prepared Docket Instance
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("public-api")
                .apiInfo(apiInfo())
                .select()
                .paths(postPaths())
                .apis(RequestHandlerSelectors.basePackage("com.abidsaleh.employeemanagement"))
                .build();
    }

    private Predicate<String> postPaths() {
        return regex("/employee.*");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Employee Management API")
                .description("EmployeeManagement API reference for developers")
                .contact(new Contact("Abid Saleh", "https://www.linkedin.com/in/abid-saleh-140360153/","eng.abidsaleh@gmail.com"))
                .version("1.0.0")
                .license("Free to use")
                .build();
    }
}
