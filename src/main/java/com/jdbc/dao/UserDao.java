package com.jdbc.dao;

import com.jdbc.connectionMaker.AwsConnectionMaker;
import com.jdbc.connectionMaker.ConnectionMaker;
import com.jdbc.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.EmptyStackException;
import java.util.List;

public class UserDao {
    private DataSource dataSource;
    private JdbcContext jdbcContext;
    private JdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcContext = new JdbcContext(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<User> rowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User(rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("password"));
            return user;
        }
    };

    public void add(final User user) {
        this.jdbcTemplate.update("INSERT INTO users(id,name,password) values(?,?,?);",
                user.getId(),
                user.getName(),
                user.getPassword());
    }

    public User get(String id) {
        String sql = "SELECT * FROM `likelion-db`.users WHERE id =?";
        return this.jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public void deleteAll() throws SQLException {
        // Template Callback 적용으로 한줄로 정리
        this.jdbcContext.executeSql("DELETE FROM `likelion-db`.users");
    }

    public int getCount() {
        return this.jdbcTemplate.queryForObject("SELECT COUNT(*) from users;", Integer.class);
    }

    public List<User> getAll() {
        String sql = "SELECT * FROM users order by id";
        return this.jdbcTemplate.query(sql, rowMapper);
    }
}