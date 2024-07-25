package com.bdp.innovation.potentialRiskService.awsConfig;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ObjectUtils;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.google.gson.Gson;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "com.bdp.innovations.riskmonitor.repository",
//entityManagerFactoryRef = "primaryEntityManagerFactory", transactionManagerRef = "primaryTransactionManager")
public class JpaConfiguration {

	@Value("${cloud.aws.secret-name}")
	private String secretName;

	@Value("${cloud.aws.region.static}")
	private String region;
	
	@Value("${spring.datasource.hikari.connection-timeout}")
	private int connectionTimeout;
	@Value("${spring.datasource.hikari.minimum-idle}")
	private int minimumIdle;
	@Value("${spring.datasource.hikari.maximum-poolsize}")
	private int maximumPoolsize;
	
	private Gson gson = new Gson();
	
	String secret;

	@Bean
	public DataSource getDataSource() {
		AwsSecrets secrets = getSecret();
		HikariConfig config = new HikariConfig();
		config.setDriverClassName("com.amazonaws.secretsmanager.sql.AWSSecretsManagerMySQLDriver");
		config.setJdbcUrl("jdbc-secretsmanager:" + secrets.getEngine() + "://" + secrets.getHost() + "/"+secrets.getRmDbName());
		config.setUsername(secretName);
		config.setConnectionTimeout(connectionTimeout);
		config.setMinimumIdle(minimumIdle);
		config.setMaximumPoolSize(maximumPoolsize);
		return new HikariDataSource(config);
	}

	private AwsSecrets getSecret() {		
		if(secret !=null){
			return gson.fromJson(secret, AwsSecrets.class);
		}
		AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard().withRegion(region)
			//	.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
				.build();

		GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(secretName);
		GetSecretValueResult getSecretValueResult = null;

		try {
			getSecretValueResult = client.getSecretValue(getSecretValueRequest);
		} catch (Exception e) {
			log.error("Exception occuring while getSecretValue from AwsSecrets : {}", e);
		}
		if (!ObjectUtils.isEmpty(getSecretValueResult)
				&& !ObjectUtils.isEmpty(getSecretValueResult.getSecretString())) {
			secret = getSecretValueResult.getSecretString();
			return gson.fromJson(secret, AwsSecrets.class);
		}

		return null;
	}

	@Bean("awsCognitoProperty")
	public AwsCognitoProperty getCognitoProperties() {
		AwsSecrets secrets = getSecret();
		AwsCognitoProperty cognitoProperty = null;
		if (!ObjectUtils.isEmpty(secrets)) {
			cognitoProperty = new AwsCognitoProperty();
			cognitoProperty.setAwsCognitoIssuerUri(secrets.getAwsCognitoIssuerUri());
			cognitoProperty.setAwsCognitoUserPoolId(secrets.getAwsCognitoUserPoolId());
			cognitoProperty.setAwsCognitoRegion(secrets.getAwsCognitoRegion());
			cognitoProperty.setAwsCognitoJwkUri(secrets.getAwsCognitoJwkUri());
			cognitoProperty.setAwsCognitoUserNameField(secrets.getAwsCognitoUserNameField());
			cognitoProperty.setAwsCognitoUserIdField(secrets.getAwsCognitoUserIdField());
			cognitoProperty.setAwsCognitoNameField(secrets.getAwsCognitoNameField());
			cognitoProperty.setAwsCognitoEmailField(secrets.getAwsCognitoEmailField());
			cognitoProperty.setSwaggerDocumentation(secrets.getSwaggerDocumentation());
			cognitoProperty.setSmApiUserKey(secrets.getSmApiUserKey());
			
			cognitoProperty.setMailhost(secrets.getMailhost());
			cognitoProperty.setNoReplymailuser(secrets.getNoReplymailuser());
			cognitoProperty.setNoReplymailpass(secrets.getNoReplymailpass());
			cognitoProperty.setRmrsaPrivateKey(secrets.getRmrsaPrivateKey());
			cognitoProperty.setRmrsaCipherInstanceKey(secrets.getRmrsaCipherInstanceKey());
			cognitoProperty.setSnowflexPrivateKey(secrets.getSnowflexPrivateKey());
			cognitoProperty.setSnowflexCipherInstanceKey(secrets.getSnowflexCipherInstanceKey());
			cognitoProperty.setSnowflexAccount(secrets.getSnowflexAccount());
			cognitoProperty.setSnowflexUser(secrets.getSnowflexUser());
			cognitoProperty.setRmDbName(secrets.getRmDbName());
			cognitoProperty.setEngine(secrets.getEngine());
			cognitoProperty.setPort(secrets.getPort());
			
			// QLIK CONFIGURATION
			cognitoProperty.setQlikWebIntegrationID(secrets.getQlikWebIntegrationID());
			cognitoProperty.setQlikKeyID(secrets.getQlikKeyID());
			cognitoProperty.setQlikIssuer(secrets.getQlikIssuer());
			cognitoProperty.setQlikAud(secrets.getQlikAud());
			cognitoProperty.setQlikPk(secrets.getQlikPk());
			
			//S3 Bucket Configuration
			cognitoProperty.setBucketName(secrets.getBucketName());
			cognitoProperty.setFolderName(secrets.getFolderName());
			
			// REPLICA PROPERTY
			// FOR PROD ENVIRONMENT
			if (!ObjectUtils.isEmpty(secrets.getRrHost())) {
				cognitoProperty.setRrHost(secrets.getRrHost());
				cognitoProperty.setRrUsername(secrets.getRrUsername());
				cognitoProperty.setRrPassword(secrets.getRrPassword());
			}
		}
		return cognitoProperty;
	}

	@Bean("swaggerDocumentSecrets")
	public SwaggerDocumentSecrets getSwaggerDocumentSecrets() {
		SwaggerDocumentSecrets swaggerDocumentSecrets = null;
		AwsSecrets secrets = getSecret();
		if (!ObjectUtils.isEmpty(secrets)) {
			swaggerDocumentSecrets = new SwaggerDocumentSecrets();
			swaggerDocumentSecrets.setSwaggerDocumentation(secrets.getSwaggerDocumentation());
		}
		return swaggerDocumentSecrets;
	}
	
	// ===========================================================


//    @Primary
//    @Bean(name = "primaryDataSourceProperties")
//    DataSourceProperties primaryDataSourceProperties() {
//		return new DataSourceProperties();
//	}
//
//    @Primary
//    @Bean(name = "primaryDataSource")
//    @ConfigurationProperties("spring.datasource-primary.configuration")
//    DataSource primaryDataSource(
//             @Qualifier("primaryDataSourceProperties") DataSourceProperties primaryDataSourceProperties) {
//		AwsSecrets secrets = getSecret();
//		HikariConfig config = new HikariConfig();
//		config.setDriverClassName("com.mysql.cj.jdbc.Driver");
//		config.setJdbcUrl("jdbc:" + secrets.getEngine() + "://" + secrets.getHost() + ":" + secrets.getPort() + "/" + secrets.getRmDbName());
//		config.setUsername(secrets.getUsername());
//		config.setPassword(secrets.getPassword());
//		config.setConnectionTimeout(connectionTimeout);
//		config.setMinimumIdle(minimumIdle);
//		config.setMaximumPoolSize(maximumPoolsize);
//		return new HikariDataSource(config);
//	}
//
//    @Primary
//    @Bean(name = "primaryEntityManagerFactory")
//    LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(
//             EntityManagerFactoryBuilder primaryEntityManagerFactoryBuilder,
//             @Qualifier("primaryDataSource") DataSource primaryDataSource) {
//    	Map<String, String> primaryJpaProperties = new HashMap<>();
//		primaryJpaProperties.put("spring.jpa.properties.hibernate.proc.param_null_passing", "true");
//		primaryJpaProperties.put("hibernate.proc.param_null_passing", "true");
//		//primaryJpaProperties.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
//		//primaryJpaProperties.put("hibernate.physical_naming_strategy", CamelCaseToUnderscoresNamingStrategy.class.getName());
//		return primaryEntityManagerFactoryBuilder.dataSource(primaryDataSource)
//				.packages("com.bdp.innovations.riskmonitor")
//				.persistenceUnit("primaryDataSource").build();
//	}
//
//    @Primary
//    @Bean(name = "primaryTransactionManager")
//    PlatformTransactionManager primaryTransactionManager(
//             @Qualifier("primaryEntityManagerFactory") EntityManagerFactory primaryEntityManagerFactory) {
//		return new JpaTransactionManager(primaryEntityManagerFactory);
//	}


}