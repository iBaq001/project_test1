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
                "id int(5) NOT NULL AUTO_INCREMENT primary key ," +
                "name VARCHAR(40) not null ," +
                "lastName VARCHAR(40) not null ," +
                "age int(3))";
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
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String qst2 = "INSERT INTO users (name, lastName, age) Values (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(qst2)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String qst3 = "Delete FROM users  WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(qst3)) {
            statement.setInt(1, (int) id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {
        String qst4 = "SELECT * FROM users";
        List<User> use = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(qst4)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId((long) resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge((byte)resultSet.getInt(4));
                use.add(user);
            }
            System.out.println(use);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return use;
    }

    public void cleanUsersTable() {
        String qst6 = "DELETE  FROM users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(qst6);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
