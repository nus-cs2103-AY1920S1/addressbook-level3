package seedu.address.logic.commands.employee;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all employees and events in the address book to the user.
 */
public class ListEmployeeCommand extends Command {

    public static final String COMMAND_WORD = "list_em";

    public static final String MESSAGE_SUCCESS = "Listed all employees.";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEmployeeList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS, "Employee");
    }
}
