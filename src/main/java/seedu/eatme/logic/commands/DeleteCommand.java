package seedu.eatme.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.eatme.commons.core.Messages;
import seedu.eatme.commons.core.index.Index;
import seedu.eatme.logic.commands.exceptions.CommandException;
import seedu.eatme.model.Model;
import seedu.eatme.model.eatery.Eatery;

/**
 * Deletes a eatery identified using it's displayed index from the eatery list.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the eatery identified by the index number used in the displayed eatery list.\n"
            + "Parameters: [index] (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_EATERY_SUCCESS = "Eatery successfully deleted: %s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Eatery> lastShownList = model.isMainMode() ? model.getFilteredEateryList() : model.getFilteredTodoList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EATERY_DISPLAYED_INDEX);
        }

        Eatery eateryToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteEatery(eateryToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_EATERY_SUCCESS, eateryToDelete.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
