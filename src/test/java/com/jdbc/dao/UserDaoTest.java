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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;

    UserDao userDao;
    User user;
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
    void addAndget() throws SQLException {

        assertEquals(0, userDao.getCount());

        // add
        userDao.add(user1);
        userDao.add(user2);

        // get
        user = userDao.get("1");
        user = userDao.get("2");
        assertEquals(2, userDao.getCount());
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

    @Test
    @DisplayName("getAll Test : 없을 때 빈 리스트 리턴하는지 / 있을 때 개수만큼 리턴하는지")
    void getAllTest() throws SQLException {
        userDao.deleteAll();
        List<User> users = userDao.getAll();
        assertEquals(0,users.size());
        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);
        users = userDao.getAll();
        assertEquals(3,users.size());
    }
}