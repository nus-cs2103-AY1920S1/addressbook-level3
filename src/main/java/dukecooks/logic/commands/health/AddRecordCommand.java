package dukecooks.logic.commands.health;

import static java.util.Objects.requireNonNull;

import dukecooks.commons.core.Event;
import dukecooks.logic.commands.AddCommand;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Model;
import dukecooks.model.health.components.Record;
import dukecooks.model.health.components.Type;

/**
 * Adds a record to Duke Cooks.]
 */
public class AddRecordCommand extends AddCommand {

    public static final String VARIANT_WORD = "health";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a health record";

    public static final String MESSAGE_SUCCESS = "New record added: %1$s";
    public static final String MESSAGE_DUPLICATE_RECORD =
            "A Record with corresponding timestamp already exists in Duke Cooks";

    private static Event event;

    private final Record toAdd;

    /**
     * Creates an AddProfileCommand to add the specified {@code Record}
     */
    public AddRecordCommand(Record record) {
        requireNonNull(record);
        toAdd = record;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // checks for duplicate record
        if (model.hasRecord(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_RECORD);
        }
        // checks if valid entry
        Type type = toAdd.getType();
        double value = toAdd.getValue().getValue();
        if (!type.isValidNumber(type.toString(), value)) {
            throw new CommandException(type.messageInflatedValue());
        }
        // add record
        model.addRecord(toAdd);
        // filters down to record type to assist the subsequent steps
        model.updateFilteredRecordList(x -> x.getType().equals(type));
        if (type.equals(Type.Weight) || type.equals(Type.Height)) {
            LinkProfile.updateProfile(model, type);
        }
        // trigger event to direct user to view the output
        event = Event.getInstance();
        event.set("health", "type");

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddRecordCommand // instanceof handles nulls
                && toAdd.equals(((AddRecordCommand) other).toAdd));
    }
}
