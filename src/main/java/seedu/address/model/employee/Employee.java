package seedu.address.model.employee;

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
    private final EmployeeId employeeId;
    private final EmployeePosition employeePosition;
    private final EmployeeGender employeeGender;



    // Data fields
    private final EmployeeAddress employeeAddress;
    private final Set<Tag> tags = new HashSet<>();
    private final EmployeeJoinDate employeeJoinDate;

    /**
     * Every field must be present and not null.
     */
    public Employee(EmployeeId employeeId, EmployeeName employeeName, EmployeeGender employeeGender,
                     EmployeePosition employeePosition, EmployeePhone employeePhone, EmployeeEmail employeeEmail,
                     EmployeeAddress employeeAddress, EmployeeJoinDate employeeJoinDate, Set<Tag> tags) {
        this.employeeName = employeeName;
        this.employeePhone = employeePhone;
        this.employeeEmail = employeeEmail;
        this.employeeAddress = employeeAddress;
        this.employeeId = employeeId;
        this.employeeGender = employeeGender;
        this.employeeJoinDate = employeeJoinDate;
        this.employeePosition = employeePosition;
        this.tags.add(new Tag(employeeGender.gender));
        this.tags.addAll(tags);
    }

    public Employee() {
        this.employeeName = null;
        this.employeePhone = null;
        this.employeeEmail = null;
        this.employeeAddress = null;
        this.employeeId = null;
        this.employeeGender = null;
        this.employeeJoinDate = null;
        this.employeePosition = null;
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

    public EmployeeId getEmployeeId() {
        return employeeId;
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
            && otherEmployee.getEmployeePhone().equals(getEmployeePhone())
            && otherEmployee.getEmployeeEmail().equals(getEmployeeEmail());

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
        return otherEmployee.getEmployeeId().equals(getEmployeeId())
                && otherEmployee.getEmployeeName().equals(getEmployeeName())
                && otherEmployee.getEmployeeGender().equals(getEmployeeGender())
                && otherEmployee.getEmployeePosition().equals(getEmployeePosition())
                && otherEmployee.getEmployeePhone().equals(getEmployeePhone())
                && otherEmployee.getEmployeeEmail().equals(getEmployeeEmail())
                && otherEmployee.getEmployeeAddress().equals(getEmployeeAddress())
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
                .append(" ID: ")
                .append(getEmployeeId())
                .append(" Gender: ")
                .append(getEmployeeGender())
                .append(" Position: ")
                .append(getEmployeePosition())
                .append(" Phone: ")
                .append(getEmployeePhone())
                .append(" Email: ")
                .append(getEmployeeEmail())
                .append(" Address: ")
                .append(getEmployeeAddress())
                .append(" JoinDate: ")
                .append(getEmployeeJoinDate())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
