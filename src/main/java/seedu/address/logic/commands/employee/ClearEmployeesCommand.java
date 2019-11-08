package seedu.address.logic.commands.employee;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
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

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (MainWindow.isScheduleTab() || MainWindow.isStatsTab()) {
            throw new CommandException(Messages.MESSAGE_WRONG_TAB_MISSING_EMPLOYEE_LIST);
        }
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS, "Employee");
    }
}
