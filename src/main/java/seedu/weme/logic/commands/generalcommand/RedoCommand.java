package seedu.weme.logic.commands.generalcommand;

import static java.util.Objects.requireNonNull;

import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;

/**
 * Reverts the {@code model}'s Weme to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_SUCCESS = "Redid the following command:\n%s";
    public static final String MESSAGE_FAILURE = "No commands to redo.";
    public static final String MESSAGE_DESCRIPTION = COMMAND_WORD + ": redo the previous undone command.";
    public static final String MESSAGE_USAGE = MESSAGE_DESCRIPTION;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoWeme()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        String feedback = model.redoWeme();
        return new CommandResult(String.format(MESSAGE_SUCCESS, feedback));
    }
}
