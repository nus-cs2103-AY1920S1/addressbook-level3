package seedu.address.logic.commands.finance;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SalaryPaid;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.*;
import seedu.address.model.employee.EmployeeSalaryPaid;


/**
 * Edits the details of an existing employee in the employeeAddress book.
 */
public class Pay extends Command {

    public static final String COMMAND_WORD = "pay";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the employee identified "
            + "by the index number used in the displayed employee list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_SalaryPaid + "PAY] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SalaryPaid + "100 ";

    public static final String MESSAGE_NOT_EDITED = "Please type correct command. Example: undopay 1 p/100";

    private final Index index;
    private final EmployeeSalaryPaid salaryPaid;

    /**
     * @param index      of the employee in the filtered employee list to edit
     * @param salaryPaid Total Paid Salary to the Employee
     */
    public Pay(Index index, EmployeeSalaryPaid salaryPaid) {
        requireNonNull(index);
        this.index = index;
        this.salaryPaid = salaryPaid;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Employee> lastShownList = model.getFilteredEmployeeList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
        }

        Employee employeeToEdit = lastShownList.get(index.getZeroBased());
        int salaryPaid = employeeToEdit.getEmployeeSalaryPaid().getValue() + 100;

//        String end = editedEmployee.getEmployeeSalaryPaid().value;
//        Double endDouble = Double.parseDouble(end);


//        double amt = startDouble + endDouble;
//        String output = (int) amt + "";

        //set amt
//        editedEmployee.setEmployeeSalaryPaid(new EmployeeSalaryPaid(output));
//        String tt = editedEmployee.getEmployeeTotalsalary().value;
//        Double ttDouble = Double.parseDouble(tt);
//        double ps = ttDouble - amt;
//        String oput = (int) ps + "";
//        editedEmployee.setEmployeePendingPay((new EmployeePendingPay(oput)));
        String output = "100";

        model.updateFilteredEmployeeList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(output, salaryPaid), "Finance");
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
        Pay e = (Pay) other;
        return index.equals(e.index) && salaryPaid.equals(e.salaryPaid);
    }


}


