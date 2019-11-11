package seedu.guilttrip.logic.parser.remindercommandparsers;

import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_LOWER_BOUND;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_UPPER_BOUND;

import java.util.Set;
import java.util.stream.Stream;

import seedu.guilttrip.logic.commands.remindercommands.AddGeneralReminderCommand;
import seedu.guilttrip.logic.parser.ArgumentMultimap;
import seedu.guilttrip.logic.parser.ArgumentTokenizer;
import seedu.guilttrip.logic.parser.Parser;
import seedu.guilttrip.logic.parser.ParserUtil;
import seedu.guilttrip.logic.parser.Prefix;
import seedu.guilttrip.logic.parser.exceptions.ParseException;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddGeneralReminderCommand object
 */
public class AddGeneralReminderCommandParser implements Parser<AddGeneralReminderCommand> {
    public static final String INCORRECT_AMT_RANGE = "Quota lower bound must be below the upper bound";
    public static final String INCORRECT_DATE_RANGE = "Starting date must be before ending date";


    /**
     * Parses the given {@code String} of arguments in the context of the AddGeneralReminderCommand
     * and returns an AddGeneralReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddGeneralReminderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args, PREFIX_TYPE, PREFIX_DESC, PREFIX_UPPER_BOUND, PREFIX_LOWER_BOUND,
                        PREFIX_START_DATE, PREFIX_END_DATE, PREFIX_TAG);
        if (!arePrefixesPresent(argMultimap, PREFIX_DESC)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGeneralReminderCommand.MESSAGE_USAGE));
        }
        Description header = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESC).get());
        AddGeneralReminderCommand command = new AddGeneralReminderCommand(header);
        if (argMultimap.getValue(PREFIX_TYPE).isPresent()) {
            command.setType(argMultimap.getValue(PREFIX_TYPE).get());
        }
        if (argMultimap.getValue(PREFIX_UPPER_BOUND).isPresent()) {
            command.setUpperBound(ParserUtil.parseAmount(argMultimap.getValue(PREFIX_UPPER_BOUND).get()).value);
        }
        if (argMultimap.getValue(PREFIX_LOWER_BOUND).isPresent()) {
            command.setLowerBound(ParserUtil.parseAmount(argMultimap.getValue(PREFIX_LOWER_BOUND).get()).value);
        }
        if (argMultimap.getValue(PREFIX_START_DATE).isPresent()) {
            command.setStart(ParserUtil.parseDate(argMultimap.getValue(PREFIX_START_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_END_DATE).isPresent()) {
            command.setEnd(ParserUtil.parseDate(argMultimap.getValue(PREFIX_END_DATE).get()));
        }
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        return command;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
