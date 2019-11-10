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
 * OpenNoteCommand used to encapsulate the action of opening a Note for viewing and editing in the application. The note
 * to be opened is the note corresponding to the id specified by the invariant of the containing NotesLogic. The
 * command also changes the current activity of the application to NOTES. The command relies on the NotesLogic and
 * ApplicationState classes to perform the necessary actions. Before opening a new Note, the command will also save
 * any changes to the previously viewed/edited note.
 */
public class OpenNoteCommand implements Command {
    private final Logger logger;

    private final NotesLogic notesLogic;
    private final int id;
    private final ApplicationState applicationState;

    /**
     * Constructor, the command will open the Note with the given id from the NotesLogic instance.
     * @param notesLogic the NotesLogic used to perform the deletion
     * @param id the id of the Note to be opened
     * @param applicationState the ApplicationState
     */
    public OpenNoteCommand(NotesLogic notesLogic, int id, ApplicationState applicationState) {
        this.logger = LogsCenter.getLogger(OpenNoteCommand.class);

        this.notesLogic = notesLogic;
        this.id = id - 1;
        this.applicationState = applicationState;
    }


    @Override
    public CommandResult execute() throws CommandException {
        logger.info("Attempting to save previously opened note...");

        // First save the previously opened Note
        this.notesLogic.saveNoteFromNoteSubmissionChannel();

        // Attempt to open the Note with the given id
        Note selectedNote;

        try {
            logger.info("Attempting to open note at index : " + id);
            selectedNote = this.notesLogic.selectNote(id);
        } catch (NoteNotFoundException e) {
            logger.warning("No note found at index : " + id);
            throw new CommandException("No note found at index : " + (id + 1));
        }

        logger.info("Successfully opened note at index : " + selectedNote);

        // Switches the current activity to make the ui focus on the NOTES tab
        applicationState.setCurrentActivity(Activity.NOTE);

        return new CommandResult("Successfully opened note : " + selectedNote.getTitle(), false);
    }
}
