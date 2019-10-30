package dukecooks.logic.commands.diary;

import static java.util.Objects.requireNonNull;

import java.util.List;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.DeleteCommand;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Model;
import dukecooks.model.diary.components.Diary;

/**
 * Deletes a diary identified using it's displayed index from Duke Cooks.
 */
public class DeleteDiaryCommand extends DeleteCommand {

    public static final String VARIANT_WORD = "diary";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the diary identified by the index number used in the displayed diary list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_DIARY_SUCCESS = "You have deleted diary with name: %1$s";

    private final Index targetIndex;

    public DeleteDiaryCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Diary> lastShownList = model.getFilteredDiaryList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DIARY_DISPLAYED_INDEX);
        }

        Diary diaryToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteDiary(diaryToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_DIARY_SUCCESS, diaryToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteDiaryCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteDiaryCommand) other).targetIndex)); // state check
    }
}
