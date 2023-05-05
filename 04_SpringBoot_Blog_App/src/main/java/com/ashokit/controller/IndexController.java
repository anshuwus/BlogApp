package com.ashokit.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ashokit.binding.CommentDAO;
import com.ashokit.entity.Comment;
import com.ashokit.entity.Post;
import com.ashokit.service.IPostService;

@Controller
public class IndexController {
	@Autowired
	private IPostService service;
	@Autowired
	private HttpSession session;
	
	@GetMapping("/")
	public String indexPage(Model model) {
		List<Post> list = service.getIndexPageData();
		model.addAttribute("list", list);
		return "index";
	}
	
	@GetMapping("/view_post")
	public String viewPost(@ModelAttribute("comment")CommentDAO post,@RequestParam Integer postId,Model model) {
		System.out.println("IndexController.viewPost(): "+postId);
		Post entity = service.getOneViewPost(postId);
		List<Comment> list = service.getAllCommentByPostId(postId);
	    list.forEach(System.out::println);
		if(entity!=null && list!=null) {
			model.addAttribute("post", entity);
			model.addAttribute("list", list);
		    return "viewPostPage";
		}
		return "/";
	}
	
	@PostMapping("/comment")
	public String savecomment(@ModelAttribute("comment")CommentDAO comment,Integer postId,RedirectAttributes attr) {
		System.out.println("IndexController.savecomment(): "+comment);
		if(comment!=null) {
			service.saveComment(comment, postId);
		}
		attr.addAttribute("postId", postId);
		return "redirect:view_post";
	}
	
	@GetMapping("/search")
	public String searchBlog(@RequestParam("data")String data,Model model) {
		System.out.println("IndexController.searchBlog():: "+data);
		List<Post> searchBlog = service.searchBlog(data);
		searchBlog.forEach(System.out::println);
		model.addAttribute("list", searchBlog);
		/*if(!searchBlog.isEmpty()) {
			System.out.println("List is not empty");
			searchBlog.forEach(System.out::println);
			return "filteredBlog";
		}
		else
			System.out.println("List is empty");*/
		return "filteredBlog";
	}
}
