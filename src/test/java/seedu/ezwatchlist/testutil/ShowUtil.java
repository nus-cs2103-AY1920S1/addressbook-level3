package seedu.ezwatchlist.testutil;

import java.util.Set;

import seedu.ezwatchlist.logic.commands.AddCommand;
import seedu.ezwatchlist.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.ezwatchlist.model.person.Person;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.model.tag.Tag;

import static seedu.ezwatchlist.logic.parser.CliSyntax.*;

/**
 * A utility class for Show.
 */
public class ShowUtil {

    /**
     * Returns an add command string for adding the {@code show}.
     */
    public static String getAddCommand(Show show) {
        return AddCommand.COMMAND_WORD + " " + getShowDetails(show);
    }

    /**
     * Returns the part of command string for the given {@code show}'s details.
     */
    public static String getShowDetails(Show show) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + show.getName().toString() + " ");
        sb.append(PREFIX_DESCRIPTION + show.getDescription().toString() + " ");
        sb.append(PREFIX_IS_WATCHED +show.isWatched().toString() + " ");
        sb.append(PREFIX_DATE_OF_RELEASE + show.getDateOfRelease().value + " ");
        sb.append(PREFIX_RUNNING_TIME + show.getRunningTime().toString() + " ");
        show.getActors().stream().forEach(
            s -> sb.append(PREFIX_ACTOR + s.actorName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditShowDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
