package seedu.planner.testutil.accommodation;

import static seedu.planner.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.planner.logic.commands.addcommand.AddAccommodationCommand;
import seedu.planner.logic.commands.editcommand.EditAccommodationCommand.EditAccommodationDescriptor;
import seedu.planner.model.accommodation.Accommodation;
import seedu.planner.model.tag.Tag;

/**
 * A utility class for Accommodation.
 */
public class AccommodationUtil {

    /**
     * Returns an add command string for adding the {@code contacts}.
     */
    public static String getAddAccommodationCommand(Accommodation accommodation) {
        return AddAccommodationCommand.COMMAND_WORD + " "
                + AddAccommodationCommand.SECOND_COMMAND_WORD + " "
                + getAccommodationDetails(accommodation);
    }

    /**
     * Returns the part of command string for the given {@code accommodation}'s details.
     */
    public static String getAccommodationDetails(Accommodation accommodation) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + accommodation.getName().toString() + " ");
        sb.append(PREFIX_ADDRESS + accommodation.getAddress().toString() + " ");
        accommodation.getContact().ifPresent(
            contact -> sb.append(PREFIX_PHONE + contact.getPhone().toString() + " ")
        );
        accommodation.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditAccommodationDescriptor}'s details.
     */
    public static String getEditAccommodationDescriptorDetails(EditAccommodationDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.toString()).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
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
