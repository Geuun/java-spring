package com.jdbc.dao;

import com.jdbc.connectionMaker.AwsConnectionMaker;
import com.jdbc.connectionMaker.ConnectionMaker;
import com.jdbc.domain.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.EmptyStackException;

public class UserDao {
    private DataSource dataSource;
    private JdbcContext jdbcContext;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcContext = new JdbcContext(dataSource);
    }

    public void add(final User user) throws SQLException {
        this.jdbcContext.jdbcContextWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {

                    PreparedStatement pstmt = connection.prepareStatement("INSERT INTO `likelion-db`.users(id, name, password) VALUES (?, ?, ?)");

                    pstmt.setString(1, user.getId());
                    pstmt.setString(2, user.getName());
                    pstmt.setString(3, user.getPassword());

                    return pstmt;
            }
        });
//        Connection connection = null;
//        PreparedStatement pstmt = null;
//        try {
//            // DB접속 (mysql)
//            connection = dataSource.getConnection();
//
//            // Query문 작성
//            pstmt = connection.prepareStatement("INSERT INTO `likelion-db`.users(id, name, password) VALUES(?,?,?);");
//            pstmt.setString(1, user.getId());
//            pstmt.setString(2, user.getName());
//            pstmt.setString(3, user.getPassword());
//
//            // Query문 실행
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if (pstmt != null) {
//                try {
//                    pstmt.close();
//                } catch (SQLException e) {
//
//                }
//            }
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//
//                }
//            }
//        }
    }

    public User findById(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;
        try {
            // DB접속 (mysql)
            connection = dataSource.getConnection();

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
        this.jdbcContext.jdbcContextWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
                return connection.prepareStatement("DELETE FROM `likelion-db`.users");
            }
        });
//        Connection connection = null;
//        PreparedStatement pstmt = null;
//
//        try {
//            // DB접속 (mysql)
//            connection = dataSource.getConnection();
//
//            // Query문 작성
//            pstmt = connection.prepareStatement("DELETE FROM `likelion-db`.users");
//
//            // Query문 실행
//            pstmt.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            if (pstmt != null) {
//                try {
//                    pstmt.close();
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
    }

    public int getCount() throws SQLException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            //DB 접속
            connection = dataSource.getConnection();

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


}