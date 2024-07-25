package com.bdp.innovation.potentialRiskService.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.bdp.innovation.potentialRiskService.awsConfig.AwsCognitoProperty;
import com.bdp.innovation.potentialRiskService.awsConfig.AwsCognitoUserDetails;
import com.bdp.innovation.potentialRiskService.dao.IUserManagementServiceDao;
import com.bdp.innovation.potentialRiskService.exceptionHandler.BaseException;
import com.bdp.innovation.potentialRiskService.exceptionHandler.ExpiredJwtException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;

import jakarta.servlet.http.HttpServletRequest;

/*
*
* Component class which are validating and authenticating IdToken
* 
*/
@Component
public class AwsCognitoIdTokenProcessor {

	@Autowired
	private JwtConfiguration jwtConfiguration;

	@Autowired
	@Qualifier("awsCognitoProperty")
	AwsCognitoProperty awsCognitoProperty;

	@Autowired
	@Qualifier("awsCognitoUserDetails")
	private AwsCognitoUserDetails cognitoUserDetails;

	@Autowired
	private IUserManagementServiceDao userManagementServiceDao;

	@Autowired
	private ConfigurableJWTProcessor<?> configurableJWTProcessor;

	// Authenticate token from header, verify it and get user details
	@SuppressWarnings("unchecked")
	public Authentication authenticate(HttpServletRequest request) throws Exception, BaseException {
		List<String> groups = null;
		String email = null;
		String idToken = request.getHeader(jwtConfiguration.getHttpHeader());
		email = request.getParameter("email");
		try {
			if(cognitoUserDetails.getEmail() ==null && email!=null) {
				cognitoUserDetails.setEmail(email);
			}
			if (!StringUtils.isEmpty(idToken)) {
				JWTClaimsSet claims = configurableJWTProcessor.process(this.getBearerToken(idToken), null);
				if (null != claims) {
					validateIssuer(claims);
					verifyIfIdToken(claims);

					email = claims.getClaims().get(awsCognitoProperty.getAwsCognitoEmailField()).toString();

					// Set the username to access it from controller
					if (cognitoUserDetails.getEmail() != null && !cognitoUserDetails.getEmail().equals(email)) {
						cognitoUserDetails.setCognitoGroups(null);
						cognitoUserDetails.setRoles(null);
						cognitoUserDetails.setCompanyList(null);
					}
					cognitoUserDetails.setEmail(email);
					
					String timeZone = (String)claims.getClaims().get("custom:timezone");
					cognitoUserDetails.setTimeZone(timeZone); 

					groups = (List<String>) claims.getClaims().get("cognito:groups");
					cognitoUserDetails
							.setName(claims.getClaims().get(awsCognitoProperty.getAwsCognitoNameField()).toString());
					cognitoUserDetails.setUserId(
							claims.getClaims().get(awsCognitoProperty.getAwsCognitoUserIdField()).toString());
					cognitoUserDetails.setUsername(
							claims.getClaims().get(awsCognitoProperty.getAwsCognitoUserNameField()).toString());
					
				}

				if (email != null) {
					if (ObjectUtils.isEmpty(cognitoUserDetails.getCognitoGroups()))
						cognitoUserDetails.setCognitoGroups(groups);
					List<GrantedAuthority> grantedAuthorities = setUserFeatures(email,
							cognitoUserDetails.getCognitoGroups());
					User user = new User(email, "", grantedAuthorities);
					return new UsernamePasswordAuthenticationToken(user, claims, grantedAuthorities);
				}
			}
			
		} catch (Exception e) {
			throw new ExpiredJwtException("Cognito Token is Expired.");
		}
		return null;
	}

	private List<GrantedAuthority> setUserFeatures(String email, List<String> groups) {
		List<GrantedAuthority> updatedAuthorities = new ArrayList<>();
		List<String> coreFeatureList = null;
		if (cognitoUserDetails.getRoles() == null || cognitoUserDetails.getRoles().isEmpty()
				|| cognitoUserDetails.getRoles().get(email) == null) {
			coreFeatureList = userManagementServiceDao.getCoreUserAuthRoles(email);
			cognitoUserDetails.setRoles(new HashMap<>());
			cognitoUserDetails.getRoles().put(email, coreFeatureList);
		}else {
			coreFeatureList = cognitoUserDetails.getRoles().get(email);
		}
		SimpleGrantedAuthority authority = null;
		for (String coreFeaturesID : coreFeatureList) {
			authority = new SimpleGrantedAuthority(coreFeaturesID);
			updatedAuthorities.add(authority);
		}
		return updatedAuthorities;
	}

	@Deprecated
	private List<GrantedAuthority> setCoreUserFeatures(String email, List<String> groups) {

		List<GrantedAuthority> updatedAuthorities = new ArrayList<>();
		List<String> coreFeatureList = null;
		if (cognitoUserDetails.getRoles() == null || cognitoUserDetails.getRoles().isEmpty()
				|| cognitoUserDetails.getRoles().get(email) == null) {
			coreFeatureList = new ArrayList<>();
			// coreFeatureList = coreFeaturesDao.getCoreUserAuthRoles(email);
			if (groups.contains("BDP_USER")) {
				coreFeatureList.add("ROLE_ADMIN");
				/** for yard user type global role access */
				coreFeatureList.add("ROLE_YARD_USER");
				if (coreFeatureList.contains("ROLE_REQUESTS")) {
					coreFeatureList.add("ROLE_BDP_HOME");
				}
			} else if (coreFeatureList.contains("ROLE_REQUESTS")) {
				coreFeatureList.add("ROLE_DRAY_HOME");
			}
			coreFeatureList.add(groups.contains("BDP_USER") ? "ROLE_BDP_USER" : "ROLE_DRAY_USER");
			if (coreFeatureList.contains("ROLE_MANAGESERVICE")) {
				coreFeatureList.add("ROLE_DRAY_HOME");
				coreFeatureList.add("ROLE_DRAY_USER");
			}
			Map<String, List<String>> role = new HashMap<>();
			role.put(email, coreFeatureList);
			cognitoUserDetails.setRoles(role);
		} else {
			coreFeatureList = cognitoUserDetails.getRoles().get(email);
		}
		SimpleGrantedAuthority authority = null;
		for (String coreFeaturesID : coreFeatureList) {
			authority = new SimpleGrantedAuthority(coreFeaturesID);
			updatedAuthorities.add(authority);
		}
		return updatedAuthorities;

	}

	// get verify claim fron conginto
	private void verifyIfIdToken(JWTClaimsSet claims) throws BaseException {
		if (!claims.getIssuer().equals(this.jwtConfiguration.getCognitoIdentityPoolUrl())) {
			throw new BaseException("JWT Token is not an ID Token");
		}
	}

	// validate issueruri from claim
	private void validateIssuer(JWTClaimsSet claims) throws BaseException {
		if (!claims.getIssuer().equals(this.jwtConfiguration.getCognitoIdentityPoolUrl())) {
			throw new BaseException(String.format("Issuer %s does not match cognito idp %s", claims.getIssuer(),
					this.jwtConfiguration.getCognitoIdentityPoolUrl()));
		}
	}

	// get token and substring Bearer
	private String getBearerToken(String token) {
		return token.startsWith("Bearer ") ? token.substring("Bearer ".length()) : token;
	}
}