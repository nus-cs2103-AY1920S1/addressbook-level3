package seedu.address.diaryfeature.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.diaryfeature.model.diaryEntry.DiaryEntry;
import seedu.address.diaryfeature.model.DiaryModel;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Adds a diary entry to the Diary Book
 */
public class AddCommand extends Command<DiaryModel> {

    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_SUCCESS = "New entry added:";
    private final DiaryEntry toAdd;
    public static final String MESSAGE_DUPLICATE_ERROR = "This entry already exists!";


    /**
     * Generates the Add Command to add an diary entry to the diary book
     *
     * @param entry
     */
    public AddCommand(DiaryEntry entry) {
        requireNonNull(entry);
        toAdd = entry;
    }

    /**
     * Executes the add command and gives the success command result
     *
     * @param model {@code model} which the command should operate on.
     * @return
     */

    @Override
    public CommandResult execute(DiaryModel model) throws CommandException {
        requireNonNull(model);
        if (model.contains(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ERROR);
        }
        model.addDiaryEntry(toAdd);
        return new CommandResult(MESSAGE_SUCCESS + "\n" + toAdd);
    }

    /**
     * Checks if the 2 add commands are equal
     *
     * @param other another object to check
     * @return true if the object is the same as this command
     */

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
