package com.bridgeit.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Ajit Shikalgar Note entity with fields as noteId, title, description,
 *         pinned archived, created and modeified date
 */
@Entity
@Table(name = "notes")
public class Note {

	@Column(name = "title")
	private String title;

	@Column
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer noteId;

	// @NotNull(message = "improper value")
	@Column(name = "createdon")
	private LocalDateTime createdDate;

	@Column(name = "modifiedon")
	private LocalDateTime modifiedDate;

	@Column(name = "description")
	private String description;

	@Column(name = "isarchived")
	private boolean isArchived;

	// initially, note must be not in trash
	// so keep this true initially
	@Column(name = "intrash")
	private boolean inTrash = false;

	@Column(name = "ispinned")
	private boolean isPinned;

	// @JsonIgnore is required so as to prevent Jackson fasterxml databind to go
	// into loop
	// if not mentioned, program goes into infinite loop and results into stack
	// overflow

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	private User user = new User();

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToMany
	@JoinTable(name = "collabUsers", joinColumns = @JoinColumn(name = "noteid"), inverseJoinColumns = @JoinColumn(name = "userid"))
	private Set<User> collabUsers = new HashSet<>();

	@Column(name = "color")
	private String color = "#ffffff";

	@Lob
	@Column(name = "image", columnDefinition = "mediumblob")
	private String image;

	@Column(name = "reminder")
	private LocalDateTime reminder;

	public LocalDateTime getReminder() {
		return reminder;
	}

	public void setReminder(LocalDateTime reminder) {
		this.reminder = reminder;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Note(String title, Integer noteId, LocalDateTime createdDate, LocalDateTime modifiedDate, String description,
			boolean isArchived, boolean inTrash, boolean isPinned, User user, Set<User> collabUsers, String color,
			String image) {
		super();
		this.title = title;
		this.noteId = noteId;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.description = description;
		this.isArchived = isArchived;
		this.inTrash = inTrash;
		this.isPinned = isPinned;
		this.user = user;
		this.collabUsers = collabUsers;
		this.color = color;
		this.image = image;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public User getUser() {
		return user;
	}

	public Set<User> getCollabUsers() {
		return collabUsers;
	}

	public void setCollabUsers(Set<User> collabUsers) {
		this.collabUsers = collabUsers;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Note(String title, Integer noteId, LocalDateTime createdDate, LocalDateTime modifiedDate, String description,
			boolean isArchived, boolean inTrash, boolean isPinned, User user, Set<User> collabUsers, String color) {
		super();
		this.title = title;
		this.noteId = noteId;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.description = description;
		this.isArchived = isArchived;
		this.inTrash = inTrash;
		this.isPinned = isPinned;
		this.user = user;
		this.collabUsers = collabUsers;
		this.color = color;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Integer getNoteId() {
		return noteId;
	}

	public void setNoteId(Integer noteId) {
		this.noteId = noteId;
	}

	public boolean isArchived() {
		return isArchived;
	}

	public void setArchived(boolean isArchived) {
		this.isArchived = isArchived;
	}

	public boolean isInTrash() {
		return inTrash;
	}

	public void setInTrash(boolean inTrash) {
		this.inTrash = inTrash;
	}

	public boolean isPinned() {
		return isPinned;
	}

	public void setPinned(boolean isPinned) {
		this.isPinned = isPinned;
	}

	@Override
	public String toString() {
		return "Note [title=" + title + ", noteId=" + noteId + ", createdDate=" + createdDate + ", modifiedDate="
				+ modifiedDate + ", description=" + description + ", isArchived=" + isArchived + ", inTrash=" + inTrash
				+ ", isPinned=" + isPinned + ", user=" + user + ", collabUsers=" + collabUsers + ", color=" + color
				+ ", image=" + image + ", reminder=" + reminder + "]";
	}

	public Note() {

	}
}
