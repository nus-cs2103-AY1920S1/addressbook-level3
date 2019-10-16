package seedu.address.logic.commands;

import seedu.address.model.ItemModel;
import seedu.address.model.exceptions.IllegalListException;

/**
 * Toggle the state of ELISA between priority and non-priority mode.
 */
public class PriorityCommand extends Command {
    public static final String COMMAND_WORD = "priority";

    private static final String PRIORITY_MODE_ON = "Priority mode activated";
    private static final String PRIORITY_MODE_OFF = "Priority mode deactivated";
    private static final String PRIORITY_MODE_ERROR = "Priority mode can only be activated on task pane";

    @Override
    public CommandResult execute(ItemModel model) {
        try {
            boolean status = model.togglePriorityMode();
            return new CommandResult((status ? PRIORITY_MODE_ON : PRIORITY_MODE_OFF));
        } catch (IllegalListException e) {
            return new CommandResult(PRIORITY_MODE_ERROR);
        }
    }
}
