package com.bridgeit.dao;

import java.util.List;

import com.bridgeit.entity.Note;
import com.bridgeit.entity.User;

/**
 * @author Ajit Shikalgar
 *
 */
public interface NoteDao {

	/**
	 * @param uId
	 * @param note
	 *            method needed to add note for particular user into DB.
	 * 
	 */
	public void createNote(Integer uId, Note note);

	/**
	 * @param uId
	 * @param nId
	 * @return getting note for particular user and particular note depicted by note
	 *         Id
	 */
	public Note getNoteById(Integer uId, Integer nId);

	/**
	 * @param updatedNote
	 * @param nId
	 *            Method deals with updation of notes. it takes new updated note
	 *            along with note id to retrive old note. since, user cannot updated
	 *            noteId, created date etc it remains same
	 */
	public void updateNote(Note updatedNote, Integer nId);

	/**
	 * @param uId
	 * @param nId
	 *            deleting note by note id if note id exists
	 */
	public void deleteNote(Integer uId, Integer nId);

	/**
	 * @param uId
	 * @return this method looks up DB to find and retrieve notes for particular
	 *         user Id and not trashed
	 */
	public List<Note> getNoteList(Integer uId);

	/**
	 * @param note
	 *            in order to delete a note temporarily, moveToTrash method is
	 *            called by moveToTrash API from UserController
	 */
	public void moveToTrash(Integer nId);

	/**
	 * @param uId
	 * @return retreiving notes from trash
	 */
	List<Note> getTrashedNoteList(Integer uId);

	/**
	 * @param note
	 *            archiving a note by setting note's isArchived property to true
	 */
	void archiveNote(Note note);

	/**
	 * @param note
	 *            Pinning a note by setting note's isPinned property to true
	 */
	void pinNote(Note note);

	void collaborateUser(User cUser, Note cNote);

	Note getCompleteNoteById(Integer nId);

	public void unCollaborate(Note cNote, User cUser);

	void restoreFromTrash(Integer nId);

	List<Note> getCompleteTrashedNoteList();
	
	public void removeFromTrash(Note note);
}
