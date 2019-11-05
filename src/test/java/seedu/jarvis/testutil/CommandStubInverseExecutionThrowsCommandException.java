package seedu.jarvis.testutil;

import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;

/**
 * {@code CommandStubInverseExecutionThrowsCommandException} is a stub class for {@code Command} that will always
 * throw a {@code CommandException} when inversely executed.
 * {@code CommandStubInverseExecutionThrowsCommandException} returns true when checked for having an inverse
 * execution.
 */
public class CommandStubInverseExecutionThrowsCommandException extends CommandStub {
    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        throw new CommandException("CommandException always thrown.");
    }
}
