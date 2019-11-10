package com.dukeacademy.logic.commands.notes;

import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandFactory;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;
import com.dukeacademy.logic.notes.NotesLogic;
import com.dukeacademy.model.state.ApplicationState;

/**
 * Factory class used to supply NewNoteCommands. The NewNoteCommand instances will be instantiated using the
 * NotesLogic and ApplicationState instance contained in the factory. The corresponding command word to invoke this
 * factory is "newnote".
 * */
public class NewNoteCommandFactory implements CommandFactory {
    private static final String TITLE_VALIDATION_REGEX = "[^\\s].*";

    private final NotesLogic notesLogic;
    private final ApplicationState applicationState;

    /**
     * Constructor, NewNoteCommand instances will be instantiated using the give NotesLogic and ApplicationState
     * @param notesLogic the NotesLogic instance
     * @param applicationState the ApplicationState instance
     */
    public NewNoteCommandFactory(NotesLogic notesLogic, ApplicationState applicationState) {
        this.notesLogic = notesLogic;
        this.applicationState = applicationState;
    }

    @Override
    public String getCommandWord() {
        return "newnote";
    }

    /**
     * The command arguments needed to create a NewNoteCommand is String corresponding to the title of the note that
     * is to be created.
     * @param commandArguments the command text from the user's input
     * @throws InvalidCommandArgumentsException if the command argument is not a valid alphanumerical String
     */
    @Override
    public Command getCommand(String commandArguments) throws InvalidCommandArgumentsException {
        if (!commandArguments.matches(TITLE_VALIDATION_REGEX)) {
            throw new InvalidCommandArgumentsException("Note title should not be empty!");
        }

        return new NewNoteCommand(notesLogic, commandArguments, applicationState);
    }
}
