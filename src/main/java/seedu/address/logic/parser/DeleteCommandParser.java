package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SERIAL_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NUMBER;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteByIndexCommand;
import seedu.address.logic.commands.DeleteBySerialNumberCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.book.SerialNumber;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SERIAL_NUMBER);
            if (isPrefixPresent(argMultimap, PREFIX_SERIAL_NUMBER)) {
                //attempting to delete by serial number
                SerialNumber sn = ParserUtil.parseSerialNumber(argMultimap.getValue(PREFIX_SERIAL_NUMBER).get());
                return new DeleteBySerialNumberCommand(sn);
            } else {
                //attempting to delete by index
                Index index = ParserUtil.parseIndex(args);
                return new DeleteByIndexCommand(index);
            }
        } catch (ParseException pe) {
            if (serialNumberProvided(args)) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_SERIAL_NUMBER, DeleteCommand.MESSAGE_USAGE), pe);
            } else {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
            }
        }
    }

    /**
     * Returns true if user attempted to provide serial number.
     *
     * @param args arguments in string representation.
     * @return true if serial number is contained within the input string.
     */
    private boolean serialNumberProvided(String args) {
        // Arguments that come to this method are part of a failed command.
        // Therefore, if the serial number prefix is present, the serial number provided is invalid.
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SERIAL_NUMBER);
        return isPrefixPresent(argMultimap, PREFIX_SERIAL_NUMBER);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return argumentMultimap.getValue(prefix).isPresent();
    }

}
