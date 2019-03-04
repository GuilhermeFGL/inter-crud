package com.guilhermefgl.inter.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.guilhermefgl.inter.controller.dto.DigitDto;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CacheServiceTest {

	@Autowired
	private CacheService cache;

	@Test
	public void testCache() {
		cache.put(createDigitDto(1, "1"), 1);

		assertNotNull(cache.get(createDigitDto(1, "1")));

		cache.put(createDigitDto(2, "2"), 2);
		cache.put(createDigitDto(3, "3"), 3);
		cache.put(createDigitDto(4, "4"), 4);
		cache.put(createDigitDto(5, "5"), 5);
		cache.put(createDigitDto(6, "6"), 6);
		cache.put(createDigitDto(7, "7"), 7);
		cache.put(createDigitDto(8, "8"), 8);
		cache.put(createDigitDto(9, "9"), 9);
		cache.put(createDigitDto(10, "10"), 10);

		assertNotNull(cache.get(createDigitDto(1, "1")));
		assertNotNull(cache.get(createDigitDto(2, "2")));
		assertNotNull(cache.get(createDigitDto(3, "3")));
		assertNotNull(cache.get(createDigitDto(4, "4")));
		assertNotNull(cache.get(createDigitDto(5, "5")));
		assertNotNull(cache.get(createDigitDto(6, "6")));
		assertNotNull(cache.get(createDigitDto(7, "7")));
		assertNotNull(cache.get(createDigitDto(8, "8")));
		assertNotNull(cache.get(createDigitDto(9, "9")));
		assertNotNull(cache.get(createDigitDto(10, "10")));

		cache.put(createDigitDto(11, "11"), 11);

		assertNull(cache.get(createDigitDto(1, "1")));
		assertNotNull(cache.get(createDigitDto(2, "2")));
		assertNotNull(cache.get(createDigitDto(3, "3")));
		assertNotNull(cache.get(createDigitDto(4, "4")));
		assertNotNull(cache.get(createDigitDto(5, "5")));
		assertNotNull(cache.get(createDigitDto(6, "6")));
		assertNotNull(cache.get(createDigitDto(7, "7")));
		assertNotNull(cache.get(createDigitDto(8, "8")));
		assertNotNull(cache.get(createDigitDto(9, "9")));
		assertNotNull(cache.get(createDigitDto(10, "10")));
		assertNotNull(cache.get(createDigitDto(11, "11")));

		cache.put(createDigitDto(2, "2"), 2);
		cache.put(createDigitDto(3, "3"), 3);
		cache.put(createDigitDto(4, "4"), 4);
		cache.put(createDigitDto(5, "5"), 5);

		assertNotNull(cache.get(createDigitDto(2, "2")));
		assertNotNull(cache.get(createDigitDto(3, "3")));
		assertNotNull(cache.get(createDigitDto(4, "4")));
		assertNotNull(cache.get(createDigitDto(5, "5")));
		assertNotNull(cache.get(createDigitDto(6, "6")));
		assertNotNull(cache.get(createDigitDto(7, "7")));
		assertNotNull(cache.get(createDigitDto(8, "8")));
		assertNotNull(cache.get(createDigitDto(9, "9")));
		assertNotNull(cache.get(createDigitDto(10, "10")));
		assertNotNull(cache.get(createDigitDto(11, "11")));
	}

	private DigitDto createDigitDto(Integer k, String n) {
		DigitDto dto = new DigitDto();
		dto.setK(k);
		dto.setN(n);
		return dto;
	}

}
