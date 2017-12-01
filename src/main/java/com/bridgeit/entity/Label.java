package com.bridgeit.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Table(name = "label")
@Entity
public class Label {

	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Integer id;

	@Column(name = "name")
	private String name;

	@JsonIgnore
	@ManyToMany(mappedBy="label", cascade=CascadeType.ALL)
	private Set<Note> notes = new HashSet<>();

	@JsonIgnore
	@ManyToOne
	private User user;

	public User getUser() {
		return user;
	}

	public Label(Integer id, String name, Set<Note> notes, User user) {
		super();
		this.id = id;
		this.name = name;
		this.notes = notes;
		this.user = user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Note> getNotes() {
		return notes;
	}

	public void setNotes(Set<Note> notes) {
		this.notes = notes;
	}

	public Label(Integer id, String name, Set<Note> notes) {
		super();
		this.id = id;
		this.name = name;
		this.notes = notes;
	}

	public Label() {

	}

	@Override
	public String toString() {
		return "Label [id=" + id + ", name=" + name + ", notes=" + notes + "]";
	}

}
