package seedu.address.diaryfeature.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.diaryfeature.model.DiaryModel;
import seedu.address.diaryfeature.model.diaryEntry.DiaryEntry;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

public class UnPrivateCommand extends Command<DiaryModel> {
    public static final String COMMAND_WORD = "unprivate";
    private static final String OVERFLOW = "For the unprivate command, your index has to be less than the size" +
            " of the list! Make your number smaller.";
    private static final String MESSAGE_UNPRIVATE_ENTRY_SUCCESS = "Your Entry has become unprivate. Everyone can see it";
    private static final String MESSAGE_HAS_DETAILS = "There is password protection!\n"
            +"Use the unlock command";
    private final Index targetIndex;

    /**
     * Generates an UnPrivateCommand to show the memory
     * @param targetIndex is the index to be unprivated
     */
    public UnPrivateCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Executes the command, if applicable, and sets the entry to unprivate
     * @param model on which the command is executes
     * @return {@code CommandResult} a readable message to show the result
     */
    @Override
    public CommandResult execute(DiaryModel model) throws CommandException {
        requireNonNull(model);
        List<DiaryEntry> lastShownList = model.getFilteredDiaryEntryList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(OVERFLOW);
        }
        if (model.hasPassword()) {
            return new CommandResult(MESSAGE_HAS_DETAILS);
        } else {
            DiaryEntry unprivate = lastShownList.get(targetIndex.getZeroBased());
            model.setDiaryEntryUnPrivate(unprivate);
            return new CommandResult(MESSAGE_UNPRIVATE_ENTRY_SUCCESS);

        }
    }
}

