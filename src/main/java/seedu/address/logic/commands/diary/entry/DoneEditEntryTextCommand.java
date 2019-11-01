package seedu.address.logic.commands.diary.entry;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.diary.DiaryEntry;
import seedu.address.model.diary.EditDiaryEntryDescriptor;

/**
 * {@link Command} that commits the data in the {@link EditDiaryEntryDescriptor} to the user diary entry.
 */
public class DoneEditEntryTextCommand extends Command {
    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Saves the edits to the current diary entry\n";

    private static final String MESSAGE_NO_DIARY_ENTRY = "You are not currently editing any entry!\n";

    private static final String MESSAGE_NO_DIARY_EDIT = "You haven't made any edits to this entry!\n";

    private static final String MESSAGE_EDIT_SUCCESS = "Saved your edit! %1$s";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        DiaryEntry diaryEntry = model.getPageStatus().getDiaryEntry();
        EditDiaryEntryDescriptor editDescriptor = model.getPageStatus().getEditDiaryEntryDescriptor();

        if (diaryEntry == null) {
            throw new CommandException(MESSAGE_NO_DIARY_ENTRY);
        }

        if (editDescriptor == null) {
            throw new CommandException(MESSAGE_NO_DIARY_EDIT);
        }

        DiaryEntry newDiaryEntry = editDescriptor.buildDiaryEntry();

        model.getPageStatus().getCurrentTripDiary().setDiaryEntry(diaryEntry, newDiaryEntry);

        model.setPageStatus(model.getPageStatus()
                .withNewEditDiaryEntryDescriptor(null)
                .withNewDiaryEntry(newDiaryEntry));

        return new CommandResult(String.format(MESSAGE_EDIT_SUCCESS, newDiaryEntry));
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof DoneEditEntryTextCommand;
    }
}
