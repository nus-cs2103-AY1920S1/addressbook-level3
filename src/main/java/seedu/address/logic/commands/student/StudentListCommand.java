package seedu.address.logic.commands.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a list student command.
 */
public class StudentListCommand extends StudentCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + " list: List of students";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS); //change later
        return new CommandResult("{MESSAGE_SUCCESS}");
    }
}
