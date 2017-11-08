package com.bridgeit.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.entity.Note;
import com.bridgeit.service.NoteService;
import com.bridgeit.service.UserService;

/**
 * @author Ajit Shikalgar Controller for managing notes such as CRUD operations,
 *         trashing, pinning etc.
 *
 */
@RestController("/usernotes")
public class NoteController {

	Logger logger = Logger.getLogger(NoteController.class);

	@Autowired
	NoteService noteService;

	@Autowired
	UserService userService;

	/**
	 * @param request
	 * @param response
	 * @return a function that returns list of notes for particular user.
	 */
	@GetMapping("/usernotes") // userId
	public ResponseEntity<List<Note>> showNotes(HttpServletRequest request, HttpServletResponse response) {
		Integer uId = (Integer) request.getAttribute("userId");
		logger.info("userId in request is: " + uId);
		List<Note> noteList = new ArrayList<>();

		noteList = noteService.getNoteList(uId);

		if (noteList == null) {
			System.out.println("No notes found for user ID: " + uId);
			return new ResponseEntity<List<Note>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Note>>(noteList, HttpStatus.OK);

	}

	/**
	 * @param note
	 * @param request
	 * @param response
	 * @return API that manages the creation of notes
	 */
	@PostMapping(value = "usernotes/createnote")
	public ResponseEntity<String> createNote(@RequestBody Note note, HttpServletRequest request,
			HttpServletResponse response) {
		Integer uId = (Integer) request.getAttribute("userId");
		logger.info("userId in request is: " + uId);

		logger.info("Id is: " + uId);
		try {
			noteService.createNote(uId, note);
			return new ResponseEntity<String>("Note Added.", HttpStatus.OK);
		} catch (Exception E) {
			E.printStackTrace();
			return new ResponseEntity<String>("User does not exist", HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * @param nId
	 * @param request
	 * @param response
	 * @return API to get a note of particular id
	 */
	@GetMapping(value = "usernotes/{nId}") // noteId
	public ResponseEntity<Note> showNote(@PathVariable("nId") Integer nId, HttpServletRequest request,
			HttpServletResponse response) {
		Integer uId = (Integer) request.getAttribute("userId");
		logger.info("userId in request is: " + uId);

		Note note;
		try {
			note = noteService.getNoteById(uId, nId);
		} catch (Exception E) {
			logger.error("Error Loading Note / Note not found");
			return new ResponseEntity<Note>(HttpStatus.NO_CONTENT);
		}

		System.out.println("Note is loaded");
		return new ResponseEntity<Note>(note, HttpStatus.OK);
	}

	/**
	 * @param updatedNote
	 * @param nId
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 *             API needed to manage updating of notes
	 */
	@PutMapping(value = "/usernotes/updatenote/{nId}")
	public ResponseEntity<Note> updateNote(@RequestBody Note updatedNote, @PathVariable("nId") Integer nId,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer uId = (Integer) request.getAttribute("userId");
		logger.info("userId in request is: " + uId);
		Note oldNote = noteService.getNoteById(uId, nId);
		if (oldNote == null) {

			PrintWriter out = response.getWriter();
			out.print("Note does not exist. note id is null");
			return new ResponseEntity<Note>(HttpStatus.BAD_REQUEST);
		}
		updatedNote.getUser().setId(uId);
		updatedNote.setCreatedDate(oldNote.getCreatedDate());
		noteService.updateNote(updatedNote, nId);
		return new ResponseEntity<Note>(updatedNote, HttpStatus.OK);
	}

	/**
	 * @param nId
	 * @param request
	 * @param response
	 * @return in order to delete notes permanently, use deleteNode API
	 */
	@DeleteMapping(value = "usernotes/deletenote/{nId}")
	public ResponseEntity<String> deleteNode(@PathVariable("nId") Integer nId, HttpServletRequest request,
			HttpServletResponse response) {
		Integer uId = (Integer) request.getAttribute("userId");
		logger.info("userId in request is: " + uId);

		noteService.deleteNote(uId, nId);
		return new ResponseEntity<String>("Note Deleted. CHECK DB", HttpStatus.OK);
	}

	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 *             API needed to get trashed notes list
	 */
	@GetMapping(value = "/usernotes/trashed")
	public ResponseEntity<List<Note>> getTrashedNoteList(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Integer uId = (Integer) request.getAttribute("userId");
		logger.info("userId in request is: " + uId);
		List<Note> trashedNoteList = new ArrayList<>();
		trashedNoteList = noteService.getTrashedNoteList(uId);
		PrintWriter out;
		if (trashedNoteList != null)
			return new ResponseEntity<List<Note>>(trashedNoteList, HttpStatus.OK);
		else {
			out = response.getWriter();
			out.print("Empty trash");
			return new ResponseEntity<List<Note>>(HttpStatus.NO_CONTENT);
		}
	}

	@PutMapping(value = "/usernotes/movetotrash/{nId}")
	public ResponseEntity<String> moveToTrash(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("nId") Integer nId) throws IOException {
		Integer uId = (Integer) request.getAttribute("userId");
		logger.info("userId in request is: " + uId);
		try {
			noteService.moveToTrash(nId);
		} catch (Exception E) {
			E.printStackTrace();
			return new ResponseEntity<String>("Note does not exists", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Note moved to trash", HttpStatus.OK);
	}

}
