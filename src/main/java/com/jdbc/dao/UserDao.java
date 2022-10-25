package com.jdbc.dao;

import com.jdbc.domain.User;

import java.sql.*;
import java.util.Map;

public class UserDao {
    private ConnectionMaker connectionMaker;

    public void add(User user) throws SQLException {
        Map<String, String> env = System.getenv();
        try {
            // DB접속 (mysql)
            Connection c = DriverManager.getConnection(env.get("DB_HOST"),
                    env.get("DB_USER"), env.get("DB_PASSWORD"));

            // Query문 작성
            PreparedStatement pstmt = c.prepareStatement("INSERT INTO `likelion-db`.users(id, name, password) VALUES(?,?,?);");
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());

            // Query문 실행
            pstmt.executeUpdate();

            pstmt.close();
            c.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findById(String id) throws SQLException {
        Map<String, String> env = System.getenv();
        Connection c;
        try {
            // DB접속 (mysql)
            c = DriverManager.getConnection(env.get("DB_HOST"),
                    env.get("DB_USER"), env.get("DB_PASSWORD"));

            // Query문 작성
            PreparedStatement pstmt = c.prepareStatement("SELECT * FROM `likelion-db`.users WHERE id = ?");
            pstmt.setString(1, id);

            // Query문 실행
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            User user = new User(rs.getString("id"), rs.getString("name"),
                    rs.getString("password"));

            rs.close();
            pstmt.close();
            c.close();

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws SQLException {
        UserDao userDao = new UserDao();
        userDao.add(new User("1", "geun", "1234"));
        User user = userDao.findById("1");
        System.out.println(user.getName());
    }
}