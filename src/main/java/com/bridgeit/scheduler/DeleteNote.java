package com.bridgeit.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bridgeit.entity.Note;
import com.bridgeit.service.NoteService;

@Component
public class DeleteNote {
	private final Logger logger = Logger.getLogger(DeleteNote.class);
	@Autowired
	private NoteService noteService;
	List<Note> trashedList;

	@Scheduled(fixedRate = 5 * 60 * 1000)
	public void deleteNote() {
		System.out.println("Reached into Scheduler");
		try {
			if (noteService.getCompleteTrashedNoteList() == null) {
				logger.info("Trash empty");
				return;
			}
			
			for (Note tempNote : noteService.getCompleteTrashedNoteList())
				if (tempNote.isInTrash() && (LocalDateTime.now().minusMinutes(1) == tempNote.getModifiedDate())) {
					noteService.removeFromTrash(tempNote);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// private final ScheduledExecutorService scheduler =
	// Executors.newScheduledThreadPool(10);

	/*
	 * @Async public void deleteEveryWeek(Integer uId, Integer nId) {
	 * 
	 * try { Thread.sleep(1*60*1000);
	 * 
	 * if (noteService.getNoteById(uId, nId).isInTrash()) {
	 * System.out.println("Deleting trashed note: " + noteService.getNoteById(uId,
	 * nId) + " for user: " + uId); noteService.deleteNote(uId, nId); return; }
	 * 
	 * else { logger.info("Note cannot be deleted. not in trash"); return; } } catch
	 * (Exception e) {
	 * 
	 * e.printStackTrace(); } }
	 */
	// final ScheduledFuture<?> deleteHandler = scheduler.schedule(noteDeletion, 24
	// * 60, TimeUnit.MINUTES);
	/*
	 * scheduler.schedule(new Runnable() { public void run() {
	 * deleteHandler.cancel(true); } }, 60 * 60, TimeUnit.SECONDS);
	 */
}

/*
 * 
 */
