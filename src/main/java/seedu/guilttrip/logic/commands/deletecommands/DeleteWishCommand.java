package seedu.guilttrip.logic.commands.deletecommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.guilttrip.commons.core.Messages;
import seedu.guilttrip.commons.core.index.Index;
import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.entry.Wish;

/**
 * Deletes a wish identified using it's displayed index from GuiltTrip.
 */
public class DeleteWishCommand extends Command {

    public static final String COMMAND_WORD = "deleteWish";

    public static final String ONE_LINER_DESC = COMMAND_WORD
            + ": Deletes the wish identified by the index number used in the displayed wish list.\n";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ENTRY_SUCCESS = "Deleted Wish: %1$s";

    private final Index targetIndex;

    public DeleteWishCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Wish> lastShownList = model.getFilteredWishes();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
        }

        Wish entryToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteWish(entryToDelete);
        model.commitGuiltTrip();
        return new CommandResult(String.format(MESSAGE_DELETE_ENTRY_SUCCESS, entryToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteWishCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteWishCommand) other).targetIndex)); // state check
    }
}
