package seedu.jarvis.testutil;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.history.commands.exceptions.InvalidCommandToJsonException;

/**
 * {@code CommandStub} returns true when checked for having an inverse execution.
 * {@code CommandStub} returns null and does nothing to {@code Model} when {@code CommandStub#execute(Model)} or
 * {@code CommandStub#executeInverse(Model)} is called.
 */
public class CommandStub extends Command {
    @Override
    public String getCommandWord() {
        throw new AssertionError("This message should not be called.");
    }

    @Override
    public boolean hasInverseExecution() {
        return true;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }

    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        return null;
    }

    @Override
    public JsonAdaptedCommand adaptToJsonAdaptedCommand() throws InvalidCommandToJsonException {
        throw new AssertionError("This message should not be called.");
    }
}
