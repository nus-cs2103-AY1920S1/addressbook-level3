package seedu.tarence.logic.parser;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_UNDO_NUM_OF_STATES;

import java.util.stream.Stream;

import seedu.tarence.logic.commands.UndoCommand;
import seedu.tarence.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates an UndoCommand object.
 */
public class UndoCommandParser implements Parser<UndoCommand> {

    /**
     * Parses and creates an Undo Command
     * @param args String arguments
     * @return UndoCommand
     * @throws ParseException thrown if there are invalid parameters.
     */
    public UndoCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_UNDO_NUM_OF_STATES);

        if (!arePrefixesPresent(argMultimap, PREFIX_UNDO_NUM_OF_STATES)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
        }

        String numOfStatesToUndoString = argMultimap.getValue(PREFIX_UNDO_NUM_OF_STATES).get();

        try {
            int numOfStatesToUndo = Integer.parseInt(numOfStatesToUndoString);

            if (numOfStatesToUndo <= 0) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
            }

            return new UndoCommand(numOfStatesToUndo);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
