package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;

import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new MarkAttendanceCommandParser object
 */
public class MarkAttendanceCommandParser implements Parser<MarkAttendanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkAttendanceCommand
     * and returns an MarkAttendanceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkAttendanceCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Prefix[] emptyPrefix = new Prefix[0];
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, emptyPrefix);

        ArrayList<Index> indexes = new ArrayList<>();

        try {
            String allIndexes = argMultimap.getPreamble();
            String[] splitIndex = allIndexes.split(",");
            for (String index : splitIndex) {
                Index parsedIndex = ParserUtil.parseIndex(index);
                indexes.add(parsedIndex);
            }
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkAttendanceCommand.MESSAGE_USAGE), pe);
        }

        return new MarkAttendanceCommand(indexes);
    }

}
