package com.ashokit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ashokit.entity.Post;
import com.ashokit.entity.User;

public interface IPostRepo extends JpaRepository<Post, Integer> {
	
	public List<Post> findByUser(User user);
	@Query("select postId,title from Post")
	public List<Object[]> findAllPostByTitle();
}
