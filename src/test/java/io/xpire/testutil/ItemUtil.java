package io.xpire.testutil;

import static io.xpire.logic.parser.CliSyntax.PREFIX_EXPIRY_DATE;
import static io.xpire.logic.parser.CliSyntax.PREFIX_NAME;
import static io.xpire.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import io.xpire.logic.commands.AddCommand;
import io.xpire.logic.commands.EditCommand.EditItemDescriptor;
import io.xpire.model.item.Item;
import io.xpire.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class ItemUtil {

    /**
     * Returns an add command string for adding the {@code item}.
     */
    public static String getAddCommand(Item item) {
        return AddCommand.COMMAND_WORD + "|" + getItemDetails(item);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getItemDetails(Item item) {
        StringBuilder sb = new StringBuilder("");
        sb.append(item.getName().toString() + "|");
        sb.append(item.getExpiryDate().toString() + "|");
        sb.append(item.getQuantity().toString());
        if (!item.getTags().isEmpty()) {
            sb.append("|");
            item.getTags().stream().forEach(s -> sb.append(s.getTagName() + " "));
        }
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditItemDescriptor}'s details.
     */
    public static String getEditItemDescriptorDetails(EditItemDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.toString()).append(" "));
        descriptor.getExpiryDate().ifPresent(ed -> sb.append(PREFIX_EXPIRY_DATE).append(ed.toString()).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.getTagName()).append(" "));
            }
        }
        return sb.toString();
    }
}
