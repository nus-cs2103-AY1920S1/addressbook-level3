package seedu.address.logic.commands.cli;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDY_PLANS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Reverts the {@code model}'s address book to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Redoing the previous undone command";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Redoes the previous undone command.\n";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.canRedoModulePlanner()) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        model.redoModulePlanner();
        model.updateFilteredStudyPlanList(PREDICATE_SHOW_ALL_STUDY_PLANS);
        return new CommandResult(MESSAGE_SUCCESS, true, false);
    }
}
