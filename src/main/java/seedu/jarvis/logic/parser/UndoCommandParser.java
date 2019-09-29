package seedu.jarvis.logic.parser;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_UNDO_REDO;

import java.util.Optional;

import seedu.jarvis.logic.commands.UndoCommand;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.logic.version.VersionControl;

/**
 * Parses input arguments and creates a new {@code UndoCommand} object
 */
public class UndoCommandParser implements Parser<UndoCommand> {

    private static final String ARGUMENT_UNDO_ALL = "all";

    /**
     * Parses the given {@code String} of arguments in the context of the {@code UndoCommand}
     * and returns a {@code UndoCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    @Override
    public UndoCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_UNDO_REDO);
        Optional<String> optionalArgument = argumentMultimap.getValue(PREFIX_UNDO_REDO);

        // if no argument is given, create an undo command for one action.
        if (optionalArgument.isEmpty()) {
            return new UndoCommand();
        }

        String argument = optionalArgument.get();

        // if argument is all, create an undo command for all available undoable action.
        if (argument.equalsIgnoreCase(ARGUMENT_UNDO_ALL)) {
            return new UndoCommand(VersionControl.INSTANCE.getTotalNumberOfUndoableCommands());
        }

        int numberOfTimes;
        try {
            numberOfTimes = Math.max(Integer.parseInt(argument), 1);
        } catch (NumberFormatException nfe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
        }

        // if argument is a number, create an undo command to undo the given number of actions.
        return new UndoCommand(numberOfTimes);
    }

}
