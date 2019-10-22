package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ItemModel;

/**
 * Command for scrolling up.
 */
public class UpCommand extends ScrollCommand {

    public static final String COMMAND_WORD = "up";
    public static final String MESSAGE_SUCCESS = "Scrolling up...";
    public static final String MESSAGE_USAGE = "up L / up R";

    public UpCommand(String input) {
        super(input);
    }

    @Override
    public CommandResult execute(ItemModel model) throws CommandException {
        requireNonNull(model);
        if (pane.equals("Illegal")) {
            throw new CommandException(MESSAGE_USAGE);
        }
        return new UpCommandResult(MESSAGE_SUCCESS, pane);
    }

    public String getCommandWord() {
        return this.COMMAND_WORD;
    }
}
