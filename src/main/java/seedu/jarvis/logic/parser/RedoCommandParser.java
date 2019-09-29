package seedu.jarvis.logic.parser;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_UNDO_REDO;

import java.util.Optional;

import seedu.jarvis.logic.commands.RedoCommand;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.logic.version.VersionControl;

/**
 * Parses input arguments and creates a new {@code RedoCommand} object
 */
public class RedoCommandParser implements Parser<RedoCommand> {

    private static final String ARGUMENT_UNDO_ALL = "all";

    /**
     * Parses the given {@code String} of arguments in the context of the {@code RedoCommand}
     * and returns a {@code RedoCommand} object for execution.
     * @throws ParseException If the user input does not conform the expected format.
     */
    @Override
    public RedoCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_UNDO_REDO);
        Optional<String> optionalArgument = argumentMultimap.getValue(PREFIX_UNDO_REDO);

        // if no argument is given, create a redo command for one action.
        if (optionalArgument.isEmpty()) {
            return new RedoCommand();
        }

        String argument = optionalArgument.get();

        // if argument is all, create a redo command for all available actions.
        if (argument.equalsIgnoreCase(ARGUMENT_UNDO_ALL)) {
            return new RedoCommand(VersionControl.INSTANCE.getTotalNumberOfUndoableCommands());
        }

        int numberOfTimes;
        try {
            numberOfTimes = Math.max(Integer.parseInt(argument), 1);
        } catch (NumberFormatException nfe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
        }

        // if argument is a number, create a redo command to redo the given number of actions.
        return new RedoCommand(numberOfTimes);
    }
}
