package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetDeadlineCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class SetDeadlineCommandParser implements Parser<SetDeadlineCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetDeadline
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetDeadlineCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK_INDEX, PREFIX_DEADLINE);

        Index index;
        LocalDateTime dateTime;

        try {
            dateTime = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DEADLINE).get());
        } catch (DateTimeParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetDeadlineCommand.MESSAGE_USAGE), pe);
        }

        try {
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_TASK_INDEX).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetDeadlineCommand.MESSAGE_USAGE), pe);
        }
        return new SetDeadlineCommand(index, dateTime);
    }

}
