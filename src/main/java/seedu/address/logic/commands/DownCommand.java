package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ItemModel;

import static java.util.Objects.requireNonNull;

public class DownCommand extends Command {

    public static final String COMMAND_WORD = "down";
    public static final String MESSAGE_SUCCESS = "Scrolling down...";
    public static final String MESSAGE_USAGE = "Message Usage";

    private String pane;

    public DownCommand(String input) {
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
        return new DownCommandResult(MESSAGE_SUCCESS, pane);
    }
}
