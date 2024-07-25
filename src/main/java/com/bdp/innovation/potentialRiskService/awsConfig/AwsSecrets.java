package com.bdp.innovation.potentialRiskService.awsConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AwsSecrets {

	private String username;
	private String password;
	private String host;
	private String engine;
	private String port;
	private String dbname;
	private String awsCognitoIssuerUri;
	private String awsCognitoUserPoolId;
	private String awsCognitoRegion;
	private String awsCognitoJwkUri;
	private String awsCognitoUserNameField;
	private String awsCognitoUserIdField;
	private String awsCognitoNameField;
	private String awsCognitoEmailField;
	private String swaggerDocumentation;
	private String smApiUserKey;
	private String mailhost;
	private String noReplymailuser;
	private String noReplymailpass;
	private String rmrsaPrivateKey;
	private String rmrsaCipherInstanceKey;
	private String snowflexPrivateKey;
	private String snowflexCipherInstanceKey;
	private String snowflexAccount;
	private String snowflexUser;
	private String rmDbName;
	private String qlikWebIntegrationID;
	private String qlikKeyID;
	private String qlikIssuer;
	private String qlikAud;
	private String qlikPk;
	private String bucketName;
	private String folderName;
	
	// replica property
	private String rrHost;
	private String rrUsername;
	private String rrPassword;

}