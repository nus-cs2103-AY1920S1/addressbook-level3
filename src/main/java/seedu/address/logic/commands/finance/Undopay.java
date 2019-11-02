package seedu.address.logic.commands.finance;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.*;


/**
 * Edits the details of an existing employee in the employeeAddress book.
 */
public class Undopay extends Command {

    public static final String COMMAND_WORD = "undopay";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the employee identified "
            + "by the index number used in the displayed employee list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_PAY + "PAY] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PAY + "100 ";

    public static final String MESSAGE_NOT_EDITED = "Please type correct command. Example: undopay 1 p/100";

    private final Index index;
    private final EditEmployeeDescriptor editEmployeeDescriptor;

    /**
     * @param index of the employee in the filtered employee list to edit
     * @param editEmployeeDescriptor details to edit the employee with
     */
    public Undopay(Index index, EditEmployeeDescriptor editEmployeeDescriptor) {
        requireNonNull(index);
        requireNonNull(editEmployeeDescriptor);

        this.index = index;
        this.editEmployeeDescriptor = new EditEmployeeDescriptor(editEmployeeDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Employee> lastShownList = model.getFilteredEmployeeList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
        }

        Employee employeeToEdit = lastShownList.get(index.getZeroBased());
        String start = employeeToEdit.getEmployeeSalaryPaid().value;
        Double startDouble = Double.parseDouble(start);


        Employee editedEmployee = createEditedEmployee(employeeToEdit, editEmployeeDescriptor);
        String end = editedEmployee.getEmployeeSalaryPaid().value;
        Double endDouble = Double.parseDouble(end);


        double amt = startDouble + endDouble;
        String output = (int) amt + "";

        //set amt
        editedEmployee.setEmployeeSalaryPaid(new EmployeeSalaryPaid(output));
//        String tt = editedEmployee.getEmployeeTotalsalary().value;
//        Double ttDouble = Double.parseDouble(tt);
//        double ps = ttDouble - amt;
//        String oput = (int) ps + "";
//        editedEmployee.setEmployeePendingPay((new EmployeePendingPay(oput)));

        if (!employeeToEdit.isSameEmployee(editedEmployee) && model.hasEmployee(editedEmployee)) {
            throw new CommandException(output);
        }

        model.setEmployee(employeeToEdit, editedEmployee);
        model.updateFilteredEmployeeList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(output, editedEmployee),"Finance");
    }



    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Pay)) {
            return false;
        }

        // state check
        Undopay e = (Undopay) other;
        return index.equals(e.index)
                && editEmployeeDescriptor.equals(e.editEmployeeDescriptor);
    }

}


