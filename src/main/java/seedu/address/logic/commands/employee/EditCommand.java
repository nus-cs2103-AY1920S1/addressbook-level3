package seedu.address.logic.commands.employee;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOIN_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
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
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.EmployeeAddress;
import seedu.address.model.employee.EmployeeEmail;
import seedu.address.model.employee.EmployeeGender;
import seedu.address.model.employee.EmployeeId;
import seedu.address.model.employee.EmployeeJoinDate;
import seedu.address.model.employee.EmployeeName;
import seedu.address.model.employee.EmployeePay;
import seedu.address.model.employee.EmployeePhone;
import seedu.address.model.employee.EmployeeSalaryPaid;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing employee in the employeeAddress book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit_em";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the employee identified "
            + "by the index number used in the displayed employee list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_GENDER + "GENDER] "
            + "[" + PREFIX_PAY + "PAY/HR] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_JOIN_DATE + "JOIN_DATE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Employee: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This employee already exists in the employeeAddress book.";

    private final Index index;
    private final EditEmployeeDescriptor editEmployeeDescriptor;

    /**
     * @param index of the employee in the filtered employee list to edit
     * @param editEmployeeDescriptor details to edit the employee with
     */
    public EditCommand(Index index, EditEmployeeDescriptor editEmployeeDescriptor) {
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
        Employee editedEmployee = createEditedEmployee(employeeToEdit, editEmployeeDescriptor);

        if (!employeeToEdit.isSameEmployee(editedEmployee) && model.hasEmployee(editedEmployee)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setEmployee(employeeToEdit, editedEmployee);
        model.updateFilteredEmployeeList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedEmployee), "Employee");
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
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
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


        public EditEmployeeDescriptor() {}

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
                    employeeGender, employeeJoinDate, employeeSalaryPaid);

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
