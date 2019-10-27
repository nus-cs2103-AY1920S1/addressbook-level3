package seedu.elisa.logic.commands;

import seedu.elisa.logic.commands.exceptions.CommandException;
import seedu.elisa.model.ItemModel;

/**
 * Abstract class for scrolling.
 */
public abstract class ScrollCommand extends Command {

    protected String pane;

    public ScrollCommand(String input) {
        switch(input.trim()) {
        case "L":
        case "l":
            this.pane = "resultDisplay";
            break;
        case "R":
        case "r":
            this.pane = "tabPane";
            break;
        default:
            this.pane = "Illegal";
        }
    }
    @Override
    public CommandResult execute(ItemModel model) throws CommandException {
        return new CommandResult("Scrolling");
    }

}
