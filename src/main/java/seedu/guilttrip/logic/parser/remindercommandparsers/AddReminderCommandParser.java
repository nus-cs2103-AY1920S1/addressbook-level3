package seedu.guilttrip.logic.parser.remindercommandparsers;

import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.List;
import java.util.stream.Stream;

import seedu.guilttrip.commons.core.index.Index;
import seedu.guilttrip.logic.commands.remindercommands.AddGeneralReminderCommand;
import seedu.guilttrip.logic.parser.ArgumentMultimap;
import seedu.guilttrip.logic.parser.ArgumentTokenizer;
import seedu.guilttrip.logic.parser.Parser;
import seedu.guilttrip.logic.parser.ParserUtil;
import seedu.guilttrip.logic.parser.Prefix;
import seedu.guilttrip.logic.parser.exceptions.ParseException;
import seedu.guilttrip.model.entry.Amount;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.reminders.Reminder;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddReminderCommandParser implements Parser<AddGeneralReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddGeneralReminderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args, PREFIX_DESC, PREFIX_INDEX, PREFIX_TRACKER_TYPE, PREFIX_AMOUNT);
        if (!arePrefixesPresent(argMultimap, PREFIX_DESC, PREFIX_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddGeneralReminderCommand.MESSAGE_USAGE));
        }

        Description message = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESC).get());
        List<Index> conditionIndexes = ParserUtil.parseIndexes(argMultimap.getAllValues(PREFIX_INDEX));
        if (argMultimap.getValue(PREFIX_TRACKER_TYPE).isPresent()) {
            Reminder.TrackerType trackerType = ParserUtil.parseTrackerType(
                    argMultimap.getValue(PREFIX_TRACKER_TYPE).get());
            Amount amt = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
            Amount quota = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
            return new AddGeneralReminderCommand(message, conditionIndexes, trackerType, quota);
        } else {
            return new AddGeneralReminderCommand(message, conditionIndexes);
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
