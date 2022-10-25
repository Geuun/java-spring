package com.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 익명클래스 사용으로 더 이상 사용하지 않음
 */
public class DeleteAllStatement implements StatementStrategy{
    @Override
    public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
//        PreparedStatement ps = connection.prepareStatement("DELETE FROM `likelion-db`.users");
//        return ps;
        return connection.prepareStatement("DELETE FROM `likelion-db`.users");
    }
}
