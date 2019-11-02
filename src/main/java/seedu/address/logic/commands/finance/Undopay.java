package seedu.address.logic.commands.finance;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.*;
import seedu.address.model.employee.EmployeePay;
import seedu.address.model.tag.Tag;


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

    /**
     * Creates and returns a {@code Employee} with the details of {@code employeeToEdit}
     * edited with {@code editEmployeeDescriptor}.
     */
    private static Employee createEditedEmployee(Employee employeeToEdit,
                                                 EditEmployeeDescriptor editEmployeeDescriptor) {
        assert employeeToEdit != null;

        EmployeeName updatedEmployeeName = editEmployeeDescriptor.getEmployeeName()
                .orElse(employeeToEdit.getEmployeeName());
        EmployeePhone updatedEmployeePhone = editEmployeeDescriptor.getEmployeePhone()
                .orElse(employeeToEdit.getEmployeePhone());
        EmployeeEmail updatedEmployeeEmail = editEmployeeDescriptor.getEmployeeEmail()
                .orElse(employeeToEdit.getEmployeeEmail());
        EmployeeAddress updatedEmployeeAddress = editEmployeeDescriptor.getEmployeeAddress()
                .orElse(employeeToEdit.getEmployeeAddress());
        Set<Tag> updatedTags = editEmployeeDescriptor.getTags()
                .orElse(employeeToEdit.getTags());
        EmployeeId updatedEmployeeId = editEmployeeDescriptor.getEmployeeId().orElse(employeeToEdit.getEmployeeId());
        EmployeeSalaryPaid updatedEmployeeSalaryPaid = editEmployeeDescriptor.getEmployeeSalaryPaid()
                .orElse(employeeToEdit.getEmployeeSalaryPaid());
        EmployeePay updatedEmployeePay = editEmployeeDescriptor.getEmployeePay()
                .orElse(employeeToEdit.getEmployeePay());
        EmployeeGender updatedEmployeeGender = editEmployeeDescriptor.getEmployeeGender()
                .orElse(employeeToEdit.getEmployeeGender());
        EmployeeJoinDate updatedEmployeeJoinDate = editEmployeeDescriptor.getEmployeeJoinDate()
                .orElse(employeeToEdit.getEmployeeJoinDate());

        return new Employee(updatedEmployeeId, updatedEmployeeName, updatedEmployeeGender,
                updatedEmployeePay, updatedEmployeePhone, updatedEmployeeEmail, updatedEmployeeAddress,
                updatedEmployeeJoinDate, updatedTags);
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

    /**
     * Stores the details to edit the employee with. Each non-empty field value will replace the
     * corresponding field value of the employee.
     */
    public static class EditEmployeeDescriptor {
        private EmployeeName employeeName;
        private EmployeePhone employeePhone;
        private EmployeeEmail employeeEmail;
        private EmployeeAddress employeeAddress;
        private EmployeeJoinDate employeeJoinDate;
        private EmployeeSalaryPaid employeeSalaryPaid;
        private EmployeePay employeePay;
        private EmployeeGender employeeGender;
        private EmployeeId employeeId;
        private Set<Tag> tags;

        public EditEmployeeDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditEmployeeDescriptor(EditEmployeeDescriptor toCopy) {
            setEmployeeName(toCopy.employeeName);
            setEmployeePhone(toCopy.employeePhone);
            setEmployeeEmail(toCopy.employeeEmail);
            setEmployeeAddress(toCopy.employeeAddress);
            setEmployeeId(toCopy.employeeId);
            setEmployeeGender(toCopy.employeeGender);
            setEmployeeSalaryPaid(toCopy.employeeSalaryPaid);
            setEmployeePay(toCopy.employeePay);;
            setEmployeeJoinDate(toCopy.employeeJoinDate);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(employeeName, employeePhone, employeeEmail, employeeAddress, tags,
                    employeeGender, employeeJoinDate, employeeSalaryPaid, employeePay);
        }

        public void setEmployeeName(EmployeeName employeeName) {
            this.employeeName = employeeName;
        }

        public Optional<EmployeeName> getEmployeeName() {
            return Optional.ofNullable(employeeName);
        }

        public void setEmployeePhone(EmployeePhone employeePhone) {
            this.employeePhone = employeePhone;
        }

        public Optional<EmployeePhone> getEmployeePhone() {
            return Optional.ofNullable(employeePhone);
        }

        public void setEmployeeId(EmployeeId employeeId) {
            this.employeeId = employeeId;
        }

        public void setEmployeeSalaryPaid(EmployeeSalaryPaid employeeSalaryPaid) {
            this.employeeSalaryPaid = employeeSalaryPaid;
        }

        public Optional<EmployeeSalaryPaid> getEmployeeSalaryPaid() {
            return Optional.ofNullable(employeeSalaryPaid);
        }

        public void setEmployeePay(EmployeePay employeePay) {
            this.employeePay = employeePay;
        }

        public Optional<EmployeePay> getEmployeePay() {
            return Optional.ofNullable(employeePay);
        }

        public void setEmployeeGender(EmployeeGender employeeGender) {
            this.employeeGender = employeeGender;
        }

        public Optional<EmployeeGender> getEmployeeGender() {
            return Optional.ofNullable(employeeGender);
        }

        public void setEmployeeJoinDate(EmployeeJoinDate employeeJoinDate) {
            this.employeeJoinDate = employeeJoinDate;
        }

        public Optional<EmployeeJoinDate> getEmployeeJoinDate() {
            return Optional.ofNullable(employeeJoinDate);
        }

        public Optional<EmployeeId> getEmployeeId() {
            return Optional.ofNullable(employeeId);
        }

        public void setEmployeeEmail(EmployeeEmail employeeEmail) {
            this.employeeEmail = employeeEmail;
        }

        public Optional<EmployeeEmail> getEmployeeEmail() {
            return Optional.ofNullable(employeeEmail);
        }

        public void setEmployeeAddress(EmployeeAddress employeeAddress) {
            this.employeeAddress = employeeAddress;
        }

        public Optional<EmployeeAddress> getEmployeeAddress() {
            return Optional.ofNullable(employeeAddress);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditEmployeeDescriptor)) {
                return false;
            }

            // state check
            EditEmployeeDescriptor e = (EditEmployeeDescriptor) other;

            return getEmployeeName().equals(e.getEmployeeName())
                    && getEmployeePhone().equals(e.getEmployeePhone())
                    && getEmployeeEmail().equals(e.getEmployeeEmail())
                    && getEmployeeAddress().equals(e.getEmployeeAddress())
                    && getTags().equals(e.getTags())
                    && getEmployeeSalaryPaid().equals(e.getEmployeeSalaryPaid())
                    && getEmployeePay().equals(e.getEmployeePay())
                    && getEmployeeId().equals(e.getEmployeeId())
                    && getEmployeeGender().equals(e.getEmployeeGender())
                    && getEmployeeJoinDate().equals(e.getEmployeeJoinDate());
        }
    }
}


