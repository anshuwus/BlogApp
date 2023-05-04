package com.ashokit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashokit.entity.User;

public interface IUserRepo extends JpaRepository<User, Integer> {
	
	public User findByEmail(String email);
}
