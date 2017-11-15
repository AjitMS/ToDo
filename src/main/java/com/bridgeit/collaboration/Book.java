package com.bridgeit.collaboration;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "book")
public class Book {
	@Id
	@Column(name = "bId")
	private String bId = null;

	@Column(name = "bName")
	private String bName;

	@ManyToMany
	private Collection<Student> studentList;

	public Collection<Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(Collection<Student> studentList) {
		this.studentList = studentList;
	}

	public String getbId() {
		return bId;
	}

	public Book(String bId, String bName) {
		super();
		this.bId = bId;
		this.bName = bName;
	}

	public void setbId(String bId) {
		this.bId = bId;
	}

	public String getbName() {
		return bName;
	}

	public void setbName(String bName) {
		this.bName = bName;
	}

	@Override
	public String toString() {
		return "Book [bId=" + bId + ", bName=" + bName + "]";
	}

	public Book() {
	}
}
