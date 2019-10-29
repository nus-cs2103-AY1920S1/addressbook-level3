package seedu.weme.logic.commands.generalcommand;

import static java.util.Objects.requireNonNull;

import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;

/**
 * Reverts the {@code model}'s Weme to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Undid the following command:\n%s";
    public static final String MESSAGE_FAILURE = "No commands to undo.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoWeme()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        String feedback = model.undoWeme();
        return new CommandResult(String.format(MESSAGE_SUCCESS, feedback));
    }
}
