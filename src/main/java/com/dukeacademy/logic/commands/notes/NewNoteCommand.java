package com.dukeacademy.logic.commands.notes;

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
 * NewNoteCommand class that encapsulates the action of creating a new Note in the application. The command also changes
 * the current activity of the application to NOTES. The command relies on the NotesLogic and ApplicationState classes
 * to perform the necessary actions.
 */
public class NewNoteCommand implements Command {
    private final NotesLogic notesLogic;
    private final ApplicationState applicationState;
    private final String noteTitle;
    private final Logger logger;

    /**
     * Constructor, the command will create a new note with the given title.
     * @param notesLogic the NotesLogic instance used to perform the operation
     * @param noteTitle the title of the Note to be created
     * @param applicationState the ApplicationState
     */
    public NewNoteCommand(NotesLogic notesLogic, String noteTitle, ApplicationState applicationState) {
        this.logger = LogsCenter.getLogger(NewNoteCommand.class);

        this.applicationState = applicationState;
        this.notesLogic = notesLogic;
        this.noteTitle = noteTitle;
    }


    @Override
    public CommandResult execute() throws CommandException {
        Note newNote = new Note(noteTitle, "");

        logger.info("Attempting to save previously opened note...");
        notesLogic.saveNoteFromNoteSubmissionChannel();

        logger.info("Creating new note : " + noteTitle);

        notesLogic.addNote(newNote);
        notesLogic.selectNote(newNote);

        logger.info("Opening new note : " + newNote);

        // Switches the current activity to make the ui focus on the NOTES tab
        applicationState.setCurrentActivity(Activity.NOTE);

        return new CommandResult("New note successfully created : " + noteTitle, false);
    }
}
