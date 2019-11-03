package seedu.address.diaryfeature.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.diaryfeature.model.DiaryModel;
import seedu.address.diaryfeature.model.details.Details;
import seedu.address.diaryfeature.model.diaryEntry.DiaryEntry;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

public class UnLockCommand extends Command<DiaryModel> {
    public static final String COMMAND_WORD = "unlock";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the diary entry identified by the index number used in the displayed diary entry list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNPRIVATE_ENTRY_SUCCESS = "Entry is now unprivate";
    public static final String MESSAGE_UNPRIVATE_ENTRY_FAILURE = "Password is wrong!";
    public static final String MESSAGE_NO_DETAILS = "There are no details set! \n Use" +
            "the unprivate command";




    private final Index targetIndex;
    private final Details detail;

    public UnLockCommand(Index targetIndex, Details detail) {

        this.targetIndex = targetIndex;
        this.detail = detail;
    }

    @Override
    public CommandResult execute(DiaryModel model) throws CommandException {
        requireNonNull(model);
        List<DiaryEntry> lastShownList = model.getFilteredDiaryEntryList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        if(!model.hasPassword()) {
            return new CommandResult(MESSAGE_NO_DETAILS);
        } else if (model.checkDetails(detail)) {
            model.setDiaryEntryUnPrivate(targetIndex.getOneBased());
            return new CommandResult(MESSAGE_UNPRIVATE_ENTRY_SUCCESS + targetIndex.getOneBased());
        } else {
            return new CommandResult(MESSAGE_UNPRIVATE_ENTRY_FAILURE + targetIndex.getOneBased());

        }


    }
}

