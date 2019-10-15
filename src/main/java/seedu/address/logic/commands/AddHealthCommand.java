package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.records.Record;

/**
 * Adds a record to Duke Cooks.
 */
public class AddHealthCommand extends Command {

    public static final String COMMAND_WORD = "addHealth";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a health record";

    public static final String MESSAGE_SUCCESS = "New record added: %1$s";

    private final Record toAdd;

    /**
     * Creates an AddProfileCommand to add the specified {@code Record}
     */
    public AddHealthCommand(Record record) {
        requireNonNull(record);
        toAdd = record;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.addRecord(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddHealthCommand // instanceof handles nulls
                && toAdd.equals(((AddHealthCommand) other).toAdd));
    }
}
