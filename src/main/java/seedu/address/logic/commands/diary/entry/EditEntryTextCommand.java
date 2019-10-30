package seedu.address.logic.commands.diary.entry;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.AppUtil.isBothNullOrEqual;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.diary.DiaryEntry;
import seedu.address.model.diary.EditDiaryEntryDescriptor;

/**
 * {@link Command} that sets the text of the current diary entry, optionally allowing editing a specific line.
 * If a specific line is ap
 */
public class EditEntryTextCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the current diary entry\n"
            + "Parameters: "
            + "[Optional: " + PREFIX_INDEX + " specific line number to edit.]\n"
            + "[Required: " + PREFIX_DESCRIPTION
            + "New Text of the specified line or entire diary entry which is either:\n"
            + "Text lines(s) consisting of normal text"
            + "OR Text lines with '<images (positive integer of a "
            + "photo according to the gallery order)> (one per line)'\n"
            + "OR Text lines with '<images (positive integers of photos according to"
            + " the gallery order separated by spaces) (one per line)>']";

    public static final String MESSAGE_NO_DIARY_ENTRY = "You are not currently viewing any entry!\n";

    private static final String MESSAGE_EDIT_SUCCESS = "Remember to execute the done command to save your changes!";

    private static final String MESSAGE_EDIT_COMMITTED = "Saved your edits! %1$s";

    private static final String MESSAGE_LINE_DOES_NOT_EXIST = "The line specified at %d does not exist!";

    private final String newText;
    private final Index lineToEdit;

    public EditEntryTextCommand(String newText) {
        requireNonNull(newText);
        this.newText = newText;
        this.lineToEdit = null;
    }

    public EditEntryTextCommand(String newText, Index lineToEdit) {
        requireAllNonNull(newText, lineToEdit);
        this.newText = newText;
        this.lineToEdit = lineToEdit;
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
            //There is no buffer being used, and the command should commit the change immediately
            editDescriptor = new EditDiaryEntryDescriptor(diaryEntry);
            boolean didEditSucceed = handleEditMode(editDescriptor);

            DiaryEntry editedDiaryEntry = editDescriptor.buildDiaryEntry();
            model.getPageStatus().getCurrentTripDiary().setDiaryEntry(diaryEntry, editedDiaryEntry);
            model.setPageStatus(model.getPageStatus()
                    .withNewDiaryEntry(editedDiaryEntry));

            return didEditSucceed
                    ? new CommandResult(String.format(MESSAGE_EDIT_COMMITTED, newText))
                    : new CommandResult(String.format(MESSAGE_LINE_DOES_NOT_EXIST, lineToEdit.getOneBased()));
        } else {
            //There is an edit buffer being used
            boolean didEditSucceed = handleEditMode(editDescriptor);

            return didEditSucceed
                    ? new CommandResult(MESSAGE_EDIT_SUCCESS)
                    : new CommandResult(String.format(MESSAGE_LINE_DOES_NOT_EXIST, lineToEdit.getOneBased()));
        }
    }

    /**
     * Runs the {@code editTextLine} or {@code setDiaryText} method on the specified {@link EditDiaryEntryDescriptor}
     * based on whether a {@code lineToEdit} was specified.
     *
     * @param editDescriptor {@link EditDiaryEntryDescriptor} to operate on.
     * @return False if the {@code lineToEdit} was specified but the line did not exist.
     */
    private boolean handleEditMode(EditDiaryEntryDescriptor editDescriptor) {
        if (lineToEdit == null) {
            editDescriptor.setDiaryText(newText);
            return true;
        } else {
            return editDescriptor.editTextLine(newText, lineToEdit);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof EditEntryTextCommand)) {
            return false;
        }

        EditEntryTextCommand otherCommand = (EditEntryTextCommand) obj;
        return newText.equals(otherCommand.newText)
                && isBothNullOrEqual(lineToEdit, otherCommand.lineToEdit);
    }
}
