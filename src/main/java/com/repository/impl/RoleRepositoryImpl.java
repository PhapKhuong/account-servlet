package com.repository.impl;

import com.bean.Role;
import com.database.DBConnection;
import com.database.MyQuery;
import com.repository.itf.RoleRepository;

import java.sql.*;

public class RoleRepositoryImpl implements RoleRepository {
    @Override
    public Role search(int id) {
        Connection connection = DBConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Role role = null;

        if (connection != null) {
            try {
                statement = connection.prepareStatement(MyQuery.SEARCH_ROLE_BY_ID);
                statement.setInt(1, id);
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int roleId = resultSet.getInt("roleId");
                    String roleName = resultSet.getString("roleName");
                    role = new Role(roleId, roleName);
                    break;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    statement.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                DBConnection.close();
            }
        }
        return role;
    }
}
