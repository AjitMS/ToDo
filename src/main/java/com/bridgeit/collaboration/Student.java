package com.bridgeit.collaboration;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class Student {
	@Id
	@Column(name = "sId")
	private String sId;
	
	@Column(name = "email")
	private String email;
	
	@ManyToMany(mappedBy = "studentList")
	private Collection<Book> bookList;

	public String getsId() {
		return sId;
	}

	public void setsId(String sId) {
		this.sId = sId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Student(String sId, String email, Collection<Book> bookList) {
		super();
		this.sId = sId;
		this.email = email;
		this.bookList = bookList;
	}

	public Collection<Book> getBookList() {
		return bookList;
	}

	public void setBookList(Collection<Book> bookList) {
		this.bookList = bookList;
	}

	public Student() {

	}
}
