package com.guilhermefgl.inter.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.guilhermefgl.inter.controller.dto.DigitDto;

@Service
public class CacheService {

	private static final int CACHE_LIMIT = 10;
	private static final Map<DigitDto, Integer> CACHE = new LinkedHashMap<DigitDto, Integer>();

	synchronized public void put(DigitDto dto, Integer result) {
		CACHE.putIfAbsent(dto, result);

		if (CACHE.size() > CACHE_LIMIT) {
			CACHE.remove((DigitDto) CACHE.entrySet().iterator().next().getKey());
		}
	}

	public Integer get(DigitDto dto) {
		return CACHE.get(dto);
	}

}
