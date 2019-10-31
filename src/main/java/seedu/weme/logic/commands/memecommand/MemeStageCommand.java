package seedu.weme.logic.commands.memecommand;

import static java.util.Objects.requireNonNull;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX;

import java.util.List;

import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;
import seedu.weme.model.meme.Meme;


/**
 * Stage Command.
 */
public class MemeStageCommand extends Command {

    public static final String COMMAND_WORD = "stage";

    public static final String MESSAGE_DESCRIPTION = COMMAND_WORD + ": stages a meme.";
    public static final String MESSAGE_USAGE = MESSAGE_DESCRIPTION
            + " Parameters: "
            + "Parameters: INDEX (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SUCCESS = "New meme staged: %1$s";
    public static final String MESSAGE_MEME_ALREADY_STAGED = "Meme is already in staging area";

    private final Index targetIndex;

    /**
     * Creates an MemeStageCommand to add the specified {@code Meme} to the staging area.
     */
    public MemeStageCommand(Index index) {
        requireNonNull(index);
        targetIndex = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Meme> lastShownList = model.getFilteredMemeList();
        List<Meme> stageList = model.getFilteredStagedMemeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
        }

        Meme memeToStage = lastShownList.get(targetIndex.getZeroBased());
        if (stageList.contains(memeToStage)) {
            throw new CommandException(MESSAGE_MEME_ALREADY_STAGED);
        }
        model.stageMeme(memeToStage);

        return new CommandResult(String.format(MESSAGE_SUCCESS, memeToStage));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MemeStageCommand // instanceof handles nulls
                && targetIndex.equals(((MemeStageCommand) other).targetIndex));
    }
}
