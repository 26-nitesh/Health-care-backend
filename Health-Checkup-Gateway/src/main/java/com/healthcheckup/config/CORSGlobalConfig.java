package com.healthcheckup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

@Configuration
//@CrossOrigin("*")
public class CORSGlobalConfig {

	  private static final String ALLOWED_ORIGIN = "http://localhost:3000"; // Update with your desired origin

	    @Bean
	    public CorsWebFilter corsWebFilter() {
	        CorsConfiguration corsConfig = new CorsConfiguration();
	        corsConfig.addAllowedOrigin(ALLOWED_ORIGIN);
	        corsConfig.addAllowedMethod("*");
	        corsConfig.addAllowedHeader("*");
	        corsConfig.setMaxAge(3600L);

	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
	        source.registerCorsConfiguration("/**", corsConfig);

	        return new CorsWebFilter(source);
	    }
}


