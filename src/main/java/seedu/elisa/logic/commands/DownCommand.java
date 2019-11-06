package seedu.elisa.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.elisa.logic.commands.exceptions.CommandException;
import seedu.elisa.model.ItemModel;

/**
 * Command for scrolling down.
 */
public class DownCommand extends ScrollCommand {

    public static final String COMMAND_WORD = "down";
    public static final String MESSAGE_SUCCESS = "Scrolling down...";
    public static final String MESSAGE_USAGE = "down L / down R";

    public DownCommand() {
        super();
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
