package com.ashokit.service;

import java.util.List;

import com.ashokit.binding.CommentDAO;
import com.ashokit.binding.CreatePost;
import com.ashokit.entity.Comment;
import com.ashokit.entity.Post;

public interface IPostService {
	
	public boolean upsertPost(CreatePost createPost,Integer userId,Integer postId);
	public List<Post> getDashboardData(Integer userId);
	public CreatePost getOnePost(Integer postId);
	public List<Post> getIndexPageData();
	public Post getOneViewPost(Integer postId);
	public boolean saveComment(CommentDAO comment,Integer postId);
	public List<Comment> getAllCommentByPostId(Integer postId);
	public List<Comment> getAllCommentsByUserId(Integer userId);
	public void deletePost(Integer postId);
	public void deleteComment(Integer commentId);
	public List<Post> searchBlog(String data);
}
