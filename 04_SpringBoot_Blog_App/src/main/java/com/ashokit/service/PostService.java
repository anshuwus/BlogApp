package com.ashokit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.binding.CommentDAO;
import com.ashokit.binding.CreatePost;
import com.ashokit.entity.Comment;
import com.ashokit.entity.Post;
import com.ashokit.entity.User;
import com.ashokit.repository.ICommetRepo;
import com.ashokit.repository.IPostRepo;
import com.ashokit.repository.IUserRepo;

@Service
public class PostService implements IPostService {
	@Autowired
	private IPostRepo postRepo;
	@Autowired
	private IUserRepo userRepo;
	@Autowired
	private ICommetRepo commentRepo;

	@Override
	public boolean upsertPost(CreatePost createPost, Integer userId,Integer postId) {
		boolean status=false;
		//for save form data
		if(postId==null) {
			Optional<User> opt = userRepo.findById(userId);
			if(opt.isPresent()) {
				User user = opt.get();
				Post entity=new Post();
				BeanUtils.copyProperties(createPost, entity);
				entity.setUser(user);
				postRepo.save(entity);
				status=true;
			}
		}//if
		else {
			//for update form data
			if(postId!=null) {
				Optional<Post> opt = postRepo.findById(postId);
				if(opt.isPresent()) {
					Post post = opt.get();
					BeanUtils.copyProperties(createPost, post);
					postRepo.save(post);
					status=true;
				}
			}
		}//else
		
		return status;
	}

	@Override
	public List<Post> getDashboardData(Integer userId) {
		Optional<User> opt = userRepo.findById(userId);
		List<Post> list=null;
		if(opt.isPresent()) {
			User user = opt.get();
			list = postRepo.findByUser(user);
			list.forEach(System.out::println);
		}
		return list;
	}

	@Override
	public CreatePost getOnePost(Integer postId) {
		Optional<Post> opt = postRepo.findById(postId);
		CreatePost entity = new CreatePost();
		if(opt.isPresent()) {
			Post post = opt.get();
			BeanUtils.copyProperties(post, entity);
		}
		return entity;
	}

	@Override
	public List<Post> getIndexPageData() {
		return postRepo.findAll();
	}

	@Override
	public Post getOneViewPost(Integer postId) {
		Optional<Post> opt = postRepo.findById(postId);
		if(opt.isPresent()) {
			Post post = opt.get();
			return post;
		}
		return null;
	}

	@Override
	public boolean saveComment(CommentDAO comment,Integer postId) {
		boolean status=false;
		Optional<Post> opt = postRepo.findById(postId);
		if(opt.isPresent()) {
			Post post = opt.get();
			//create Comment entity class object
			Comment entity=new Comment();
			BeanUtils.copyProperties(comment, entity);
			entity.setPost(post);
			//save in db
			commentRepo.save(entity);
			status=true;
		}
		return status;
	}

	@Override
	public List<Comment> getAllCommentByPostId(Integer postId) {
		Optional<Post> opt = postRepo.findById(postId);
		List<Comment> list=null;
		if(opt.isPresent()) {
			Post post = opt.get();
			list = post.getComments();
		}
		return list;
	}

	@Override
	public List<Comment> getAllCommentsByUserId(Integer userId) {
		Optional<User> opt = userRepo.findById(userId);
		if(opt.isPresent()) {
			User user = opt.get();
			List<Post> list = postRepo.findByUser(user);
			List<Comment> listOfComment=null;
			List<Comment> commList=new ArrayList<Comment>();
			for(Post post : list) {			
				listOfComment = post.getComments();
				for(Comment c:listOfComment) {
					commList.add(c);
				}
			}//for
		
			return commList;
		}
		return null;
	}

	@Override
	public void deletePost(Integer postId) {
		postRepo.deleteById(postId);
	}

	@Override
	public void deleteComment(Integer commentId) {
		commentRepo.deleteById(commentId);
	}

	@Override
	public List<Post> searchBlog(String data) {
		System.out.println("PostService.searchBlog()");
		List<Post> list = postRepo.findAll();
		List<Post> post = list.stream()
		    .filter(e -> e.getTitle().contains(data))
		    .collect(Collectors.toList());
		
		return post;
	}
}
