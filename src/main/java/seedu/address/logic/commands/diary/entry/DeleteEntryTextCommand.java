package seedu.address.logic.commands.diary.entry;

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
 * and commits the change to the current diary entry immediately if there is no {@link EditDiaryEntryDescriptor}
 * currently in use.
 */
public class DeleteEntryTextCommand extends Command {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a line of text and commits to the "
            + "current diary entry\n"
            + "Parameters: One based index (must be a valid positive integer)";

    private static final String MESSAGE_NO_DIARY_ENTRY = "You are not currently viewing any entry!\n";

    private static final String MESSAGE_DELETE_SUCCESS = "Deleted line %d!";

    private static final String MESSAGE_LINE_DOES_NOT_EXIST = "The line specified at %d does not exist!";

    private final Index lineIndexToDelete;

    public DeleteEntryTextCommand(Index lineIndexToDelete) {
        requireNonNull(lineIndexToDelete);
        this.lineIndexToDelete = lineIndexToDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        DiaryEntry diaryEntry = model.getPageStatus().getDiaryEntry();
        EditDiaryEntryDescriptor editDescriptor = model.getPageStatus().getEditDiaryEntryDescriptor();

        if (diaryEntry == null) {
            throw new CommandException(MESSAGE_NO_DIARY_ENTRY);
        }

        if (editDescriptor == null) {
            editDescriptor = new EditDiaryEntryDescriptor(diaryEntry);
            boolean didDelete = editDescriptor.deleteTextParagraph(lineIndexToDelete);

            if (didDelete) {
                DiaryEntry newDiaryEntry = editDescriptor.buildDiaryEntry();

                model.getPageStatus().getCurrentTripDiary().setDiaryEntry(diaryEntry, newDiaryEntry);
                model.setPageStatus(model.getPageStatus()
                        .withNewEditDiaryEntryDescriptor(null)
                        .withNewDiaryEntry(newDiaryEntry));

                return new CommandResult(
                        String.format(MESSAGE_DELETE_SUCCESS, this.lineIndexToDelete.getOneBased()));
            } else {
                return new CommandResult(
                        String.format(MESSAGE_LINE_DOES_NOT_EXIST, this.lineIndexToDelete.getOneBased()));
            }
        } else {
            boolean didDelete = editDescriptor.deleteTextParagraph(lineIndexToDelete);

            return didDelete
                    ? new CommandResult(
                            String.format(MESSAGE_DELETE_SUCCESS, this.lineIndexToDelete.getOneBased()))
                    : new CommandResult(
                            String.format(MESSAGE_LINE_DOES_NOT_EXIST, this.lineIndexToDelete.getOneBased()));
        }
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj
                || (obj instanceof DeleteEntryTextCommand
                && ((DeleteEntryTextCommand) obj).lineIndexToDelete.equals(lineIndexToDelete));
    }
}
