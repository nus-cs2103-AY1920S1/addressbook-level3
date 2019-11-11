package seedu.weme.logic.commands.memecommand;

import static java.util.Objects.requireNonNull;
import static seedu.weme.model.ModelContext.CONTEXT_VIEW;

import java.util.List;

import seedu.weme.commons.core.Messages;
import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;
import seedu.weme.model.meme.Meme;

/**
 * Views a meme identified using its displayed index from Weme.
 */
public class MemeViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_DESCRIPTION = COMMAND_WORD
            + ": views the meme identified by the index number used in the displayed meme list.";

    public static final String MESSAGE_USAGE = MESSAGE_DESCRIPTION
            + "\nParameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VIEW_MEME_SUCCESS = "Viewing meme: %1$s";

    private final Index targetIndex;

    public MemeViewCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Meme> lastShownList = model.getFilteredMemeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
        }

        Meme memeToView = lastShownList.get(targetIndex.getZeroBased());
        model.setViewableMeme(memeToView);

        model.setContext(CONTEXT_VIEW);

        return new CommandResult(String.format(MESSAGE_VIEW_MEME_SUCCESS, memeToView));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MemeViewCommand // instanceof handles nulls
                && targetIndex.equals(((MemeViewCommand) other).targetIndex)); // state check
    }

}
