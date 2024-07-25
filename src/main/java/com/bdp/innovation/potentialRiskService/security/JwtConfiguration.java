package com.bdp.innovation.potentialRiskService.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.bdp.innovation.potentialRiskService.awsConfig.AwsCognitoProperty;


/*
*
* Component class which are making urls
* 
*/

@Component
public class JwtConfiguration {

	@Autowired
	@Qualifier("awsCognitoProperty")
	AwsCognitoProperty awsCognitoProperty;

	private String jwkUrl;

	private int connectionTimeout = 2000;

	private int readTimeout = 2000;

	private String httpHeader = "Authorization";

	public String getJwkUrl() {
		return this.jwkUrl != null && !this.jwkUrl.isEmpty() ? this.jwkUrl
				: String.format(awsCognitoProperty.getAwsCognitoJwkUri(), awsCognitoProperty.getAwsCognitoRegion(),
						awsCognitoProperty.getAwsCognitoUserPoolId());
	}

	public String getCognitoIdentityPoolUrl() {
		return String.format(awsCognitoProperty.getAwsCognitoIssuerUri(), awsCognitoProperty.getAwsCognitoRegion(),
				awsCognitoProperty.getAwsCognitoUserPoolId());
	}

	public void setJwkUrl(String jwkUrl) {
		this.jwkUrl = jwkUrl;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public String getHttpHeader() {
		return httpHeader;
	}

	public void setHttpHeader(String httpHeader) {
		this.httpHeader = httpHeader;
	}
}