package com.guilhermefgl.inter.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.guilhermefgl.inter.model.User;
import com.guilhermefgl.inter.model.repositories.UserRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

	@Autowired
	private UserRepository repository;

	@Autowired
	private UserService service;

	private User user;

	@Before
	public void setUp() {
		user = new User();
		user.setName("name");
		user.setEmail("email");
		user = repository.save(user);
	}

	@Test
	public void testList() {
		List<User> users = service.list();
		assertNotNull(users);
		assertFalse(users.isEmpty());
		assertEquals(1, users.size());
	}

	@Test
	public void testFind() {
		Optional<User> userOpt = service.find(user.getId());
		assertNotNull(userOpt);
		assertTrue(userOpt.isPresent());
	}

	@Test
	public void testSave() {
		User user = new User();
		user.setName("name 2");
		user.setEmail("email 2");

		user = service.save(user);

		assertNotNull(user);
		assertNotNull(user.getId());
		assertEquals("name 2", user.getName());
		assertEquals("email 2", user.getEmail());
	}

	@Test
	public void testDelete() {
		Optional<User> userOtp = service.find(user.getId());
		assertTrue(userOtp.isPresent());

		service.delete(user);

		userOtp = service.find(user.getId());
		assertFalse(userOtp.isPresent());
	}

	@After
	public void tearDown() {
		repository.deleteAll();
	}

}
