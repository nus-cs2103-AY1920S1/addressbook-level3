package seedu.elisa.logic.commands;

import seedu.elisa.commons.core.item.Item;

/**
 * Creates a command result for open the view of a given item.
 */
public class OpenCommandResult extends CommandResult {

    private Item item;

    public OpenCommandResult(String feedbackToUser, Item item) {
        super(feedbackToUser);
        this.item = item;
    }

    public Item getItem() {
        return this.item;
    }
}
