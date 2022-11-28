package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try(Connection conn = Util.getInstance().getConnection()) {
            try (Statement statement = conn.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS users " +
                        "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), last_name VARCHAR(255), age INT)");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try(Connection conn = Util.getInstance().getConnection()) {
            try (Statement statement = conn.createStatement()) {
                statement.executeUpdate("DROP TABLE IF EXISTS users");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(Connection conn = Util.getInstance().getConnection()) {
            try (PreparedStatement pstm = conn.prepareStatement("INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)")) {
                pstm.setString(1, name);
                pstm.setString(2, lastName);
                pstm.setByte(3, age);
                pstm.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try(Connection conn = Util.getInstance().getConnection()) {
            try (PreparedStatement pstm = conn.prepareStatement("DELETE FROM users WHERE id = ?")) {
                pstm.setLong(1, id);
                pstm.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(Connection conn = Util.getInstance().getConnection()) {
            try (ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM users")){
                while(resultSet.next()) {
                    User user = new User(resultSet.getString("name"),
                            resultSet.getString("last_name"), resultSet.getByte("age"));
                    user.setId(resultSet.getLong("id"));
                    users.add(user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
                e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try(Connection conn = Util.getInstance().getConnection()) {
            try (Statement statement = conn.createStatement()) {
                statement.executeUpdate("TRUNCATE TABLE users");
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
