package com.guilhermefgl.inter.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guilhermefgl.inter.model.Digit;
import com.guilhermefgl.inter.model.User;

public interface DigitRepository extends JpaRepository<Digit, Long> {

	List<Digit> findByUser(User user);

}
