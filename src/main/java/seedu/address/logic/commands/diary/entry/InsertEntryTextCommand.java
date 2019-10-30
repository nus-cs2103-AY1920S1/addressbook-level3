package seedu.address.logic.commands.diary.entry;

import static java.util.Objects.requireNonNull;

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
 * {@link Command} that inserts the new text specified by the user at the specified line,
 * and commits the data in the current {@link EditDiaryEntryDescriptor} (if any) to the user diary entry.
 */
public class InsertEntryTextCommand extends Command {
    public static final String COMMAND_WORD = "insert";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Inserts and commits text to a line number in"
            + " the current diary entry.\n"
            + "If the line number specified is more than the current number of lines, then as many empty lines"
            + "as necessary will be added!\n"
            + "Parameters: "
            + "[" + PREFIX_INDEX + " specific line number to edit (must be a valid positive integer).]\n"
            + "[" + PREFIX_DESCRIPTION + "Text line to insert\n"
            + "OR Text line with '<images (positive integer of a photo according to the gallery order)>'\n"
            + "OR '<images (positive integers of photos according to the gallery order separated by spaces)>']";

    private static final String MESSAGE_NO_DIARY_ENTRY = "You are not currently viewing any entry!\n";

    private static final String MESSAGE_INSERT_SUCCESS = "Inserted your line %1$d! %2$s";

    private final String textToInsert;
    private final Index lineNumber;

    public InsertEntryTextCommand(String textToInsert, Index lineNumber) {
        requireAllNonNull(textToInsert, lineNumber);
        this.textToInsert = textToInsert;
        this.lineNumber = lineNumber;
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
            editDescriptor.insertTextLine(this.textToInsert, this.lineNumber);

            DiaryEntry editedDiaryEntry = editDescriptor.buildDiaryEntry();
            model.getPageStatus().getCurrentTripDiary().setDiaryEntry(diaryEntry, editedDiaryEntry);
            model.setPageStatus(model.getPageStatus()
                    .withNewDiaryEntry(editedDiaryEntry));

            return new CommandResult(
                    String.format(MESSAGE_INSERT_SUCCESS, lineNumber.getOneBased(), this.textToInsert));
        } else {
            //There is an edit buffer being used
            editDescriptor.insertTextLine(this.textToInsert, this.lineNumber);

            return new CommandResult(
                    String.format(MESSAGE_INSERT_SUCCESS, lineNumber.getOneBased(), this.textToInsert));
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof InsertEntryTextCommand)) {
            return false;
        }

        InsertEntryTextCommand otherCommand = (InsertEntryTextCommand) obj;
        return textToInsert.equals(otherCommand.textToInsert)
                && lineNumber.equals(otherCommand.lineNumber);
    }
}
