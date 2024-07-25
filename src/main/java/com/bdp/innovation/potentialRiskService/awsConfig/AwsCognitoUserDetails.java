package com.bdp.innovation.potentialRiskService.awsConfig;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class AwsCognitoUserDetails {
	private String username;
	private String userId;
	private String name;
	private String email;
	private String timeZone;
	private Map<String,List<String>> roles;
	private List<String> cognitoGroups;
	private List<Integer> companyList;
	
}
