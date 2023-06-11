package com.repository.impl;

import com.bean.Role;
import com.bean.User;
import com.database.DBConnection;
import com.database.MyQuery;
import com.repository.itf.UserRepository;
import com.util.MyUtil;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public List<User> display() {
        Connection connection = DBConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<User> userList = new ArrayList<>();
        Map<Integer, User> userMap = new HashMap<>();

        if (connection != null) {
            try {
                statement = connection.prepareStatement(MyQuery.SELECT_ALL_USER);
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int userId = resultSet.getInt("userId");
                    String fullName = resultSet.getString("fullName");
                    String code = resultSet.getString("code");

                    Date d1 = resultSet.getDate("birthday");
                    LocalDate birthday = MyUtil.convertToLocalDateViaSqlDate(d1);
                    Date d2 = resultSet.getDate("initTime");
                    LocalDateTime initTime = MyUtil.convertToLocalDateTimeViaSqlTimestamp(d2);

                    int roleId = resultSet.getInt("roleId");
                    String roleName = resultSet.getString("roleName");
                    Role role = new Role(roleId, roleName);

                    if (userMap.containsKey(userId)) {
                        userMap.get(userId).getRoles().add(role);
                        userMap.replace(userId, userMap.get(userId));
                    } else {
                        List<Role> roles = new ArrayList<>();
                        roles.add(role);
                        User user = new User(userId, fullName, code, birthday, initTime, roles);
                        userMap.put(userId, user);
                    }
                }
                for (User value : userMap.values()) {
                    userList.add(value);
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
        return userList;
    }

    @Override
    public void update(User user) {
        Connection connection = DBConnection.getConnection();
        PreparedStatement statement = null;
        if (connection != null) {
            try {
                // Create user
                connection.setAutoCommit(false);
                statement = connection.prepareStatement(MyQuery.UPDATE_USER);
                statement.setString(1, user.getFullName());
                statement.setString(2, user.getCode());

                String birthday = MyUtil.localDateToString(user.getBirthday());
                statement.setString(3, birthday);

                String initTime = MyUtil.localDateTimeToString(user.getInitTime());
                statement.setString(4, initTime);
                statement.setInt(5, user.getUserId());
                statement.executeUpdate();

                // Delete Account chứa User
                statement = connection.prepareStatement(MyQuery.DELETE_ACC);
                statement.setInt(1, user.getUserId());
                statement.executeUpdate();

                // Create lại Account chứa User
                for (Role r : user.getRoles()) {
                    statement = connection.prepareStatement(MyQuery.CREATE_ACC);
                    statement.setInt(1, user.getUserId());
                    statement.setInt(2, r.getRoleId());
                    statement.executeUpdate();
                }
                connection.commit();
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
    }

    @Override
    public void delete(int id) {
        Connection connection = DBConnection.getConnection();
        PreparedStatement statement = null;
        if (connection != null) {
            try {
                statement = connection.prepareStatement(MyQuery.DELETE_ACC);
                statement.setInt(1, id);
                statement.executeUpdate();

                statement = connection.prepareStatement(MyQuery.DELETE_USER);
                statement.setInt(1, id);
                statement.executeUpdate();
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
    }

    @Override
    public void create(User user) {
        Connection connection = DBConnection.getConnection();
        PreparedStatement statement = null;

        if (connection != null) {
            try {
                // Create user
                connection.setAutoCommit(false);
                statement = connection.prepareStatement(MyQuery.CREATE_USER);
                statement.setInt(1, user.getUserId());
                statement.setString(2, user.getFullName());
                statement.setString(3, user.getCode());

                String birthday = MyUtil.localDateToString(user.getBirthday());
                statement.setString(4, birthday);

                String initTime = MyUtil.localDateTimeToString(user.getInitTime());
                statement.setString(5, initTime);
                statement.executeUpdate();

                // Create account
                for (Role r : user.getRoles()) {
                    statement = connection.prepareStatement(MyQuery.CREATE_ACC);
                    statement.setInt(1, user.getUserId());
                    statement.setInt(2, r.getRoleId());
                    statement.executeUpdate();
                }
                connection.commit();
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
    }

    @Override
    public List<User> search(String name) {
        Connection connection = DBConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<User> userList = new ArrayList<>();
        Map<Integer, User> userMap = new HashMap<>();

        if (connection != null) {
            try {
                statement = connection.prepareStatement(MyQuery.SEARCH_USER);
                statement.setString(1, name);
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int userId = resultSet.getInt("userId");
                    String fullName = resultSet.getString("fullName");
                    String code = resultSet.getString("code");

                    Date d1 = resultSet.getDate("birthday");
                    LocalDate birthday = MyUtil.convertToLocalDateViaSqlDate(d1);
                    Date d2 = resultSet.getDate("initTime");
                    LocalDateTime initTime = MyUtil.convertToLocalDateTimeViaSqlTimestamp(d2);

                    int roleId = resultSet.getInt("roleId");
                    String roleName = resultSet.getString("roleName");
                    Role role = new Role(roleId, roleName);

                    if (userMap.containsKey(userId)) {
                        userMap.get(userId).getRoles().add(role);
                        userMap.replace(userId, userMap.get(userId));
                    } else {
                        List<Role> roles = new ArrayList<>();
                        roles.add(role);
                        User user = new User(userId, fullName, code, birthday, initTime, roles);
                        userMap.put(userId, user);
                    }
                }
                for (User value : userMap.values()) {
                    userList.add(value);
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
        return userList;
    }
}
