package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditEateryDescriptor;
import seedu.address.model.eatery.Eatery;
import seedu.address.model.eatery.Tag;

/**
 * A utility class for Eatery.
 */
public class EateryUtil {

    /**
     * Returns an add command string for adding the {@code eatery}.
     */
    public static String getAddCommand(Eatery eatery) {
        return AddCommand.COMMAND_WORD + " " + getEateryDetails(eatery);
    }

    /**
     * Returns the part of command string for the given {@code eatery}'s details.
     */
    public static String getEateryDetails(Eatery eatery) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + eatery.getName().fullName + " ");
        sb.append(PREFIX_ADDRESS + eatery.getAddress().value + " ");
        sb.append(PREFIX_CATEGORY + eatery.getCategory().getName() + " ");
        eatery.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.getName() + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditEateryDescriptor}'s details.
     */
    public static String getEditEateryDescriptorDetails(EditEateryDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.getName()).append(" "));
            }
        }
        return sb.toString();
    }
}
