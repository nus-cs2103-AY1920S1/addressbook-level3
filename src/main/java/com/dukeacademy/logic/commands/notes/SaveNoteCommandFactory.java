package com.dukeacademy.logic.commands.notes;

import com.dukeacademy.logic.commands.Command;
import com.dukeacademy.logic.commands.CommandFactory;
import com.dukeacademy.logic.commands.exceptions.InvalidCommandArgumentsException;
import com.dukeacademy.logic.notes.NotesLogic;
import com.dukeacademy.model.state.ApplicationState;

/**
 * Factory class used to supply SaveNoteCommands. The SaveNoteCommand instances will be instantiated using the
 * NotesLogic and ApplicationState instance contained in the factory. The corresponding command word to invoke this
 * factory is "savenote".
 * */
public class SaveNoteCommandFactory implements CommandFactory {
    private final ApplicationState applicationState;
    private final NotesLogic notesLogic;

    public SaveNoteCommandFactory(ApplicationState applicationState, NotesLogic notesLogic) {
        this.applicationState = applicationState;
        this.notesLogic = notesLogic;
    }

    @Override
    public String getCommandWord() {
        return "savenote";
    }

    /**
     * The SaveNoteCommand does not take in any command arguments.
     * @param commandArguments the command text from the user's input
     * @throws InvalidCommandArgumentsException if the command argument is not empty
     */
    @Override
    public Command getCommand(String commandArguments) throws InvalidCommandArgumentsException {
        if (!commandArguments.matches("\\s*")) {
            throw new InvalidCommandArgumentsException("Save note command does not take any arguments");
        }

        return new SaveNoteCommand(notesLogic, applicationState);
    }
}
