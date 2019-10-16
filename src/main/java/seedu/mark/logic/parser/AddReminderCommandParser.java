package seedu.mark.logic.parser;
import static java.util.Objects.requireNonNull;

import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_TIME;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import seedu.mark.commons.core.index.Index;
import seedu.mark.logic.commands.AddReminderCommand;
import seedu.mark.logic.parser.exceptions.ParseException;
import seedu.mark.model.reminder.Note;

/**
 * Parses input arguments and creates a new AddReminderCommand object
 */
public class AddReminderCommandParser implements Parser<AddReminderCommand> {
    @Override
    public AddReminderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TIME, PREFIX_NOTE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            System.out.println(argMultimap.getPreamble());
        } catch (ParseException pe) {
            System.out.println("no index");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddReminderCommand.MESSAGE_USAGE), pe);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_TIME)) {
            System.out.println("no time");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE));
        }



        // compulsory fields
        LocalDateTime time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());

        // optional fields
        Note note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).orElse(Note.DEFAULT_VALUE));

        return new AddReminderCommand(index, note, time);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


}
