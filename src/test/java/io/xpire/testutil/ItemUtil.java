package io.xpire.testutil;

import io.xpire.logic.commands.AddCommand;
import io.xpire.model.item.Item;

/**
 * A utility class for Item.
 */
public class ItemUtil {

    /**
     * Returns an add command string for adding the {@code item}.
     */
    public static String getAddCommand(Item item) {
        return AddCommand.COMMAND_WORD + "|" + getItemDetails(item);
    }

    /**
     * Returns the part of command string for the given {@code item}'s details.
     */
    public static String getItemDetails(Item item) {
        StringBuilder sb = new StringBuilder("");
        sb.append(item.getName().toString() + "|");
        sb.append(item.getExpiryDate().toString());
        if (!item.getTags().isEmpty()) {
            sb.append("|");
            item.getTags().stream().forEach(s -> sb.append(s.getTagName() + " "));
        }
        return sb.toString();
    }


}
