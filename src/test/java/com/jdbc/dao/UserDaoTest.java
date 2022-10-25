package com.jdbc.dao;

import com.jdbc.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
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
    void setUp() throws SQLException {
        this.userDao = context.getBean("awsUserDao", UserDao.class);

        userDao.deleteAll();

        this.user1 = new User("1", "geun", "1123");
        this.user2 = new User("2", "gana", "2223");
        this.user3 = new User("3", "dara", "3323");

        System.out.println("BeforeEach");
    }

    @Test
    @DisplayName("Add 와 Get Test")
    void addAndGet() throws SQLException {
        //insert
        userDao.add(user1);

        //select
        User user = userDao.findById(user1.getId());
        //select 결과 값과 user1 와 비교하기
        assertEquals(user1.getName(), user.getName());
        assertEquals(user1.getPassword(), user.getPassword());
    }

    @Test
    void count() throws SQLException {

        userDao.deleteAll();
        assertEquals(0, userDao.getCount());

        userDao.add(user1);
        assertEquals(1, userDao.getCount());
        userDao.add(user2);
        assertEquals(2, userDao.getCount());
        userDao.add(user3);
        assertEquals(3, userDao.getCount());
    }
}