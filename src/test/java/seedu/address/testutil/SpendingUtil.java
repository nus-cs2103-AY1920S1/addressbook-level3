package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditSpendingDescriptor;
import seedu.address.model.spending.Spending;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Spending.
 */
public class SpendingUtil {

    /**
     * Returns an add command string for adding the {@code Spending}.
     */
    public static String getAddCommand(Spending spending) {
        return AddCommand.COMMAND_WORD + " " + getSpendingDetails(spending);
    }

    /**
     * Returns the part of command string for the given {@code Spending}'s details.
     */
    public static String getSpendingDetails(Spending spending) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + spending.getName().fullName + " ");
        sb.append(PREFIX_PHONE + spending.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + spending.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + spending.getAddress().value + " ");
        spending.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditSpendingDescriptor}'s details.
     */
    public static String getEditSpendingDescriptorDetails(EditSpendingDescriptor descriptor) {
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
