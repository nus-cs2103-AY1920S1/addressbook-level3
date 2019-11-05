package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.FLAG_EVENT;
import static seedu.address.logic.parser.CliSyntax.FLAG_PERSON;
import static seedu.address.logic.parser.CliSyntax.FLAG_TRAINING;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Deletes a person, event, or training.
 */
public abstract class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = "Use the flags "
        + FLAG_PERSON + " or " + FLAG_EVENT + " or " + FLAG_TRAINING
        + " to delete a person, event or training respectively.";

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

    @Override
    public boolean isUndoable() {
        return true;
    }

}
