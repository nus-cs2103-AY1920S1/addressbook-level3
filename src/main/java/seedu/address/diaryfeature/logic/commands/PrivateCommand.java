package seedu.address.diaryfeature.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.diaryfeature.model.DiaryModel;
import seedu.address.diaryfeature.model.diaryEntry.DiaryEntry;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

public class PrivateCommand extends Command<DiaryModel> {
    public static final String COMMAND_WORD = "private";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the diary entry identified by the index number used in the displayed diary entry list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_PRIVATE_ENTRY_SUCCESS = "Entry is now private";

    private final Index targetIndex;

    public PrivateCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(DiaryModel model) throws CommandException {
        requireNonNull(model);
        List<DiaryEntry> lastShownList = model.getFilteredDiaryEntryList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        model.setDiaryEntryPrivate(targetIndex.getOneBased());
        model.updateFilteredDiaryList(model.PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(MESSAGE_PRIVATE_ENTRY_SUCCESS + targetIndex.getOneBased());
    }
}


