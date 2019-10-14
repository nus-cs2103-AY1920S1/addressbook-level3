package seedu.jarvis.logic.parser.history;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CliSyntax.UndoRedoSyntax.PREFIX_UNDO_REDO;

import java.util.Optional;

import seedu.jarvis.logic.commands.history.UndoCommand;
import seedu.jarvis.logic.parser.ArgumentMultimap;
import seedu.jarvis.logic.parser.ArgumentTokenizer;
import seedu.jarvis.logic.parser.Parser;
import seedu.jarvis.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code UndoCommand} object
 */
public class UndoCommandParser implements Parser<UndoCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code UndoCommand}
     * and returns a {@code UndoCommand} object for execution.
     *
     * @throws ParseException If the user input does not conform the expected format.
     */
    @Override
    public UndoCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_UNDO_REDO);
        Optional<String> optionalArgument = argumentMultimap.getValue(PREFIX_UNDO_REDO);

        // If no argument is given, create an undo command for one action.
        if (optionalArgument.isEmpty()) {
            return new UndoCommand();
        }

        String argument = optionalArgument.get();
        int numberOfTimes;
        try {
            numberOfTimes = Integer.parseInt(argument);
        } catch (NumberFormatException nfe) {
            // If argument is not an integer.
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
        }

        // If argument is an integer that is less than 1.
        if (numberOfTimes < 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
        }

        // If argument is a valid integer, create an undo command to undo the given number of actions.
        return new UndoCommand(numberOfTimes);
    }

}
