package seedu.jarvis.testutil;

import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;

/**
 * {@code CommandStubExecutionThrowsCommandException} is a stub class for {@code Command} that will always throw a
 * {@code CommandException} when executed.
 * {@code CommandStubExecutionThrowsCommandException} returns true when checked for having an inverse execution.
 */
public class CommandStubExecutionThrowsCommandException extends CommandStub {
    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException("CommandException always thrown.");
    }
}
