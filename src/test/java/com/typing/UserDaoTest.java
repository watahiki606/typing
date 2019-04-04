package com.typing;

import static org.junit.Assert.assertEquals;

import com.typing.login.domain.repository.UserDao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserDaoTest {

	@Autowired
	@Qualifier("UserDaoJdbcImpl")
	UserDao dao;

	@Test
	public void countTest1() {
		assertEquals(3, dao.count());
	}

	@Test
	@Sql("/testdata.sql")
	public void countTest2() {
		assertEquals(4, dao.count());
	}

}
