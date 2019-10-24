package seedu.address.diaryfeature.logic.commands;

import static java.util.Objects.requireNonNull;


import seedu.address.diaryfeature.model.DiaryModel;
import seedu.address.diaryfeature.model.diaryEntry.DiaryEntry;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command<DiaryModel> {

    public static final String COMMAND_WORD = "add entry";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an entry to the diaryBook. ";


    public static final String MESSAGE_SUCCESS = "New entry added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This entry already exists in the diaryBook";

    private final DiaryEntry toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(DiaryEntry entry) {
        requireNonNull(entry);
        toAdd = entry;
    }

    @Override
    public CommandResult execute(DiaryModel model) throws CommandException {
        requireNonNull(model);

        model.addDiaryEntry(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}