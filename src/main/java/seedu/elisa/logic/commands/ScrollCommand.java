package seedu.elisa.logic.commands;

import seedu.elisa.logic.commands.exceptions.CommandException;
import seedu.elisa.model.ItemModel;

/**
 * Abstract class for scrolling.
 */
public abstract class ScrollCommand extends Command {

    protected String pane;

    public ScrollCommand() {
        this.pane = "tabPane";
    }
    @Override
    public CommandResult execute(ItemModel model) throws CommandException {
        return new CommandResult("Scrolling");
    }

}
