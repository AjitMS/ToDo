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

import com.bridgeit.entity.Label;
import com.bridgeit.entity.Note;
import com.bridgeit.entity.User;
import com.bridgeit.scheduler.DeleteNote;
import com.bridgeit.service.NoteService;
import com.bridgeit.service.UserService;

/**
 * @author Ajit Shikalgar Controller for managing notes such as CRUD operations,
 *         trashing, pinning etc.
 *
 */
@RestController
public class NoteController {

	Logger logger = Logger.getLogger(NoteController.class);

	@Autowired
	NoteService noteService;

	@Autowired
	UserService userService;

	@Autowired
	DeleteNote deleteNote;

	/**
	 * @param request
	 * @param response
	 * @return a function that returns list of notes for particular user.
	 */
	@GetMapping("/userallnotes") // userId
	public ResponseEntity<List<Note>> showNotes(HttpServletRequest request, HttpServletResponse response) {
		Integer uId = (Integer) request.getAttribute("userId");
		logger.info("userId in request is: " + uId);
		String noteCategory = request.getHeader("noteCategory");
		logger.info("note category: " + noteCategory);
		if (uId == -1) {
			logger.info("User not found / Token validation failed");
			return new ResponseEntity<List<Note>>(HttpStatus.BAD_REQUEST);
		}
		List<Note> noteList = new ArrayList<>();

		noteList = noteService.getNoteList(uId, noteCategory);

		if (noteList == null) {
			System.out.println("No notes found for user ID: " + uId);
			return new ResponseEntity<List<Note>>(HttpStatus.NOT_FOUND);
		}
		Runtime.getRuntime().freeMemory();
		return new ResponseEntity<List<Note>>(noteList, HttpStatus.OK);

	}

	@GetMapping("/usernotes") // userId
	public ResponseEntity<List<Note>> showAllNotes(HttpServletRequest request, HttpServletResponse response) {
		Integer uId = (Integer) request.getAttribute("userId");
		logger.info("userId in request is: " + uId);
		if (uId == -1) {
			logger.info("User not found / Token validation failed");
			return new ResponseEntity<List<Note>>(HttpStatus.BAD_REQUEST);
		}
		List<Note> noteList = new ArrayList<>();

		noteList = noteService.getAllNoteList(uId);

		if (noteList == null) {
			System.out.println("No notes found for user ID: " + uId);
			return new ResponseEntity<List<Note>>(HttpStatus.NOT_FOUND);
		}
		Runtime.getRuntime().freeMemory();
		return new ResponseEntity<List<Note>>(noteList, HttpStatus.OK);

	}

	/**
	 * @param note
	 * @param request
	 * @param response
	 * @return API that manages the creation of notes
	 */
	@PostMapping(value = "usernotes/createnote")
	public ResponseEntity<Note> createNote(@RequestBody Note note, HttpServletRequest request,
			HttpServletResponse response) {
		Integer uId = (Integer) request.getAttribute("userId");
		logger.info("userId in request is: " + uId);

		logger.info("Id is: " + uId);
		try {
			Note createdNote = noteService.createNote(uId, note);
			logger.info("***Note Created***");
			return new ResponseEntity<Note>(createdNote, HttpStatus.OK);
		} catch (Exception E) {
			E.printStackTrace();
			logger.info("***Note caanot be Created***");
			return new ResponseEntity<Note>(HttpStatus.BAD_REQUEST);
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
	@PutMapping(value = "/usernotes/updatenote")
	public ResponseEntity<Note> updateNote(@RequestBody Note updatedNote, HttpServletRequest request)
			throws IOException {
		System.out.println("updatedNOte.image: " + updatedNote.getImage());
		Integer uId = (Integer) request.getAttribute("userId");
		Integer nId = updatedNote.getNoteId();
		System.out.println("imafge : " + updatedNote.getImage());
		logger.info("userId in request is: " + uId);
		Note oldNote = noteService.getNoteById(uId, nId);
		if (oldNote == null) {
			oldNote = noteService.getCompleteNoteById(nId);
			if (oldNote == null) {
				logger.info("User does not exist");
				return new ResponseEntity<Note>(HttpStatus.BAD_REQUEST);
			}
			System.out.println("****Oldnote: " + oldNote);
			/*
			 * for (User tempUser : oldNote.getCollabUsers()) if
			 * (tempUser.getId().compareTo(nId) == 0) { }
			 * logger.info("user updated a collaborated note"); return new
			 * ResponseEntity<Note>(updatedNote, HttpStatus.OK);
			 */
		}
		noteService.updateNote(updatedNote, nId);
		System.out.println("***updatedNote: " + updatedNote);
		return new ResponseEntity<Note>(updatedNote, HttpStatus.OK);
	}

	/**
	 * @param nId
	 * @param request
	 * @param response
	 * @return in order to delete notes permanently, use deleteNode API
	 */
	@DeleteMapping(value = "usernotes/deletenote")
	public ResponseEntity<String> deleteNode(@RequestBody String noteId, HttpServletRequest request,
			HttpServletResponse response) {

		Integer uId = (Integer) request.getAttribute("userId");
		Integer nId = Integer.parseInt(noteId);
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

	@PutMapping(value = "/usernotes/movetotrash")
	public ResponseEntity<String> moveToTrash(@RequestBody Note note, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		Integer uId = (Integer) request.getAttribute("userId");
		logger.info("userId in request is: " + uId);

		/* logger.info("noteId in request is: " + nId); */
		Integer nId = note.getNoteId();
		User user = new User();
		user = userService.getUserById(uId, user);
		if (user == null) {
			logger.info("Note cannot be deleted. user not found");
			return new ResponseEntity<String>("User not found", HttpStatus.BAD_REQUEST);
		}
		try {
			note = noteService.getNoteById(uId, nId);
		} catch (Exception E) {
			logger.info("Note Owner not found");
		}
		System.out.println("Note is: " + note);

		try {
			if (note == null)
				note = noteService.getCompleteNoteById(nId);
			if (note == null) {
				logger.info("No note found");
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}
			if (uId.compareTo(note.getUser().getId()) == 0) {

				noteService.moveToTrash(nId);
				// call scheduler with uId and nId to delete in every 7 days
				logger.info("Note moved to trash");
				return new ResponseEntity<String>(HttpStatus.OK);
			} else
				try {
					noteService.unCollaborate(note, user);
					return new ResponseEntity<String>("Note Uncollaborate", HttpStatus.OK);
				} catch (Exception E) {
					logger.info("" + E);
				}

		} catch (Exception E) {
			E.printStackTrace();
			return new ResponseEntity<String>("Note does not exists", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@PostMapping(value = "/usernotes/collaborate")
	public ResponseEntity<User> collaborateNote(@RequestBody Note cNote, HttpServletRequest request,
			HttpServletResponse response) {
		User cUser = new User();
		logger.info("Note Object: " + cNote);
		if (cNote == null) {
			logger.info("Note Empty");
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}
		String email = request.getHeader("userEmail");
		User tempUser = new User();
		tempUser = userService.getUserByEmail(email, tempUser);
		if (!userService.userExists(tempUser)) {
			logger.info("Invalid Email in collaborator");
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}
		Integer userId = (Integer) request.getAttribute("userId");
		if (request.getHeader("userEmail") == null) {
			logger.info("*********No email received*********");
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}

		logger.info("collaborating note with id: " + cNote.getNoteId());

		cNote = noteService.getNoteById(cNote.getUser().getId(), cNote.getNoteId());
		System.out.println("cNOte: " + cNote);
		if (cNote == null) {
			logger.info("Note does not exist");
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}
		Integer cUserId = tempUser.getId();
		// check if logged in user is editing note.
		// or collaborated users editing note //

		if (userId.compareTo(cNote.getUser().getId()) == 0) {
			cUser = userService.getUserById(cUserId, cUser);
			noteService.collaborateUser(cUser, cNote);

		} else {
			logger.info("Note Owner Authorization failed");
			return new ResponseEntity<User>(cUser, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<User>(cUser, HttpStatus.OK);
	}

	@GetMapping("/usernotes/getuserbyid")
	public ResponseEntity<User> getUserById(HttpServletRequest request, HttpServletResponse response) {
		Integer uId = (Integer) request.getAttribute("userId");
		logger.info("Got Id in request is: " + uId);
		User user = new User();
		user = userService.getUserById(uId, user);
		logger.info("User sent is: " + user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@PostMapping("/usernotes/createlabel")
	public ResponseEntity<User> createLabel(@RequestBody Label label, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("Label is: " + label);
		Integer uId = (Integer) request.getAttribute("userId");
		logger.info("Got Id in request is: " + uId);
		User user = new User();
		user = userService.getUserById(uId, user);
		noteService.createLabel(user, label);
		return new ResponseEntity<User>(HttpStatus.OK);
	}

	@GetMapping("/usernotes/getlabels")
	public ResponseEntity<List<Label>> getLabels(HttpServletRequest request, HttpServletResponse response) {
		Integer uId = (Integer) request.getAttribute("userId");
		logger.info("Got Id in request is: " + uId);
		User user = new User();
		user = userService.getUserById(uId, user);
		List<Label> labelList = noteService.getLabels(user);
		return new ResponseEntity<List<Label>>(labelList, HttpStatus.OK);
	}

}
