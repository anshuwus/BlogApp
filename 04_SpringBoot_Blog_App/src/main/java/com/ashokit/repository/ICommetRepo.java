package com.ashokit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashokit.entity.Comment;

public interface ICommetRepo extends JpaRepository<Comment, Integer> {

}
