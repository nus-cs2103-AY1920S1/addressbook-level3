package seedu.address.model.Employee;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Employee in the employeeAddress book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Employee {

    // Identity fields
    private final EmployeeName employeeName;
    private final EmployeePhone employeePhone;
    private final EmployeeEmail employeeEmail;
    private final EmployeeID employeeID;
    private final EmployeePosition employeePosition;
    private final EmployeeGender employeeGender;



    // Data fields
    private final EmployeeAddress employeeAddress;
    private final Set<Tag> tags = new HashSet<>();
    private final EmployeeJoinDate employeeJoinDate;

    /**
     * Every field must be present and not null.
     */
    public Employee(EmployeePosition employeePosition, EmployeeID employeeID, EmployeeGender employeeGender,
                    EmployeeJoinDate employeeJoinDate, EmployeeName employeeName, EmployeePhone employeePhone,
                    EmployeeEmail employeeEmail, EmployeeAddress employeeAddress, Set<Tag> tags) {
        requireAllNonNull(employeeName, employeePhone, employeeEmail, employeeAddress, tags,
                          employeeGender, employeeID, employeeJoinDate, employeePosition);
        this.employeeName = employeeName;
        this.employeePhone = employeePhone;
        this.employeeEmail = employeeEmail;
        this.employeeAddress = employeeAddress;
        this.employeeID = employeeID;
        this.employeeGender = employeeGender;
        this.employeeJoinDate = employeeJoinDate;
        this.employeePosition = employeePosition;
        this.tags.addAll(tags);
    }

    public EmployeeName getEmployeeName() {
        return employeeName;
    }

    public EmployeePhone getEmployeePhone() {
        return employeePhone;
    }

    public EmployeeEmail getEmployeeEmail() {
        return employeeEmail;
    }

    public EmployeeAddress getEmployeeAddress() {
        return employeeAddress;
    }

    public EmployeeID getEmployeeID() {
        return employeeID;
    }

    public EmployeePosition getEmployeePosition() {
        return employeePosition;
    }

    public EmployeeGender getEmployeeGender() {
        return employeeGender;
    }

    public EmployeeJoinDate getEmployeeJoinDate() {
        return employeeJoinDate;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both employees of the same employeeName have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two employees.
     */
    public boolean isSameEmployee(Employee otherEmployee) {
        if (otherEmployee == this) {
            return true;
        }

        return otherEmployee != null
                && otherEmployee.getEmployeeName().equals(getEmployeeName())
                && (otherEmployee.getEmployeePhone().equals(getEmployeePhone()) || otherEmployee.getEmployeeEmail().equals(getEmployeeEmail()));
    }

    /**
     * Returns true if both employees have the same identity and data fields.
     * This defines a stronger notion of equality between two employees.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Employee)) {
            return false;
        }

        Employee otherEmployee = (Employee) other;
        return otherEmployee.getEmployeeName().equals(getEmployeeName())
                && otherEmployee.getEmployeePhone().equals(getEmployeePhone())
                && otherEmployee.getEmployeeEmail().equals(getEmployeeEmail())
                && otherEmployee.getEmployeeAddress().equals(getEmployeeAddress())
                && otherEmployee.getEmployeeID().equals(getEmployeeID())
                && otherEmployee.getEmployeePosition().equals(getEmployeePosition())
                && otherEmployee.getEmployeeGender().equals(getEmployeeGender())
                && otherEmployee.getEmployeeJoinDate().equals(getEmployeeJoinDate())
                && otherEmployee.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(employeeName, employeePhone, employeeEmail, employeeAddress, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getEmployeeName())
                .append(" EmployeeID: ")
                .append(getEmployeeID())
                .append(" EmployeeGender: ")
                .append(getEmployeeGender())
                .append(" EmployeePosition: ")
                .append(getEmployeePosition())
                .append(" EmployeePhone: ")
                .append(getEmployeePhone())
                .append(" EmployeeEmail: ")
                .append(getEmployeeEmail())
                .append(" EmployeeAddress: ")
                .append(getEmployeeAddress())
                .append(" EmployeeJoinDate: ")
                .append(getEmployeeJoinDate())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
