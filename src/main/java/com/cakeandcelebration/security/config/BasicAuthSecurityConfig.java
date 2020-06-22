/*
 * package com.cakeandcelebration.security.config;
 * 
 * import org.springframework.context.annotation.Configuration; import
 * org.springframework.core.annotation.Order; import
 * org.springframework.security.config.annotation.authentication.builders.
 * AuthenticationManagerBuilder; import
 * org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import org.springframework.security.config.annotation.web.configuration.
 * WebSecurityConfigurerAdapter;
 * 
 * @Configuration
 * 
 * @Order(1) class BasicAuthSecurityConfig extends WebSecurityConfigurerAdapter
 * {
 * 
 * @Override protected void configure(HttpSecurity http) throws Exception {
 * http.antMatcher("/authenticate/**") .authorizeRequests()
 * .anyRequest().hasRole("BASIC_USER") .and() .httpBasic() .and()
 * .csrf().disable(); }
 * 
 * 
 * 
 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
 * Exception { auth.inMemoryAuthentication() .withUser("basic_user")
 * .password("test") .roles("BASIC_USER"); } }
 */