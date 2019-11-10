package com.dukeacademy.logic.commands.notes;

import java.util.Optional;
import java.util.logging.Logger;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandResult;
import com.dukeacademy.logic.commands.exceptions.CommandException;
import com.dukeacademy.logic.notes.NotesLogic;
import com.dukeacademy.model.notes.Note;
import com.dukeacademy.model.state.Activity;
import com.dukeacademy.model.state.ApplicationState;


/**
 * OpenNoteCommand used to encapsulate the action of saving a note that is currently opened in the application.
 * The opened note is dictated by the currently-selected Note in the containing NotesLogic. The command also changes
 * the current activity of the application to NOTES. The command relies on the NotesLogic and ApplicationState classes
 * to perform the necessary actions.
 */
public class SaveNoteCommand implements Command {
    private final Logger logger;

    private final NotesLogic notesLogic;
    private final ApplicationState applicationState;

    public SaveNoteCommand(NotesLogic notesLogic, ApplicationState applicationState) {
        this.logger = LogsCenter.getLogger(SaveNoteCommand.class);

        this.notesLogic = notesLogic;
        this.applicationState = applicationState;
    }

    @Override
    public CommandResult execute() throws CommandException {
        logger.info("Attempting to save current working note...");

        Optional<Note> savedNote = notesLogic.saveNoteFromNoteSubmissionChannel();

        if (savedNote.isEmpty()) {
            logger.warning("No note currently opened. Skipping save...");
            throw new CommandException("No note is currently opened for saving");
        }

        logger.info("Successfully saved note : " + savedNote.get());

        // Switches the current activity to make the ui focus on the NOTES tab
        this.applicationState.setCurrentActivity(Activity.NOTE);

        return new CommandResult("Your note has been successfully saved : " + savedNote.get().getTitle());
    }
}
