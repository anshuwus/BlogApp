package com.ashokit.service;

import java.util.List;

import com.ashokit.binding.CreatePost;
import com.ashokit.entity.Post;

public interface IPostService {
	
	public boolean upsertPost(CreatePost createPost,Integer userId,Integer postId);
	public List<Post> getDashboardData(Integer userId);
	public CreatePost getOnePost(Integer postId);
	public List<Post> getIndexPageData();
}
