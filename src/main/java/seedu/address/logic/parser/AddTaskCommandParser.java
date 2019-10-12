package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.item.Event;
import seedu.address.commons.core.item.Item;
import seedu.address.commons.core.item.Item.ItemBuilder;
import seedu.address.commons.core.item.ItemDescription;
import seedu.address.commons.core.item.Priority;
import seedu.address.commons.core.item.Reminder;
import seedu.address.commons.core.item.Task;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 *  Parses input arguments and creates a new AddCommand object
 */
public class AddTaskCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String desc, String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATETIME, PREFIX_REMINDER, PREFIX_PRIORITY, PREFIX_TAG);

        ItemDescription description = ParserUtil.parseDescription(desc);
        Optional<Event> dateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).orElse(null));
        Optional<Reminder> itemReminder = ParserUtil.parseReminder(argMultimap.getValue(PREFIX_REMINDER).orElse(null));
        Optional<Priority> priority = ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).orElse(null));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        ItemBuilder itemBuilder = new ItemBuilder();
        itemBuilder.setItemDescription(description);
        itemBuilder.setTags(tagList);
        Task task = new Task(priority.orElse(null), null);
        itemBuilder.setTask(task);

        if (dateTime.isPresent()) {
            itemBuilder.setEvent(dateTime.get());
        }
        if (itemReminder.isPresent()) {
            itemBuilder.setReminder(itemReminder.get());
        }

        Item newItem;
        try {
            newItem = itemBuilder.build();
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }

        return new AddCommand(newItem);
    }
}
