package seedu.address.logic.commands.finance;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARYPAID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;


/**
 * Command to pay out Salary to the Employee and record it
 */
public class Undopay extends Command {

    public static final String COMMAND_WORD = "undo_pay";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the employee identified "
            + "by the index number used in the displayed employee list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_SALARYPAID + "PAY] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SALARYPAID + "100 ";
    public static final String MESSAGE_SUCCESS = "Undo the payment for %s. Amount : $ %s";

    private final Index index;
    private final double salaryToPay;

    /**
     * @param index       of the employee in the filtered employee list to edit
     * @param salaryToPay Total Paid Salary to the Employee
     */
    public Undopay(Index index, double salaryToPay) {
        requireNonNull(index);
        this.index = index;
        this.salaryToPay = salaryToPay;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Employee> lastShownList = model.getFilteredEmployeeList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
        }

        Employee employeeToPay = lastShownList.get(index.getZeroBased());
        employeeToPay.undoSalaryPaid(salaryToPay);

        model.updateFilteredEmployeeList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, employeeToPay.getEmployeeName(), salaryToPay),
                "Finance");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) { // short circuit if same object
            return true;
        }

        if (!(other instanceof Pay)) { // instanceof handles nulls
            return false;
        }

        // state check
        Undopay e = (Undopay) other;
        return index.equals(e.index) && salaryToPay == e.salaryToPay;
    }


}





