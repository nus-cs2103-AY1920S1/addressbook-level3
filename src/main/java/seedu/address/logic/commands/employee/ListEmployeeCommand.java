package seedu.address.logic.commands.employee;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;

/**
 * Lists all employees and events in the address book to the user.
 */
public class ListEmployeeCommand extends Command {

    public static final String COMMAND_WORD = "list_em";

    public static final String MESSAGE_SUCCESS = "Listed all employees.";
    public static final String MESSAGE_WRONG_TAB = "Current Window does not have an Employee List\n"
            + "Note: Employee Commands only works on either the Main or Finance Tab.";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (MainWindow.isScheduleTab() || MainWindow.isStatsTab()) {
            throw new CommandException(MESSAGE_WRONG_TAB);
        }

        model.updateFilteredEmployeeList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS, "Employee");
    }
}
