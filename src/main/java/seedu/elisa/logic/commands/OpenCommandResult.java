package seedu.elisa.logic.commands;

import seedu.elisa.commons.core.item.Item;

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
