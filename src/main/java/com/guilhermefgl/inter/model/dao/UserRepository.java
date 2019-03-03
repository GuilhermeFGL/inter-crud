package com.guilhermefgl.inter.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guilhermefgl.inter.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
