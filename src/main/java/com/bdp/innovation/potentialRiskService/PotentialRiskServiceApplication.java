package com.bdp.innovation.potentialRiskService;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.bdp.innovation.potentialRiskService.security.JwtConfiguration;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.util.DefaultResourceRetriever;
import com.nimbusds.jose.util.ResourceRetriever;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;

@EnableCaching
@EnableWebMvc
@SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class })
@EnableScheduling
//@ComponentScan(basePackages = { "com.bdp.innovation.potentialRiskService", "com.bdp.innovations.secondary" })
public class PotentialRiskServiceApplication {
	@Autowired
	private JwtConfiguration jwtConfiguration;

	public static void main(String[] args) {
		SpringApplication.run(PotentialRiskServiceApplication.class, args);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public ConfigurableJWTProcessor configurableJWTProcessor() throws MalformedURLException {
		ResourceRetriever resourceRetriever = new DefaultResourceRetriever(2000, 2000);
		URL jwkURL = new URL(jwtConfiguration.getJwkUrl());
		JWKSource keySource = new RemoteJWKSet(jwkURL, resourceRetriever);
		ConfigurableJWTProcessor jwtProcessor = new DefaultJWTProcessor();
		JWSKeySelector keySelector = new JWSVerificationKeySelector(JWSAlgorithm.RS256, keySource);
		jwtProcessor.setJWSKeySelector(keySelector);
		return jwtProcessor;
	}

	@Bean(name = "threadPoolTask")
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(25);
		executor.setThreadNamePrefix("ThreadPoolTaskExecutor");
		return executor;
	}

}
