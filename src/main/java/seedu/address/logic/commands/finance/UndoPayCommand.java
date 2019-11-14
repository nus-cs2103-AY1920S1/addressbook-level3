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
 * Command to undo payment made to the Employee and record it
 */
public class UndoPayCommand extends Command {

    public static final String COMMAND_WORD = "undo_pay";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the employee identified "
            + "by the index number used in the displayed employee list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_SALARY_PAID + "PAY] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SALARY_PAID + "100 ";
    public static final String MESSAGE_SUCCESS = "A Salary Payment of %s for Employee %s has been undone.";

    private final Index index;
    private final double salaryToUndo;

    /**
     * @param index        of the employee in the filtered employee list to edit
     * @param salaryToUndo Total Paid Salary to the Employee
     */
    public UndoPayCommand(Index index, double salaryToUndo) {
        requireNonNull(index);
        this.index = index;
        this.salaryToUndo = salaryToUndo;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (MainWindow.isScheduleTab() || MainWindow.isStatsTab()) {
            throw new CommandException(Messages.MESSAGE_WRONG_TAB_MISSING_EMPLOYEE_LIST);
        }

        List<Employee> lastShownList = model.getFilteredEmployeeList();
        Employee e = lastShownList.get(index.getZeroBased());
        double totalSalary = EmployeeEventProcessor.findEmployeeTotalWorkedHours(e, model.getFilteredEventList())
                * e.getEmployeePay().getPay();
        double paid = e.getEmployeeSalaryPaid().getValue();
        double pendingPay = totalSalary - paid;

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
        } else if (salaryToUndo > paid) {
            throw new CommandException(Messages.MESSAGE_INVALID_EMPLOYEE_PAID);
        } else {
            Employee employeeToPay = lastShownList.get(index.getZeroBased());
            employeeToPay.undoSalaryPaid(salaryToUndo);

            model.updateFilteredEmployeeList(PREDICATE_SHOW_ALL_PERSONS);

            return new CommandResult(
                    String.format(MESSAGE_SUCCESS, salaryToUndo, employeeToPay.getEmployeeName()),
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
        UndoPayCommand e = (UndoPayCommand) other;
        return index.equals(e.index) && salaryToUndo == e.salaryToUndo;
    }

}
