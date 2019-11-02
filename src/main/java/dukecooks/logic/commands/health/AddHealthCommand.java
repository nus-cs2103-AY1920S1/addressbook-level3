package dukecooks.logic.commands.health;

import static java.util.Objects.requireNonNull;

import dukecooks.logic.commands.AddCommand;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Model;
import dukecooks.model.health.components.Record;

/**
 * Adds a record to Duke Cooks.
 */
public class AddHealthCommand extends AddCommand {

    public static final String VARIANT_WORD = "health";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a health record";

    public static final String MESSAGE_SUCCESS = "New record added: %1$s";
    public static final String MESSAGE_DUPLICATE_RECORD =
            "A Record with corresponding timestamp already exists in Duke Cooks";

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

        if (model.hasRecord(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_RECORD);
        }

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
