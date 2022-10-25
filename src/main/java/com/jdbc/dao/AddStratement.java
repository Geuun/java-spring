package com.jdbc.dao;

import com.jdbc.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 익명클래스 사용으로 더 이상 사용하지 않음
 */
public class AddStratement implements StatementStrategy{
    private User user;

    public AddStratement(User user) {
        this.user = user;
    }
    @Override
    public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement("INSERT INTO `likelion-db`.users(id, name, password) VALUES (?, ?, ?)");

        pstmt.setString(1, user.getId());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getPassword());

        return pstmt;
    }
}
