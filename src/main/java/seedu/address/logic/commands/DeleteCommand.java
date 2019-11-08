package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Deletes a person, event, or training.
 */
public abstract class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_USAGE = "\nTo delete a person,\n" + DeletePersonCommand.MESSAGE_USAGE + "\n"
        + "\nTo delete an event,\n" + DeleteEventCommand.MESSAGE_USAGE + "\n"
        + "\nTo delete a trainng,\n" + DeleteTrainingCommand.MESSAGE_USAGE + "\n"
        + "\nTo delete a record, \n" + DeleteRecordCommand.MESSAGE_USAGE;

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

    @Override
    public boolean isUndoable() {
        return true;
    }
    @Override
    public String toString() {
        return "Delete Command";
    }
}
