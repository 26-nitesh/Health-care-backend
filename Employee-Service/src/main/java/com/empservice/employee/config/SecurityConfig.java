//package com.empservice.employee.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import com.empservice.employee.service.CustomUserDetailService;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Autowired CustomUserDetailService userDetailsService;
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//		auth.userDetailsService(userDetailsService);
//	}
//	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return NoOpPasswordEncoder.getInstance();
//	}
//	
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//    	http
//        .authorizeHttpRequests()
//            .antMatchers("/public/**","/swagger-ui/**","v3/**","/v3/api-docs/**").permitAll() // make all URLs starting with "/public" public
//            .antMatchers("/api/employee/**").hasRole("ORGANISATION")
//            .anyRequest().authenticated()
//            .and().httpBasic().and()
//        .formLogin()
//            .permitAll() // allow unauthenticated access to the login page
//            .and()
//        .logout()
//            .permitAll().and().csrf().disable(); // allow unauthenticated access to the logout URL
//}
//    
//    
//	
//	
//}
