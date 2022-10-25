package com.jdbc.dao;

import com.jdbc.domain.User;

import java.sql.*;
import java.util.EmptyStackException;

public class UserDao {
    private ConnectionMaker connectionMaker;

    public UserDao() {
        this.connectionMaker = new AwsConnectionMaker();
    }

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = connectionMaker.makeConnection();

            pstmt = stmt.makePreparedStatement(connection);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void add(User user) throws SQLException {
        StatementStrategy st = new AddStratement(user);
        jdbcContextWithStatementStrategy(st);
    }

    public User findById(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;
        try {
            // DB접속 (mysql)
            connection = connectionMaker.makeConnection();

            // Query문 작성
            pstmt = connection.prepareStatement("SELECT * FROM `likelion-db`.users WHERE id = ?");
            pstmt.setString(1, id);

            // Query문 실행
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("id"), rs.getString("name"),
                        rs.getString("password"));
            }
            if (user == null) {
                throw new EmptyStackException();
            }

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void deleteAll() throws SQLException {
        StatementStrategy st = new DeleteAllStatement();
        jdbcContextWithStatementStrategy(st);
    }

    public int getCount() throws SQLException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            //DB 접속
            connection = connectionMaker.makeConnection();

            // Query문 작성
            pstmt = connection.prepareStatement("SELECT COUNT(*) FROM `likelion-db`.users");

            // Query문 실행
            rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        UserDao userDao = new UserDao();
        userDao.add(new User("1", "geun", "1234"));
        User user = userDao.findById("1");
        System.out.println(user.getName());
    }
}