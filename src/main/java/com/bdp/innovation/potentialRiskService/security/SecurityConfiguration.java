package com.bdp.innovation.potentialRiskService.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/*
*
* Configuration class for security checks
* 
*/
@EnableMethodSecurity
@Configuration
@EnableWebSecurity
public class SecurityConfiguration  {

	@Autowired
	private AwsCognitoJwtAuthFilter awsCognitoJwtAuthenticationFilter;

	// @Value("${security.enable-csrf}")
	private boolean csrfEnabled = false;

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		HttpSecurity security = null;
//		if (!csrfEnabled) {
//			security = http.csrf().disable();
//			security.cors().configurationSource(corsConfigurationSource()).and()
//					.addFilterBefore(awsCognitoJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//					.authorizeRequests().antMatchers("**/health").permitAll().antMatchers("/riskmonitor-api/api/**")
//					.authenticated();
//		}
//
//	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.cors(cors -> cors.configurationSource(corsConfigurationSource()))
		.csrf(csrf -> csrf.disable())
				.addFilterBefore(awsCognitoJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.authorizeHttpRequests(auth -> auth.requestMatchers("/riskmonitor-api/api/**").authenticated()
				.requestMatchers("/riskmonitor-api/health").permitAll()
				.requestMatchers("/").permitAll())
			    .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			    		.authenticationProvider(new PreAuthenticatedAuthenticationProvider());
		return http.build();
		}

	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		List<String> allowOrigins = Arrays.asList("*");
		configuration.setAllowedOrigins(allowOrigins);
		configuration.setAllowedMethods(Collections.singletonList("*"));
		configuration.setAllowedHeaders(Collections.singletonList("*"));
		configuration.setAllowCredentials(false);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}