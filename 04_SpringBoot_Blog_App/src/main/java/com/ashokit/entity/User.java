package com.ashokit.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="USER_TAB")
@Setter
@Getter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	private String fname;
	private String lname;
	private String email;
	private String pwd;
	
	/*@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<Post> posts;
	*/
	
	//toString()
	@Override
	public String toString() {
		return "User [userId=" + userId + ", fname=" + fname + ", lname=" + lname + ", email=" + email + ", pwd=" + pwd
				+ "]";
	}
	
	
}
