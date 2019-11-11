package seedu.address.diaryfeature.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.diaryfeature.model.DiaryModel;
import seedu.address.diaryfeature.model.diaryEntry.DiaryEntry;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Sets a memory in the diary entry to private
 */
public class PrivateCommand extends Command<DiaryModel> {
    public static final String COMMAND_WORD = "private";
    private static final String OVERFLOW = "For the Private command, your index has to be less than the size" +
            " of the list! Make your number smaller.";
    private static final String MESSAGE_PRIVATE_ENTRY_SUCCESS = "Your Entry has become private. No one can see it";
    private final Index targetIndex;

    /**
     * Generates a private command which hides the memory of the specified entry
     *
     * @param targetIndex Entry to set Private
     */
    public PrivateCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Executes the command by setting the entry to private and
     *
     * @param model on which the command is executes
     * @return {@code CommandResult} a readable form of the entries
     */
    @Override
    public CommandResult execute(DiaryModel model) throws CommandException {
        requireNonNull(model);
        List<DiaryEntry> lastShownList = model.getFilteredDiaryEntryList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(OVERFLOW);
        }
        DiaryEntry privateEntry = lastShownList.get(targetIndex.getZeroBased());
        model.setDiaryEntryPrivate(privateEntry);
        return new CommandResult((MESSAGE_PRIVATE_ENTRY_SUCCESS));
    }
}


