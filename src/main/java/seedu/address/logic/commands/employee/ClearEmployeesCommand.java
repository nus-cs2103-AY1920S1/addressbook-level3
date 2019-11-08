package seedu.address.logic.commands.employee;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;

/**
 * Clears all entries in the EmployeeBook.
 */
public class ClearEmployeesCommand extends Command {

    public static final String COMMAND_WORD = "clear_em";
    public static final String MESSAGE_SUCCESS = "EmployeeBook has been cleared!";
    public static final String MESSAGE_WRONG_TAB = "Current Window does not have an Employee List\n" +
            "Note: Employee Commands only works on either the Main or Finance Tab.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (MainWindow.isScheduleTab() || MainWindow.isStatsTab()) {
            throw new CommandException(MESSAGE_WRONG_TAB);
        }
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS, "Employee");
    }
}
