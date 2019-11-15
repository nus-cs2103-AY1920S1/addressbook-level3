package dukecooks.logic.commands.health;

import static java.util.Objects.requireNonNull;

import java.util.List;

import dukecooks.commons.core.Event;
import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.DeleteCommand;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.Model;
import dukecooks.model.health.components.Record;
import dukecooks.model.health.components.Type;

/**
 * Deletes a record identified using it's displayed index from Duke Cooks.
 */
public class DeleteRecordCommand extends DeleteCommand {

    public static final String VARIANT_WORD = "health";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the record identified by the index number used in the displayed record list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_RECORD_SUCCESS = "Deleted Record: %1$s";

    private static Event event;

    private final Index targetIndex;

    public DeleteRecordCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Record> lastShownList = model.getFilteredRecordList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
        }

        Record recordToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteRecord(recordToDelete);

        Type type = recordToDelete.getType();
        ListHealthByTypeCommand.setCurrentTypeView(type);
        model.updateFilteredRecordList(x -> x.getType().equals(type));
        if (type.equals(Type.Weight) || type.equals(Type.Height)) {
            LinkProfile.updateProfile(model, type);
        }
        // trigger event to direct user to view the output
        event = Event.getInstance();
        event.set("health", "type");

        return new CommandResult(String.format(MESSAGE_DELETE_RECORD_SUCCESS, recordToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteRecordCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteRecordCommand) other).targetIndex)); // state check
    }
}
