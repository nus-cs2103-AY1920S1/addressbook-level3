package com.dukeacademy.logic.commands.notes;

import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandFactory;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;
import com.dukeacademy.logic.notes.NotesLogic;
import com.dukeacademy.model.state.ApplicationState;

/**
 * Factory class used to supply DeleteNoteCommands. The DeleteNoteCommand instances will be instantiated using the
 * NotesLogic and ApplicationState instance contained in the factory. The corresponding command word to invoke this
 * factory is "deletenote".
 */
public class DeleteNoteCommandFactory implements CommandFactory {
    private final NotesLogic notesLogic;
    private final ApplicationState applicationState;

    /**
     * Constructor, DeleteNoteCommand instances will be instantiated using the give NotesLogic and ApplicationState
     * @param notesLogic the NotesLogic instance
     * @param applicationState the ApplicationState instance
     */
    public DeleteNoteCommandFactory(NotesLogic notesLogic, ApplicationState applicationState) {
        this.notesLogic = notesLogic;
        this.applicationState = applicationState;
    }

    @Override
    public String getCommandWord() {
        return "deletenote";
    }

    /**
     * The command arguments needed to create a DeleteNoteCommand is an integer id corresponding to the note that
     * is meant to be deleted.
     * @param commandArguments the command text from the user's input
     * @throws InvalidCommandArgumentsException if the command argument is not a valid number
     */
    @Override
    public Command getCommand(String commandArguments) throws InvalidCommandArgumentsException {
        try {
            int index = Integer.parseInt(commandArguments.strip());
            return new DeleteNoteCommand(notesLogic, index, applicationState);
        } catch (NumberFormatException e) {
            throw new InvalidCommandArgumentsException("Index should be a valid number.");
        }
    }
}
