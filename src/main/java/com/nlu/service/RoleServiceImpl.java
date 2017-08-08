package com.nlu.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nlu.dao.ManagerRole;
import com.nlu.dao.Role;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	@Autowired
	SessionFactory sf;

	@Override
	public ManagerRole getListRole(int maGv) throws SQLException {
		SessionImpl session = (SessionImpl) sf.getCurrentSession();
		Connection connection = session.connection();
		CallableStatement callableStatement = connection.prepareCall("SELECT * FROM F_GETLISTROLE(?)");
		callableStatement.setInt(1, maGv);
		ResultSet resultSet = callableStatement.executeQuery();
		List<Role> role = new ArrayList<>();
		while (resultSet.next()) {
			role.add(new Role(resultSet.getInt("ROLE_NAME"), resultSet.getString("DESCRIBE")));
		}

		return new ManagerRole(role);
	}
}
