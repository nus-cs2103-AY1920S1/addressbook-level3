package seedu.mark.logic.commands;

import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.mark.model.Model.PREDICATE_SHOW_ALL_BOOKMARKS;

import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.model.Model;
import seedu.mark.storage.Storage;

/**
 * Reverts the {@code model}'s Mark to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undoes a given number of previous undoable commands. "
            + "If not specified, the default number of commands to undo is 1. \n"
            + "Parameters: [STEP] (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 2";

    public static final String MESSAGE_SUCCESS = "Action(s) successfully undone: \n%1$s";
    public static final String MESSAGE_FAILURE = "Not enough commands to undo! "
            + "Only %1$s remaining commands to undo.";

    private final int steps;

    public UndoCommand(int steps) {
        this.steps = steps;
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        requireAllNonNull(model, storage);

        if (!model.canUndoMark(steps)) {
            throw new CommandException(String.format(MESSAGE_FAILURE, model.getMaxStepsToUndo()));
        }

        String record = model.undoMark(steps);
        model.updateFilteredBookmarkList(PREDICATE_SHOW_ALL_BOOKMARKS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, record));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UndoCommand // instanceof handles nulls
                && steps == ((UndoCommand) other).steps); // state check
    }
}
