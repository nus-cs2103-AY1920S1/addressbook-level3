package seedu.address.logic.commands.diary;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.diary.DiaryEntry;
import seedu.address.model.diary.EditDiaryEntryDescriptor;

public class DoneEditDiaryEntryCommand extends Command {
    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Saves the edits to the current diary entry\n";

    public static final String MESSAGE_NO_DIARY_ENTRY = "You are not currently editing any entry!\n";

    public static final String MESSAGE_NO_DIARY_EDIT = "You haven't made any edits to this entry!\n";

    public static final String MESSAGE_EDIT_SUCCESS = "Saved your edit! %1$s";

    public DoneEditDiaryEntryCommand() {}

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Optional<DiaryEntry> diaryEntry = Optional.ofNullable(model.getPageStatus().getDiaryEntry());

        if (!diaryEntry.isPresent()) {
            throw new CommandException(MESSAGE_NO_DIARY_ENTRY);
        }

        if (model.getPageStatus().getEditDiaryEntryDescriptor() == null) {
            throw new CommandException(MESSAGE_NO_DIARY_EDIT);
        }


        EditDiaryEntryDescriptor editDescriptor = model.getPageStatus().getEditDiaryEntryDescriptor();
        DiaryEntry newDiaryEntry = editDescriptor.buildDiaryEntry();

        model.getPageStatus().getTrip().getDiary().setDiaryEntry(diaryEntry.get(), newDiaryEntry);

        model.setPageStatus(model.getPageStatus()
                .withNewEditDiaryEntryDescriptor(null)
                .withNewDiaryEntry(newDiaryEntry));

        return new CommandResult(String.format(MESSAGE_EDIT_SUCCESS, diaryEntry));
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof DoneEditDiaryEntryCommand;
    }
}
