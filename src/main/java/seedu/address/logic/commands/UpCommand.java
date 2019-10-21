package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ItemModel;

/**
 * Command for scrolling up.
 */
public class UpCommand extends Command {

    public static final String COMMAND_WORD = "up";
    public static final String MESSAGE_SUCCESS = "Scrolling up...";
    public static final String MESSAGE_USAGE = "Message Usage";

    private String pane;

    public UpCommand(String input) {
        switch(input.trim()) {
        case "L":
            this.pane = "resultDisplay";
            break;
        case "R":
            this.pane = "tabPane";
            break;
        default:
            this.pane = "Illegal";
        }
    }

    @Override
    public CommandResult execute(ItemModel model) throws CommandException {
        requireNonNull(model);
        if (pane.equals("Illegal")) {
            throw new CommandException(MESSAGE_USAGE);
        }
        return new UpCommandResult(MESSAGE_SUCCESS, pane);
    }
}
