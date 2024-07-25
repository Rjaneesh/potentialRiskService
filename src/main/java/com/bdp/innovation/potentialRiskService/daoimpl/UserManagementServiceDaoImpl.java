package com.bdp.innovation.potentialRiskService.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bdp.innovation.potentialRiskService.awsConfig.AwsCognitoProperty;
import com.bdp.innovation.potentialRiskService.dao.IUserManagementServiceDao;
import com.bdp.innovation.potentialRiskService.dto.UserRole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
public class UserManagementServiceDaoImpl implements IUserManagementServiceDao {
	@Value("${cloud.aws.secret-name}")
	private String secretName;

	@Value("${cloud.aws.region.static}")
	private String region;

	@Autowired
	@Qualifier("awsCognitoProperty")
	AwsCognitoProperty awsCognitoProperty;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<String> getCoreUserAuthRoles(String email) {
		List<String> roles = new ArrayList<>();
		final String pattern = "Risk Monitor";
		String query = "SELECT FFU.id AS featureId, FFU.features AS featureName FROM `TnT-Portal`.features_associated_to_user FAU "
				+ "JOIN `TnT-Portal`.features_for_users FFU ON FAU.features_id=FFU.ID "
				+ "WHERE FFU.features like('Risk Monitor%') and FAU.USER_ID=?";
		List<UserRole> userRoles = createNativeQuery(UserRole.class, query).setParameter(1, email).getResultList();
		for (UserRole userRole : userRoles) {
			String roleName = userRole.getFeatureName().replaceFirst(pattern, "").replace(" ", "_");
			roleName = "role".concat(roleName).toUpperCase();
			roles.add(roleName);
		}
		return roles;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	private Query createNativeQuery(Class<?> className, String query) {
		if (className == null) {
			return entityManager.createNativeQuery(query).unwrap(NativeQuery.class);
		} else {
			return entityManager.createNativeQuery(query).unwrap(NativeQuery.class)
					.setResultTransformer(Transformers.aliasToBean(className));
		}
	}
}
