package seedu.elisa.logic.parser;

import static seedu.elisa.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.elisa.logic.parser.CliSyntax.PREFIX_AUTO_RESCHEDULE;
import static seedu.elisa.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.elisa.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.elisa.logic.parser.CliSyntax.PREFIX_REMINDER;
import static seedu.elisa.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.elisa.commons.core.item.Event;
import seedu.elisa.commons.core.item.Item;
import seedu.elisa.commons.core.item.Item.ItemBuilder;
import seedu.elisa.commons.core.item.ItemDescription;
import seedu.elisa.commons.core.item.Priority;
import seedu.elisa.commons.core.item.Reminder;
import seedu.elisa.commons.core.item.tag.Tag;
import seedu.elisa.logic.commands.AddCommand;
import seedu.elisa.logic.commands.AddEventCommand;
import seedu.elisa.logic.parser.exceptions.ParseException;
import seedu.elisa.model.AutoReschedulePeriod;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddEventCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String desc, String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATETIME, PREFIX_REMINDER, PREFIX_PRIORITY,
                        PREFIX_TAG, PREFIX_AUTO_RESCHEDULE);

        // Event must have a deadline.
        if (!arePrefixesPresent(argMultimap, PREFIX_DATETIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        ItemDescription description = ParserUtil.parseDescription(desc);
        // Event must be present.
        Event event = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).get()).get();
        Optional<Reminder> itemReminder = ParserUtil.parseReminder(
                argMultimap.getValue(PREFIX_REMINDER).orElse(null));
        Optional<Priority> priority = ParserUtil.parsePriority(
                argMultimap.getValue(PREFIX_PRIORITY).orElse(null));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        try {
            Optional<AutoReschedulePeriod> reschedulePeriod = ParserUtil.parseReschedule(
                    argMultimap.getValue(PREFIX_AUTO_RESCHEDULE).orElse(null));
            if (reschedulePeriod.isPresent()) {
                event = event.setAutoReschedule(true).setReschedulePeriod(reschedulePeriod.get());
                if (event.getStartDateTime().isBefore(LocalDateTime.now())) {
                    LocalDateTime updatedDateTime = ParserUtil.getUpdatedDateTime(event.getStartDateTime(),
                            reschedulePeriod.get().getPeriod());
                    event = event.changeStartDateTime(updatedDateTime);
                }
            }
        } catch (ParseException pe) {
            throw pe;
        } catch (Exception e) {
            System.out.println("Issue with parsing -auto " + e.getMessage());
        }

        ItemBuilder itemBuilder = new ItemBuilder();
        itemBuilder.setItemDescription(description);
        itemBuilder.setTags(tagList);

        if (priority.isPresent()) {
            itemBuilder.setItemPriority(priority.get());
        }
        itemBuilder.setEvent(event);


        if (itemReminder.isPresent()) {
            itemBuilder.setReminder(itemReminder.get());
        }

        Item newItem;
        try {
            newItem = itemBuilder.build();
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }

        AddCommand addCommand = new AddEventCommand(newItem);
        return addCommand;
    }


    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

