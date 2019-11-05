package seedu.guilttrip.logic.parser.conditioncommandparsers;

import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Stream;

import seedu.guilttrip.logic.commands.conditioncommands.AddDateConditionCommand;
import seedu.guilttrip.logic.parser.ArgumentMultimap;
import seedu.guilttrip.logic.parser.ParserUtil;
import seedu.guilttrip.logic.parser.Prefix;
import seedu.guilttrip.logic.parser.exceptions.ParseException;
import seedu.guilttrip.model.entry.Date;

/**
 * Parses input arguments and creates a new AddDateConditionCommand object
 */
public class AddDateConditionCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the AddClassConditionCommand
     * and returns an AddClassConditionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddDateConditionCommand parse(String args) throws ParseException {
        try {
            List<Date> dates = ParserUtil.parseStartAndEndDate(args.trim());
            if (dates.size() != 2) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDateConditionCommand.MESSAGE_USAGE));
            }
            return new AddDateConditionCommand(dates.get(0), dates.get(1));
        } catch (DateTimeParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDateConditionCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> prefix == PREFIX_DATE);
    }
}
