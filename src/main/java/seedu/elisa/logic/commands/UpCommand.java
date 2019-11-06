package seedu.elisa.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.elisa.logic.commands.exceptions.CommandException;
import seedu.elisa.model.ItemModel;

/**
 * Command for scrolling up.
 */
public class UpCommand extends ScrollCommand {

    public static final String COMMAND_WORD = "up";
    public static final String MESSAGE_SUCCESS = "Scrolling up...";
    public static final String MESSAGE_USAGE = "up L / up R";

    public UpCommand() {
        super();
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
