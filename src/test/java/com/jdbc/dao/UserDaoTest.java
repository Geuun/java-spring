package com.jdbc.dao;

import com.jdbc.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;

    UserDao userDao;
    User user1;
    User user2;
    User user3;

    @BeforeEach
    void setUp() {

        this.user1 = new User("1", "geun", "1123");
        this.user2 = new User("2", "gana", "2223");
        this.user3 = new User("3", "dara", "3323");
        System.out.println("BeforeEach");
    }

    @Test
    @DisplayName("Add 와 Get Test")
    void addAndGet() throws SQLException {
        UserDao userDao = new UserDaoFactory().awsUserDao();

        //insert
        userDao.add(user1);

        //select
        User user = userDao.findById(user1.getId());
        //select 결과 값과 user1 와 비교하기
        assertEquals(user1.getName(), user.getName());
        assertEquals(user1.getPassword(), user.getPassword());
    }
}