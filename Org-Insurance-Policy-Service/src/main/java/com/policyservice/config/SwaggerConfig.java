package com.policyservice.config;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class SwaggerConfig {

	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("Java Techie").apiInfo(apiInfo()).select()
				.paths(or(regex("/public.*"),regex("/api.*"))).build();
//				.paths(regex("/public.*")).build();
	}

private ApiInfo apiInfo() {
	return new ApiInfoBuilder().title("f ")
			.description("f")
			.termsOfServiceUrl("v")
			.license("J")
			.licenseUrl("").version("1.0").build();
}
}
