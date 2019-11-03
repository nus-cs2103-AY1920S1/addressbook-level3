package seedu.address.logic.commands.finance;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;

/**
 * Command to find out how much an Employee has earnt over the past.
 */
public class PaySlip extends Command {

    public static final String COMMAND_WORD = "payslip";
    public static final String MESSAGE_SUCCESS = "Total Earnings of Employee %s from past events: %s";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the total earnings of the Employee from past events";

    private final Index index;

    /**
     * AutoAssigns a mapping for the full range of dates that the Event is held
     */
    public PaySlip(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Employee> shownEmployeelist = model.getFilteredEmployeeList();
        if (index.getZeroBased() >= shownEmployeelist.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
        }

        Employee employeeToFetch = shownEmployeelist.get(index.getZeroBased());
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, employeeToFetch.getEmployeeName()), "Finance");
    }
}
