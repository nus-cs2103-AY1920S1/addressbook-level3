package seedu.address.logic.commands.inventory;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * This class's execute function is called whenever an item is deleted from the inventory list
 */
public class CheckInventoryCommand extends Command {

    public static final String COMMAND_WORD = "check";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Checks a thing as done in the inventory list.";

    public static final String MESSAGE_SUCCESS = "The thing has been marked as done in the inventory list";

    private Index index;

    public CheckInventoryCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);

        model.getPageStatus().getTrip().getInventoryList().markDone(index);

        return new CommandResult(MESSAGE_SUCCESS, false);
    }
}
