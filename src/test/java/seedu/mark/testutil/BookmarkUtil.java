package seedu.mark.testutil;

import static seedu.mark.logic.parser.CliSyntax.PREFIX_FOLDER;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_URL;

import java.util.Set;

import seedu.mark.logic.commands.AddCommand;
import seedu.mark.logic.commands.EditCommand;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.tag.Tag;

/**
 * A utility class for Bookmark.
 */
public class BookmarkUtil {

    /**
     * Returns an add command string for adding the {@code bookmark}.
     */
    public static String getAddCommand(Bookmark bookmark) {
        return AddCommand.COMMAND_WORD + " " + getBookmarkDetails(bookmark);
    }

    /**
     * Returns the part of command string for the given {@code bookmark}'s details.
     */
    public static String getBookmarkDetails(Bookmark bookmark) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + bookmark.getName().fullName + " ");
        sb.append(PREFIX_URL + bookmark.getUrl().value + " ");
        sb.append(PREFIX_REMARK + bookmark.getRemark().value + " ");
        sb.append(PREFIX_FOLDER + bookmark.getFolder().folderName + " ");
        bookmark.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditBookmarkDescriptor}'s details.
     */
    public static String getEditBookmarkDescriptorDetails(EditCommand.EditBookmarkDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getUrl().ifPresent(url -> sb.append(PREFIX_URL).append(url.value).append(" "));
        descriptor.getRemark().ifPresent(remark -> sb.append(PREFIX_REMARK).append(remark.value).append(" "));
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
