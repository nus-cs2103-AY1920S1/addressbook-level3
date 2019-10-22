package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ItemModel;

/**
 * Command for scrolling down.
 */
public class DownCommand extends ScrollCommand {

    public final String COMMAND_WORD = "down";
    public final String MESSAGE_SUCCESS = "Scrolling down...";
    public final String MESSAGE_USAGE = "down L / down R";
    
    public DownCommand(String input) {
        super(input);
    }

    @Override
    public CommandResult execute(ItemModel model) throws CommandException {
        requireNonNull(model);
        if (pane.equals("Illegal")) {
            throw new CommandException(MESSAGE_USAGE);
        }
        return new DownCommandResult(MESSAGE_SUCCESS, pane);
    }
    
    public String getCommandWord() {
        return this.COMMAND_WORD;
    }
}
