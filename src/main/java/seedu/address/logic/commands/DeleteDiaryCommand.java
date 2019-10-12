package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.diary.Diary;

/**
 * Deletes a diary identified using it's displayed index from Duke Cooks.
 */
public class DeleteDiaryCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the diary identified by the index number used in the displayed diary list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_DIARY_SUCCESS = "Deleted Diary: %1$s";

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
