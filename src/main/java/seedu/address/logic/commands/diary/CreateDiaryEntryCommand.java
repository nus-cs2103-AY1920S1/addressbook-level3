package seedu.address.logic.commands.diary;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.diary.DiaryEntry;
import seedu.address.model.diary.EditDiaryEntryDescriptor;

public class CreateDiaryEntryCommand extends Command {
    public static final String COMMAND_WORD = "create";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates a new current diary entry for the specified day\n"
            + "Parameters: INDEX (must be a positive integer, and the entry must not already exist) "
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_ENTRY_EXISTS = "There is already an entry for this day! %1$s";

    public static final String MESSAGE_CREATE_SUCCESS = "Brought up the edit window, go ahead and type!";

    private Index dayIndex;

    public CreateDiaryEntryCommand(Index dayIndex) {
        requireNonNull(dayIndex);
        this.dayIndex = dayIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        DiaryEntry diaryEntry = new DiaryEntry(dayIndex);
        try {
            model.getPageStatus().getTrip().getDiary().getDiaryEntryList().addDiaryEntry(diaryEntry);
        } catch (IllegalArgumentException ex) {
            throw new CommandException(String.format(MESSAGE_ENTRY_EXISTS,
                    model.getPageStatus().getTrip().getDiary().getDiaryEntry(dayIndex).get()));
        }

        EditDiaryEntryDescriptor editDescriptor = new EditDiaryEntryDescriptor(diaryEntry);

        model.setPageStatus(model.getPageStatus()
                .withNewEditDiaryEntryDescriptor(editDescriptor)
                .withNewDiaryEntry(diaryEntry));

        return new CommandResult(MESSAGE_CREATE_SUCCESS);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj
                || (obj instanceof CreateDiaryEntryCommand
                        && ((CreateDiaryEntryCommand) obj).dayIndex.equals(dayIndex));
    }
}
