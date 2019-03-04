package com.guilhermefgl.inter.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guilhermefgl.inter.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
