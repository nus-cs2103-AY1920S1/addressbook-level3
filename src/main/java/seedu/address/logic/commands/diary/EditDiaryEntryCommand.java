package seedu.address.logic.commands.diary;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.diary.DiaryEntry;
import seedu.address.model.diary.EditDiaryEntryDescriptor;
import seedu.address.model.diary.photo.Photo;

public class EditDiaryEntryCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the current diary entry\n";

    public static final String MESSAGE_NO_DIARY_ENTRY = "You are not currently viewing any entry!\n";

    public static final String MESSAGE_EDIT_SUCCESS = "Brought up the edit window, go ahead and type!";

    private String newText;

    public EditDiaryEntryCommand(String newText) {
        requireNonNull(newText);
        this.newText = newText;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Optional<DiaryEntry> diaryEntry = Optional.ofNullable(model.getPageStatus().getDiaryEntry());

        if (!diaryEntry.isPresent()) {
            throw new CommandException(MESSAGE_NO_DIARY_ENTRY);
        }

        EditDiaryEntryDescriptor editDescriptor = model.getPageStatus().getEditDiaryEntryDescriptor() == null
                ? new EditDiaryEntryDescriptor(diaryEntry.get())
                : model.getPageStatus().getEditDiaryEntryDescriptor();



        model.setPageStatus(model.getPageStatus()
                .withNewEditDiaryEntryDescriptor(editDescriptor));

        return new CommandResult(MESSAGE_EDIT_SUCCESS);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj
                || obj instanceof EditDiaryEntryCommand;
    }
}
