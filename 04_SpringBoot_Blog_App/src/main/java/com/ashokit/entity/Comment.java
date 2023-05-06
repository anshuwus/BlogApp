package com.ashokit.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="COMMENT_TAB")
@Setter
@Getter
@SQLDelete(sql="UPDATE COMMENT_TAB SET STATUS='INACTIVE' WHERE id=?")
@Where(clause="STATUS <> 'INACTIVE'")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String email;
	
	@Lob
	private String content;
	
	@CreationTimestamp
	private LocalDate createdOn;
	
	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;
	
	private String status="ACTIVE";
	
	//toString()
	@Override
	public String toString() {
		return "Comment [id=" + id + ", name=" + name + ", email=" + email + ", content=" + content + ", createdOn="
				+ createdOn + "]";
	}

}
