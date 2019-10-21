package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ItemModel;

public abstract class ScrollCommand extends Command {

    public static final String COMMAND_WORD = "down";
    public static final String MESSAGE_SUCCESS = "Scrolling down...";
    public static final String MESSAGE_USAGE = "Message Usage";

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
