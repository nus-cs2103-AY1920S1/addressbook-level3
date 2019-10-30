package seedu.elisa.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.elisa.model.ItemModel;

/**
 * Creates a command to close the expanded view of an item.
 */
public class CloseCommand extends Command {
    public static final String COMMAND_WORD = "close";
    public static final String MESSAGE_SUCCESS = "I've closed it";
    public static final String MESSAGE_FAILURE = "There's nothing to close";

    /**
     * Carries out the operations of this close command on the given model.
     * @param model {@code Model} which the command should operate on.
     * @return the result of executing this command.
     */
    public CloseCommandResult execute(ItemModel model) {
        requireNonNull(model);
        return new CloseCommandResult(MESSAGE_SUCCESS);
    }
}
