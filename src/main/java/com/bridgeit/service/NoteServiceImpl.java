package com.bridgeit.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.dao.NoteDao;
import com.bridgeit.entity.Label;
import com.bridgeit.entity.Note;
import com.bridgeit.entity.User;

@Service("noteService")
@Transactional
public class NoteServiceImpl implements NoteService {
	Logger logger = Logger.getLogger(NoteServiceImpl.class);
	@Autowired
	NoteDao dao;

	@Override
	@Transactional
	public Note getNoteById(Integer uId, Integer nId) {
		Note note = dao.getNoteById(uId, nId);
		return note;
	}

	@Override
	@Transactional
	public void updateNote(Note updatedNote, Integer nId) {
		dao.updateNote(updatedNote, nId);
		return;
	}

	@Override
	@Transactional
	public void deleteNote(Integer uId, Integer nId) {
		dao.deleteNote(uId, nId);
	}

	@Override
	public List<Note> getNoteList(Integer uId, String noteCategory) {
		List<Note> noteList;
		noteList = dao.getNoteList(uId, noteCategory);
		return noteList;
	}

	@Override
	public List<Note> getAllNoteList(Integer uId) {
		List<Note> noteList;
		noteList = dao.getAllNoteList(uId);
		return noteList;
	}

	@Override
	@Transactional
	public Note createNote(Integer uId, Note note) {
		return dao.createNote(uId, note);

	}

	@Override
	@Transactional
	public void moveToTrash(Integer nId) {
		dao.moveToTrash(nId);
	}

	@Override
	@Transactional
	public List<Note> getTrashedNoteList(Integer uId) {
		List<Note> trashedNoteList = dao.getTrashedNoteList(uId);
		if (trashedNoteList.size() == 0) {
			logger.info("Empty List");
			return null;
		}
		return trashedNoteList;
	}

	@Override
	@Transactional
	public void collaborateUser(User cUser, Note cNote) {
		dao.collaborateUser(cUser, cNote);

	}

	public Note getCompleteNoteById(Integer nId) {
		return dao.getCompleteNoteById(nId);
	}

	@Override
	@Transactional
	public void unCollaborate(Note cNote, User cUser) {
		dao.unCollaborate(cNote, cUser);

	}

	@Override
	public List<Note> getCompleteTrashedNoteList() {
		return dao.getCompleteTrashedNoteList();
	}

	@Override
	public void removeFromTrash(Note note) {
		dao.removeFromTrash(note);
	}

	@Override
	@Transactional
	public Label createLabel(User user, Label label) {
		return dao.createLabel(user, label);
	}

	@Override
	public List<Label> getLabels(User user) {
		return dao.getLabels(user);
	}

}
