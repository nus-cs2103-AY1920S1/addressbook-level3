package seedu.address.logic.commands.diary;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.diary.DiaryEntry;
import seedu.address.model.diary.EditDiaryEntryDescriptor;

/**
 * {@link Command} opens the edit box with the current diary entry text, if any.
 */
public class EditDiaryEntryCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the current diary entry\n";

    public static final String MESSAGE_NO_DIARY_ENTRY = "You are not currently viewing any entry!\n";

    public static final String MESSAGE_EDIT_SUCCESS = "Brought up the edit window, go ahead and type!";

    public static final String MESSAGE_ALREADY_EDITING = "The edit window is already shown!";

    public EditDiaryEntryCommand() { }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        DiaryEntry diaryEntry = model.getPageStatus().getDiaryEntry();
        EditDiaryEntryDescriptor currentEditEntryDescriptor = model.getPageStatus().getEditDiaryEntryDescriptor();

        if (diaryEntry == null) {
            throw new CommandException(MESSAGE_NO_DIARY_ENTRY);
        }

        EditDiaryEntryDescriptor editDescriptor;

        if (currentEditEntryDescriptor == null) {
            editDescriptor = new EditDiaryEntryDescriptor(diaryEntry);
            model.setPageStatus(model.getPageStatus()
                    .withNewEditDiaryEntryDescriptor(editDescriptor));

            return new CommandResult(MESSAGE_EDIT_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_ALREADY_EDITING);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj
                || obj instanceof EditDiaryEntryCommand;
    }
}
