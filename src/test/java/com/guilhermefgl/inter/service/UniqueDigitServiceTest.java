package com.guilhermefgl.inter.service;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UniqueDigitServiceTest {

	@Autowired
	private UniqueDigitService service;

	@Test
	public void testUniqueDigit() {
		assertEquals(Integer.valueOf(2), service.uniqueDigit("9875"));
	}

	@Test
	public void testUniqueDigitConcat() {
		assertEquals(Integer.valueOf(116), service.uniqueDigit(4, 9875));
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
