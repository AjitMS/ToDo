package com.bridgeit.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "user-note")
@Entity
public class NoteCollaborator {
	private User createdBy;
	private User createdFor;
	private Note collaboratedNote;

	@Override
	public String toString() {
		return "NoteCollaborator [createdBy=" + createdBy + ", createdFor=" + createdFor + ", collaboratedNote="
				+ collaboratedNote + "]";
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getCreatedFor() {
		return createdFor;
	}

	public void setCreatedFor(User createdFor) {
		this.createdFor = createdFor;
	}

	public Note getCollaboratedNote() {
		return collaboratedNote;
	}

	public void setCollaboratedNote(Note collaboratedNote) {
		this.collaboratedNote = collaboratedNote;
	}

	public NoteCollaborator(User createdBy, User createdFor, Note collaboratedNote) {
		super();
		this.createdBy = createdBy;
		this.createdFor = createdFor;
		this.collaboratedNote = collaboratedNote;
	}
}
