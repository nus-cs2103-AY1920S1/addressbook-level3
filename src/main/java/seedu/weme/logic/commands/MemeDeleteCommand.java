package seedu.weme.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.weme.commons.core.Messages;
import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;
import seedu.weme.model.meme.Meme;

/**
 * Deletes a meme identified using it's displayed index from the meme book.
 */
public class MemeDeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the meme identified by the index number used in the displayed meme list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_MEME_SUCCESS = "Deleted Meme: %1$s";

    private final Index targetIndex;

    public MemeDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Meme> lastShownList = model.getFilteredMemeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
        }

        Meme memeToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteMeme(memeToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_MEME_SUCCESS, memeToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MemeDeleteCommand // instanceof handles nulls
                && targetIndex.equals(((MemeDeleteCommand) other).targetIndex)); // state check
    }
}
