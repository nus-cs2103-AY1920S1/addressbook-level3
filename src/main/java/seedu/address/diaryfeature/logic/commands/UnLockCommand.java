package seedu.address.diaryfeature.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.diaryfeature.model.DiaryModel;
import seedu.address.diaryfeature.model.details.Details;
import seedu.address.diaryfeature.model.diaryEntry.DiaryEntry;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Unlocks a memory, if the accompaying details match the current details
 */
public class UnLockCommand extends Command<DiaryModel> {
    public static final String COMMAND_WORD = "unlock";
    private static final String OVERFLOW = "For the unlock command, your index has to be less than the size" +
            " of the list! Make your number smaller.";
    private static final String MESSAGE_UNLOCK_ENTRY_SUCCESS = "Your Entry is now unlocked. Everyone can see it";
    private static final String MESSAGE_UNLOCK_ENTRY_FAILURE = "Password is wrong!";
    private static final String MESSAGE_NO_DETAILS = "There are no details set! \n Use" +
            "the unprivate command";

    private final Index targetIndex;
    private final Details detail;

    /**
     * Generates an UnLockCommand to show the memory
     *
     * @param targetIndex is the index to be unlocked
     * @param detail      is the accompanying username and password to allow access
     */
    public UnLockCommand(Index targetIndex, Details detail) {

        this.targetIndex = targetIndex;
        this.detail = detail;
    }

    /**
     * Executes the command, if applicable, and sets the entry to unprivate
     *
     * @param model on which the command is executes
     * @return {@code CommandResult} a readable message to show the result
     */
    @Override
    public CommandResult execute(DiaryModel model) throws CommandException {
        requireNonNull(model);
        List<DiaryEntry> lastShownList = model.getFilteredDiaryEntryList();
        //First check if the index is out of bounds
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(OVERFLOW);
        }
        //Next, if the model has no password, just use unprivate, cant use unlock
        if (!model.hasDetails()) {
            return new CommandResult(MESSAGE_NO_DETAILS);
            //Next, if the details match the stored ones, then you can show the memory
        } else if (model.checkDetails(detail)) {
            DiaryEntry unlock = lastShownList.get(targetIndex.getZeroBased());
            model.setDiaryEntryUnPrivate(unlock);
            return new CommandResult(String.format(MESSAGE_UNLOCK_ENTRY_SUCCESS));
            //If the details don't match, don't show the memory
        } else {
            return new CommandResult(MESSAGE_UNLOCK_ENTRY_FAILURE);

        }
    }
}

