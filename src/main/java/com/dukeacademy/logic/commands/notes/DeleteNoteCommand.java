package com.dukeacademy.logic.commands.notes;

import java.util.logging.Logger;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandResult;
import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.notes.NotesLogic;
import com.dukeacademy.logic.notes.exceptions.NoteNotFoundException;
import com.dukeacademy.model.notes.Note;
import com.dukeacademy.model.state.Activity;
import com.dukeacademy.model.state.ApplicationState;

/**
 * DeleteNoteCommand that encapsulates the action of deleting a Note from the application. The note is deleted according
 * to its id as specified by the invariant of the NotesLogic instance used by the command to perform the deletion. The
 * Command also changes the current activity of the application to NOTES. The command relies on the NotesLogic and
 * ApplicationState classes to perform the necessary actions.
 */
public class DeleteNoteCommand implements Command {
    private final Logger logger;

    private final NotesLogic notesLogic;
    private final int id;
    private final ApplicationState applicationState;

    /**
     * Constructor, the command will delete the Note with the given id from the NotesLogic instance.
     * @param notesLogic the NotesLogic used to perform the deletion
     * @param id the id of the Note to be deleted
     * @param applicationState the ApplicationState
     */
    public DeleteNoteCommand(NotesLogic notesLogic, int id, ApplicationState applicationState) {
        this.logger = LogsCenter.getLogger(DeleteNoteCommand.class);

        this.applicationState = applicationState;
        this.notesLogic = notesLogic;
        this.id = id - 1;
    }

    @Override
    public CommandResult execute() throws CommandException {
        logger.info("Attempting to delete note at index : " + id);

        try {
            Note deletedNote = notesLogic.deleteNote(id);

            logger.info("Successfully deleted note : " + deletedNote);

            // Switches the current activity to make the ui focus on the NOTES tab
            applicationState.setCurrentActivity(Activity.NOTE);

            return new CommandResult("Successfully deleted your note : " + deletedNote.getTitle());
        } catch (NoteNotFoundException e) {
            logger.warning("No note found at index : " + (id + 1));
            throw new CommandException("No note at index " + (id + 1) + " found");
        }
    }
}
