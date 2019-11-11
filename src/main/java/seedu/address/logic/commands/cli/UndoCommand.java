package seedu.address.logic.commands.cli;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Reverts the {@code model}'s address book to its previous state.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Undoing the previous command";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undoes the previous command.\n";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoModulePlanner()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoModulePlanner();
        model.updateFilteredStudyPlanList(Model.PREDICATE_SHOW_ALL_STUDY_PLANS);
        return new CommandResult(MESSAGE_SUCCESS, true, false);
    }
}
