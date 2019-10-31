package seedu.weme.logic.commands.exportcommand;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.weme.commons.core.Messages;
import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;
import seedu.weme.model.meme.Meme;

/**
 * Unstage Command.
 */
public class UnstageCommand extends Command {

    public static final String COMMAND_WORD = "unstage";

    public static final String MESSAGE_DESCRIPTION = COMMAND_WORD + ": unstages a meme.";
    public static final String MESSAGE_USAGE = MESSAGE_DESCRIPTION
            + " Parameters: "
            + "Parameters: INDEX (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SUCCESS = "Meme unstaged: %1$s";

    private final Index targetIndex;

    /**
     * Creates an UnstageCommand to add the specified {@code Meme}
     */
    public UnstageCommand(Index index) {
        requireNonNull(index);
        targetIndex = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Meme> stagedMemeList = model.getFilteredStagedMemeList();

        if (targetIndex.getZeroBased() >= stagedMemeList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
        }

        Meme memeToUnstage = stagedMemeList.get(targetIndex.getZeroBased());
        model.unstageMeme(memeToUnstage);
        return new CommandResult(String.format(MESSAGE_SUCCESS, memeToUnstage));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnstageCommand // instanceof handles nulls
                && targetIndex.equals(((UnstageCommand) other).targetIndex));
    }
}
