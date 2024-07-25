package com.bdp.innovation.potentialRiskService.dao;

import java.util.List;

public interface IUserManagementServiceDao {

	List<String> getCoreUserAuthRoles(String email);

}
