package com.nlu.service;

import java.sql.SQLException;

import com.nlu.dao.ManagerRole;

public interface RoleService {
	public ManagerRole getListRole(int maGv) throws SQLException;
}
