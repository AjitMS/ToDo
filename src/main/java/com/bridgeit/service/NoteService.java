package com.bridgeit.service;

import java.util.List;

import com.bridgeit.entity.Note;

/**
 * @author Ajit Shikalgar service layer for UserController which calls UserDao
 *         for DB operations and EmailUtility for mail operations
 */
public interface NoteService {

	/**
	 * @param uId
	 * @param nId
	 * @return gets note by particular note id
	 */
	public Note getNoteById(Integer uId, Integer nId);

	/**
	 * @param updatedNote
	 * @param nId
	 *            deals with updation of note. calls UserDao for updation
	 */
	public void updateNote(Note updatedNote, Integer nId);

	/**
	 * @param uId
	 * @param note
	 *            service layer operation that performs creation of note
	 */
	public void createNote(Integer uId, Note note);

	/**
	 * @param uId
	 * @return fetches noteList from NoteDao for particular user Id
	 */
	List<Note> getNoteList(Integer uId);

	/**
	 * @param uId
	 * @param nId
	 *            deals with deletion of note
	 */
	void deleteNote(Integer uId, Integer nId);

	/**
	 * @param note
	 */
	public void moveToTrash(Integer nId);

	/**
	 * @param uId
	 * @return fetches trashed notes from NoteDao
	 */
	List<Note> getTrashedNoteList(Integer uId);

}
