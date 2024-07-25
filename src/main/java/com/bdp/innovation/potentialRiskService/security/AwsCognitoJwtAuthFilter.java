package com.bdp.innovation.potentialRiskService.security;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.bdp.innovation.potentialRiskService.exceptionHandler.ExpiredJwtException;
import com.nimbusds.jwt.proc.BadJWTException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@SuppressWarnings("serial")
@Component
public class AwsCognitoJwtAuthFilter extends GenericFilter {

	private static final Log logger = LogFactory.getLog(AwsCognitoJwtAuthFilter.class);

	private AwsCognitoIdTokenProcessor cognitoIdTokenProcessor;

	public AwsCognitoJwtAuthFilter(AwsCognitoIdTokenProcessor cognitoIdTokenProcessor) {
		this.cognitoIdTokenProcessor = cognitoIdTokenProcessor;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse res, FilterChain filterChain)
			throws IOException, ServletException {
		Authentication authentication;

		try {
			authentication = this.cognitoIdTokenProcessor.authenticate((HttpServletRequest) request);
			if (authentication != null) {
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (BadJWTException var6) {
			logger.error("Cognito ID Token processing error. " + var6.getMessage());
			SecurityContextHolder.clearContext();
			throw new ExpiredJwtException("Cognito ID Token processing error. " + var6.getMessage());
		} catch (Exception var6) {
			logger.error("Cognito ID Token processing error", var6);
			SecurityContextHolder.clearContext();
		}

		filterChain.doFilter(request, res);
	}
}