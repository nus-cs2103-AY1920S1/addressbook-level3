package seedu.planner.testutil.contact;

import static seedu.planner.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.planner.logic.commands.AddContactCommand;
import seedu.planner.logic.commands.EditContactCommand.EditContactDescriptor;
import seedu.planner.model.contact.Contact;
import seedu.planner.model.tag.Tag;

/**
 * A utility class for Contact.
 */
public class ContactUtil {

    /**
     * Returns an add command string for adding the {@code contacts}.
     */
    public static String getAddCommand(Contact contact) {
        return AddContactCommand.COMMAND_WORD + " " + AddContactCommand.SECOND_COMMAND_WORD + " "
                + getContactDetails(contact);
    }

    /**
     * Returns the part of command string for the given {@code contacts}'s details.
     */
    public static String getContactDetails(Contact contact) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + contact.getName().toString() + " ");
        sb.append(PREFIX_PHONE + contact.getPhone().value + " ");

        if (contact.getEmail().isPresent()) {
            sb.append(PREFIX_EMAIL + contact.getEmail().get().value + " ");
        } else {
            sb.append(PREFIX_EMAIL + " ");
        }

        if (contact.getAddress().isPresent()) {
            sb.append(PREFIX_ADDRESS + contact.getAddress().get().value + " ");
        } else {
            sb.append(PREFIX_ADDRESS + " ");
        }

        contact.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditContactDescriptorDetails(EditContactDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.toString()).append(" "));
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
