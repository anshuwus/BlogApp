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

import com.ashokit.binding.CreatePost;
import com.ashokit.entity.Comment;
import com.ashokit.entity.Post;
import com.ashokit.service.IPostService;

@Controller
public class PostController {
	@Autowired
	private IPostService service;
	
	@Autowired
	private HttpSession session;
	
	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		System.out.println("PostController.dashboard()");
		Integer userId = (Integer) session.getAttribute("userId");
		if(userId!=null) {
			List<Post> list = service.getDashboardData(userId);
			model.addAttribute("list", list);
		}
		return "dashboardPage";
	}
	
	
	@GetMapping("/newPost")
	public String createPost(@ModelAttribute("createPost")CreatePost createPost) {
		return "createPostPage";
	}
	
	@PostMapping("/newPost")
	public String createPostProcess(@ModelAttribute("createPost")CreatePost createPost,RedirectAttributes attr,@RequestParam(name="postId",required=false)Integer postId) {
		System.out.println("PostController.createPostProcess()"+createPost);
		Integer userId=(Integer)session.getAttribute("userId");
		if(userId!=null && createPost!=null) {
			boolean status = service.upsertPost(createPost, userId,postId);
			if(status)
				return "redirect:dashboard";
			else
				attr.addFlashAttribute("errMsg","Post not uploaded");
		}
		return "redirect:newPost";
	}
	
	@GetMapping("/edit")
	public String editPost(@ModelAttribute("edit")CreatePost createPost,@RequestParam(name="postId",required=false)Integer postId,Model model) {
		if(postId!=null) {
			//for edit form binding
			CreatePost post = service.getOnePost(postId);
			model.addAttribute("edit", post);
			model.addAttribute("postId",postId);
		}
		return "editPostPage";
	}
	
	@PostMapping("/edit")
	public String updatePost(@ModelAttribute("createPost")CreatePost createPost,
			                  RedirectAttributes attr,
			                  @RequestParam(name="postId",required=false)Integer postId) {
		
		//for update blog
		if(postId!=null) {
			boolean status = service.upsertPost(createPost, null, postId);
			if(status)
				attr.addFlashAttribute("succMsg","Post Updated");
			else
				attr.addFlashAttribute("errMsg","Post not uploaded");
		}
		return "redirect:edit";
	}
	
	@GetMapping("/deletePost")
	public String deletePost(@RequestParam("postId")Integer postId) {
		service.deletePost(postId);
		return "redirect:dashboard";
	}
	
	@GetMapping("/comments")
	public String postComments(Model model) {
		System.out.println("PostController.postComments()");
		Integer userId=(Integer)session.getAttribute("userId");
		List<Comment> list = service.getAllCommentsByUserId(userId);
		list.forEach(System.out::println);
		model.addAttribute("list", list);
		return "commentsPage";
	}
	
	@GetMapping("/deleteComment")
	public String deleteComment(@RequestParam("id")Integer commentId) {
		service.deleteComment(commentId);
		return "redirect:comments";
		
	}
	
	@GetMapping("/searchPost")
	public String searchBlog(@RequestParam("data")String data,Model model) {
		List<Post> searchBlog = service.searchBlog(data);
		model.addAttribute("list", searchBlog);
		return "filteredPost";
	}
}
