package com.guilhermefgl.inter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guilhermefgl.inter.model.User;
import com.guilhermefgl.inter.model.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repository;

	public List<User> list() {
		return repository.findAll();
	}

	public Optional<User> find(Long id) {
		return repository.findById(id);
	}

	public User save(User user) {
		return repository.save(user);
	}

	public void delete(User user) {
		repository.delete(user);
	}

}
