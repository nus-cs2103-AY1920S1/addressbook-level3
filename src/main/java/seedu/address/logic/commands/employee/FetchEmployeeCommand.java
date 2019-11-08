package seedu.address.logic.commands.employee;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.processor.DistinctDatesProcessor;
import seedu.address.model.Model;
import seedu.address.model.distinctdate.DistinctDate;
import seedu.address.model.employee.Employee;
import seedu.address.ui.MainWindow;

/**
 * Fetches all details of an Employee and the employee's schedule.
 */
public class FetchEmployeeCommand extends Command {
    public static final String COMMAND_WORD = "fetch_em";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Fetches all details of an Employee and display his schedule\n"
            + "Example: " + COMMAND_WORD + " on/02/12/2019";

    public static final String MESSAGE_SUCCESS = "Fetched Employee: %1$s";

    private final Index index;

    public FetchEmployeeCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (MainWindow.isScheduleTab() || MainWindow.isStatsTab()) {
            throw new CommandException(Messages.MESSAGE_WRONG_TAB_MISSING_EMPLOYEE_LIST);
        }

        List<Employee> shownEmployeeList = model.getFilteredEmployeeList();
        if (index.getZeroBased() >= shownEmployeeList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
        }
        Employee employeeToFetch = shownEmployeeList.get(index.getZeroBased());
        List<DistinctDate> distinctDates =
                DistinctDatesProcessor.generateEmployeesDistinctDateList(model, employeeToFetch);
        model.updateEmployeeDistinctDateList(distinctDates);

        return new CommandResult(String.format(MESSAGE_SUCCESS, employeeToFetch.getEmployeeName()), false,
                false, index.getZeroBased(), "Employee_Fetch");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FetchEmployeeCommand)) {
            return false;
        }

        // state check
        FetchEmployeeCommand e = (FetchEmployeeCommand) other;
        return index.equals(e.index);
    }
}
