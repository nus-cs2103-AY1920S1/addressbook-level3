package seedu.guilttrip.logic.parser.remindercommandparsers;

import static java.util.Objects.requireNonNull;
import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_ARGUMENT_FORMAT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_FREQ;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_PERIOD;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.stream.Stream;

import seedu.guilttrip.commons.core.index.Index;
import seedu.guilttrip.logic.commands.remindercommands.SetReminderCommand;
import seedu.guilttrip.logic.parser.ArgumentMultimap;
import seedu.guilttrip.logic.parser.ArgumentTokenizer;
import seedu.guilttrip.logic.parser.Parser;
import seedu.guilttrip.logic.parser.ParserUtil;
import seedu.guilttrip.logic.parser.Prefix;
import seedu.guilttrip.logic.parser.exceptions.ParseException;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Period;
import seedu.guilttrip.model.util.Frequency;

/**
 * Parses input argument and creates a new SetReminderCommand object.
 */
public class SetReminderCommandParser implements Parser<SetReminderCommand> {
    private Index index;
    private String type;
    private Description desc;
    private Period period;
    private Frequency freq;

    /**
     * Parses the given {@code String} of arguments in the context of the EditExpenseCommand
     * and returns an EditExpenseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetReminderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_DESC, PREFIX_PERIOD, PREFIX_FREQ);
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_ARGUMENT_FORMAT, SetReminderCommand.MESSAGE_USAGE));
        }
        if (!arePrefixesPresent(argMultimap, PREFIX_TYPE, PREFIX_DESC, PREFIX_PERIOD, PREFIX_FREQ)) {
            throw new ParseException(String.format(MESSAGE_INVALID_ARGUMENT_FORMAT, SetReminderCommand.MESSAGE_USAGE));
        }
        type = argMultimap.getValue(PREFIX_TYPE).get().toLowerCase();
        desc = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESC).get());
        period = ParserUtil.parsePeriods(argMultimap.getValue(PREFIX_PERIOD).get().toLowerCase());
        freq = ParserUtil.parseFrequency(argMultimap.getValue(PREFIX_FREQ).get());
        return new SetReminderCommand(desc, type, index, period, freq);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values
     * in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap,
                                              Prefix... prefixes) {
        return Stream.of(prefixes)
                .allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
