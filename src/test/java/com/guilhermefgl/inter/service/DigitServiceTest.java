package com.guilhermefgl.inter.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.guilhermefgl.inter.model.Digit;
import com.guilhermefgl.inter.model.User;
import com.guilhermefgl.inter.model.repositories.DigitRepository;
import com.guilhermefgl.inter.model.repositories.UserRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DigitServiceTest {

	@Autowired
	private DigitService service;

	@Autowired
	private DigitRepository digitRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testSave() {
		Digit digit = new Digit();
		digit.setK(1);
		digit.setN("2");
		digit.setDigit(3);
		digit = service.save(digit);

		assertNotNull(digit);
		assertNotNull(digit.getId());

		digitRepository.deleteAll();
	}

	@Test
	public void testListByUser() {
		User user = new User();
		user.setName("name");
		user.setEmail("email");
		user = userRepository.save(user);

		Digit digit = new Digit();
		digit.setK(1);
		digit.setN("2");
		digit.setDigit(3);
		digit.setUser(user);
		digit = digitRepository.save(digit);

		List<Digit> digits = service.listByUser(user);

		assertNotNull(digit);
		assertFalse(digits.isEmpty());

		userRepository.deleteAll();
	}

	@Test
	public void testUniqueDigit() {
		assertEquals(Integer.valueOf(2), service.uniqueDigit("9875"));
		assertEquals(Integer.valueOf(8), service.uniqueDigit(4, "9875"));
	}

	@Test
	public void testConcatInteger() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Method method = service.getClass().getDeclaredMethod("repeatDigits", Integer.class, String.class);
		method.setAccessible(true);

		assertEquals("9875987598759875", method.invoke(service, 4, "9875"));
	}

	@Test
	public void testSumDigit() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Method method = service.getClass().getDeclaredMethod("sumDigits", String.class);
		method.setAccessible(true);

		assertEquals(Integer.valueOf(29), method.invoke(service, "9875"));
		assertEquals(Integer.valueOf(11), method.invoke(service, "29"));
		assertEquals(Integer.valueOf(2), method.invoke(service, "11"));
		assertEquals(Integer.valueOf(2), method.invoke(service, "2"));
	}

}
