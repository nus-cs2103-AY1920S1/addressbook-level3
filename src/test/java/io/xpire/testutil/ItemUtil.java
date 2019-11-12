package io.xpire.testutil;

import io.xpire.logic.commands.AddCommand;
import io.xpire.model.item.XpireItem;

/**
 * A utility class for XpireItem.
 */
public class ItemUtil {

    /**
     * Returns an add command string for adding the {@code xpireItem}.
     */
    public static String getAddCommand(XpireItem xpireItem) {
        return AddCommand.COMMAND_WORD + "|" + getItemDetails(xpireItem);
    }

    /**
     * Returns the part of command string for the given {@code xpireItem}'s details.
     */
    public static String getItemDetails(XpireItem xpireItem) {
        StringBuilder sb = new StringBuilder("");
        sb.append(xpireItem.getName().toString() + "|");
        sb.append(xpireItem.getExpiryDate().toString() + "|");
        sb.append(xpireItem.getQuantity().toString());
        if (!xpireItem.getTags().isEmpty()) {
            sb.append("|");
            xpireItem.getTags().stream().forEach(s -> sb.append(s.getTagName() + " "));
        }
        return sb.toString();
    }


}
