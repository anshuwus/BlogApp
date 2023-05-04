package com.ashokit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.binding.CreatePost;
import com.ashokit.entity.Post;
import com.ashokit.entity.User;
import com.ashokit.repository.IPostRepo;
import com.ashokit.repository.IUserRepo;

@Service
public class PostService implements IPostService {
	@Autowired
	private IPostRepo postRepo;
	@Autowired
	private IUserRepo userRepo;

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
}
