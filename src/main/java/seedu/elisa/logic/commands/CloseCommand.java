package seedu.elisa.logic.commands;

import seedu.elisa.model.ItemModel;
import static java.util.Objects.requireNonNull;

public class CloseCommand extends Command {
    public static final String COMMAND_WORD = "close";
    public static final String MESSAGE_SUCCESS = "I've closed it";
    public static final String MESSAGE_FAILURE = "There's nothing to close";

    public CloseCommandResult execute(ItemModel model) {
        requireNonNull(model);
        return CloseCommandResult.SUCCESS;
    }
}