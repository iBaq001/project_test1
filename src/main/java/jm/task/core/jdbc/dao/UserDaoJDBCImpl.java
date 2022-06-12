package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.*;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Util.getConnection();
        Util.createStatement();
        try {
            statement.executeUpdate("CREATE TABLE users (" +
                    "id int(5) NOT NULL AUTO_INCREMENT primary key ," +
                    "name VARCHAR(40) not null ," +
                    "lastName VARCHAR(40) not null ," +
                    "age int(3))");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        Util.getConnection();
        Util.createStatement();
        try {
            statement.executeUpdate("DROP TABLE users");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        Util.getConnection();
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name, lastName, age) Values (?, ?, ?)")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {       // не правильно реализован preparedStatement
        Util.getConnection();
        try (PreparedStatement statement = connection.prepareStatement("Delete FROM users  WHERE id = ?")) {
            statement.setInt(1, (int) id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        Util.getConnection();
        Util.createStatement();
        List<String> use = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                use.add(String.valueOf(resultSet.getInt(1)));
                use.add(resultSet.getString(2));
                use.add(resultSet.getString(3));
                use.add(String.valueOf(resultSet.getInt(4)));
            }
            System.out.println(use);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cleanUsersTable() {

    }
}
