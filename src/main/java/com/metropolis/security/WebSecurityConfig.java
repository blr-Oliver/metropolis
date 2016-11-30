package com.metropolis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

import com.metropolis.repository.AccountRepository;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private AccountRepository accountRepo;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				// Configures form login
				.formLogin()//
				.loginPage("/login")//
				.loginProcessingUrl("/login/auth")//
				.failureUrl("/login?error")//
				// Configures the logout function
				.and().logout()//
				.deleteCookies("JSESSIONID")//
				.logoutUrl("/logout")//
				.logoutSuccessUrl("/login?logout")//
				// Configures url based authorization
				.and().authorizeRequests()
				// Anyone can access the urls
				.antMatchers("/auth/**", "/login", "/user/register", "/css/**", "/js/**").permitAll()
				// The rest of the our application is protected.
				.antMatchers("/**").permitAll()//
				;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService());
	}

	@Override
	@Bean
	public RepositoryUserDetailsService userDetailsService() {
		return new RepositoryUserDetailsService(accountRepo);
	}
}
