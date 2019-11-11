package seedu.moolah.testutil;

import static seedu.moolah.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_TIMESTAMP;

import seedu.moolah.logic.commands.event.AddEventCommand;
import seedu.moolah.logic.commands.event.EditEventCommand;
import seedu.moolah.model.event.Event;

/**
 * A utility class for Event.
 */
public class EventUtil {

    /**
     * Returns an add command string for adding the {@code event}.
     */
    public static String getAddCommand(Event event) {
        return AddEventCommand.COMMAND_WORD + " " + getEventDetails(event);
    }

    /**
     * Returns the part of command string for the given {@code event}'s details.
     */
    public static String getEventDetails(Event event) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_DESCRIPTION + event.getDescription().fullDescription + " ");
        sb.append(PREFIX_PRICE + event.getPrice().value + " ");

        sb.append(PREFIX_CATEGORY + event.getCategory().getCategoryName() + " ");
        sb.append(PREFIX_TIMESTAMP + event.getTimestamp().toString() + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditEventDescriptor}'s details.
     */
    public static String getEditEventDescriptorDetails(EditEventCommand.EditEventDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getDescription().ifPresent(description -> sb
                .append(PREFIX_DESCRIPTION).append(description.fullDescription).append(" "));
        descriptor.getPrice().ifPresent(price -> sb.append(PREFIX_PRICE).append(price.value).append(" "));
        descriptor.getCategory().ifPresent(category -> sb.append(PREFIX_CATEGORY).append(
                category.getCategoryName()).append(" "));
        descriptor.getTimestamp()
                .ifPresent(timestamp -> sb.append(PREFIX_TIMESTAMP).append(timestamp.toString()).append(" "));
        return sb.toString();
    }
}
