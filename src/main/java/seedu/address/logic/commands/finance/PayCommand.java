package seedu.address.logic.commands.finance;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY_PAID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.processor.EmployeeEventProcessor;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.ui.MainWindow;

/**
 * Command to pay Salary to the Employee and record it
 */
public class PayCommand extends Command {

    public static final String COMMAND_WORD = "pay";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Records a salary payment to the employee identified "
            + "by the index number used in the displayed employee list. \n"
            + "The payment amount cannot exceed their pending payment, "
            + "which is calculated by their total hours worked in the past events. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_SALARY_PAID + "PAY] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SALARY_PAID + "100 ";
    public static final String MESSAGE_SUCCESS = "%s has been paid %s";

    private final Index index;
    private final double salaryToPay;


    /**
     * @param index       of the employee in the filtered employee list to edit
     * @param salaryToPay Total Paid Salary to the Employee
     */
    public PayCommand(Index index, double salaryToPay) {
        requireNonNull(index);
        this.index = index;
        this.salaryToPay = salaryToPay;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (MainWindow.isScheduleTab() || MainWindow.isStatsTab()) {
            throw new CommandException(Messages.MESSAGE_WRONG_TAB_MISSING_EMPLOYEE_LIST);
        }

        List<Employee> lastShownList = model.getFilteredEmployeeList();
        Employee e = lastShownList.get(index.getZeroBased());
        double totalSalary = EmployeeEventProcessor.findEmployeeTotalWorkedHours(e, model.getFullListEvents())
                * e.getEmployeePay().getPay();
        double paid = e.getEmployeeSalaryPaid().getValue();
        double pendingPay = totalSalary - paid;

        if (pendingPay < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_EMPLOYEE_OVERPAID);
        } else if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
        } else if (salaryToPay > pendingPay) {
            throw new CommandException(Messages.MESSAGE_INVALID_EMPLOYEE_PAID);
        } else {
            Employee employeeToPay = lastShownList.get(index.getZeroBased());
            employeeToPay.addSalaryPaid(salaryToPay);

            model.updateFilteredEmployeeList(PREDICATE_SHOW_ALL_PERSONS);

            return new CommandResult(
                    String.format(MESSAGE_SUCCESS, employeeToPay.getEmployeeName(), salaryToPay),
                    "Finance");
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) { // short circuit if same object
            return true;
        }

        if (!(other instanceof PayCommand)) { // instanceof handles nulls
            return false;
        }

        // state check
        PayCommand e = (PayCommand) other;
        return index.equals(e.index) && salaryToPay == e.salaryToPay;
    }


}


