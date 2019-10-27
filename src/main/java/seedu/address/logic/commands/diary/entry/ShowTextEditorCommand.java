package seedu.address.logic.commands.diary.entry;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.diary.DiaryEntry;
import seedu.address.model.diary.EditDiaryEntryDescriptor;

/**
 * {@link Command} that opens the edit box with the current diary entry text, if any.
 */
public class ShowTextEditorCommand extends Command {

    public static final String COMMAND_WORD = "editor";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens the edit box for multiline text editing\n";

    private static final String MESSAGE_NO_DIARY_ENTRY = "You are not currently viewing any entry!\n";

    private static final String MESSAGE_OPEN_EDIT_SUCCESS = "Brought up the edit window, go ahead and type!";

    private static final String MESSAGE_ALREADY_EDITING = "The edit box is already shown!";

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

            model.setPageStatus(model.getPageStatus()
                    .withNewEditDiaryEntryDescriptor(editDescriptor));

            return new CommandResult(MESSAGE_OPEN_EDIT_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_ALREADY_EDITING);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj
                || obj instanceof ShowTextEditorCommand;
    }
}
