package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }
    static Connection connection = Util.getConnection();

    public void createUsersTable() {
        String qst = "CREATE TABLE users (" +
                "id BIGINT(5) NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(40) NOT NULL," +
                "lastName VARCHAR(40) NOT NULL," +
                "age TINYINT(3))";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(qst);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String qst1 = "DROP TABLE users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(qst1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String qst2 = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(qst2)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String qst3 = "DELETE FROM users  WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(qst3)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        String qst4 = "SELECT * FROM users";
        List<User> use = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(qst4)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                use.add(user);
            }
            System.out.println(use);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return use;
    }

    public void cleanUsersTable() {
        String qst6 = "DELETE  FROM users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(qst6);
        } catch (SQLException e) {
            e.printStackTrace();
            e.printStackTrace();
        }
    }
}
