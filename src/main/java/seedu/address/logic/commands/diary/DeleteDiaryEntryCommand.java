package seedu.address.logic.commands.diary;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.diary.DiaryEntry;
import seedu.address.model.diary.EditDiaryEntryDescriptor;

/**
 * {@link Command} that deletes a line of text in the diary entry by a provided {@link Index},
 * and commits the change to the current diary entry.
 */
public class DeleteDiaryEntryCommand extends Command {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a line of text and commits to the "
            + "current diary entry\n"
            + "Parameters: One based index (must be a valid positive integer)";

    public static final String MESSAGE_NO_DIARY_ENTRY = "You are not currently viewing any entry!\n";

    public static final String MESSAGE_DELETE_SUCCESS = "Deleted line %d! %1$d";

    private Index lineIndexToDelete;

    public DeleteDiaryEntryCommand(Index lineIndexToDelete) {
        requireNonNull(lineIndexToDelete);
        this.lineIndexToDelete = lineIndexToDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        DiaryEntry diaryEntry = model.getPageStatus().getDiaryEntry();
        EditDiaryEntryDescriptor currentEditEntryDescriptor = model.getPageStatus().getEditDiaryEntryDescriptor();

        if (diaryEntry == null) {
            throw new CommandException(MESSAGE_NO_DIARY_ENTRY);
        }

        EditDiaryEntryDescriptor editDescriptor = currentEditEntryDescriptor == null
                ? new EditDiaryEntryDescriptor(diaryEntry)
                : currentEditEntryDescriptor;

        editDescriptor.deleteTextParagraph(this.lineIndexToDelete.getZeroBased());
        DiaryEntry newDiaryEntry = editDescriptor.buildDiaryEntry();

        model.getPageStatus().getTrip().getDiary().setDiaryEntry(diaryEntry, newDiaryEntry);
        model.setPageStatus(model.getPageStatus()
                .withNewEditDiaryEntryDescriptor(null)
                .withNewDiaryEntry(newDiaryEntry));

        return new CommandResult(String.format(MESSAGE_DELETE_SUCCESS, this.lineIndexToDelete.getOneBased()));
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj
                || (obj instanceof DeleteDiaryEntryCommand
                && ((DeleteDiaryEntryCommand) obj).lineIndexToDelete.equals(lineIndexToDelete));
    }
}
