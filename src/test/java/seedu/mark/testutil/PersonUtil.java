package seedu.mark.testutil;

import static seedu.mark.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.mark.logic.commands.AddCommand;
import seedu.mark.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.tag.Tag;

/**
 * A utility class for Bookmark.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code bookmark}.
     */
    public static String getAddCommand(Bookmark bookmark) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(bookmark);
    }

    /**
     * Returns the part of command string for the given {@code bookmark}'s details.
     */
    public static String getPersonDetails(Bookmark bookmark) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + bookmark.getName().fullName + " ");
        sb.append(PREFIX_PHONE + bookmark.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + bookmark.getUrl().value + " ");
        sb.append(PREFIX_ADDRESS + bookmark.getAddress().value + " ");
        bookmark.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getUrl().ifPresent(url -> sb.append(PREFIX_EMAIL).append(url.value).append(" "));
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
