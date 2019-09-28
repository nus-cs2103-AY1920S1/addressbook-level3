package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditMemeDescriptor;
import seedu.address.model.meme.Meme;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Meme.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code meme}.
     */
    public static String getAddCommand(Meme meme) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(meme);
    }

    /**
     * Returns the part of command string for the given {@code meme}'s details.
     */
    public static String getPersonDetails(Meme meme) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + meme.getName().fullName + " ");
        sb.append(PREFIX_PHONE + meme.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + meme.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + meme.getAddress().value + " ");
        meme.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditMemeDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditMemeDescriptor descriptor) {
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
