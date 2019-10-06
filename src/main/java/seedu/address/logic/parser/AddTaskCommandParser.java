package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.*;
import seedu.address.model.tag.Tag;

public class AddTaskCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String desc, String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATETIME, PREFIX_REMINDER, PREFIX_PRIORITY, PREFIX_TAG);

        Description description = ParserUtil.parseDescription(desc);
        // Need to check if all below isPresent() first
        DateTime dateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).get());
        ItemReminder itemReminder = ParserUtil.parseReminder(argMultimap.getValue(PREFIX_REMINDER).get());
        Priority priority = ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Task task = new Task();
        // Task.setPriority(priority);
        Item item = new Item(description.toString(), Optional.of(task), Optional.empty(), Optional.empty(), tagList);

        if (dateTime.isPresent()) {
            Event event = new Event();
            event.setDate(dateTime.getDate());
            item.setEvent(event);
        }
        if (itemReminder.isPresent()) {
            Reminder reminder = new Reminder();
            reminder.setDate(itemReminder.getDate());
            item.setReminder(reminder);
        }

        return new AddCommand(item);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}