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
    private final EmployeeGender employeeGender;


    // Data fields
    private final EmployeeAddress employeeAddress;
    private final Set<Tag> tags = new HashSet<>();
    //private final EmployeeJoinDate employeeJoinDate;
    private final EmployeePay employeePay;
    private final EmployeeSalaryPaid employeeSalaryPaid;

    /**
     * Every field must be present and not null.
     * Called in instantiating an Employee from previously stored object.
     */
    public Employee(EmployeeId employeeId, EmployeeName employeeName, EmployeeGender employeeGender,
                    EmployeePay employeePay, EmployeeSalaryPaid employeeSalaryPaid,
                    EmployeePhone employeePhone, EmployeeEmail employeeEmail,
                    EmployeeAddress employeeAddress, Set<Tag> tags) {
        this.employeeName = employeeName;
        this.employeePhone = employeePhone;
        this.employeeEmail = employeeEmail;
        this.employeeAddress = employeeAddress;
        this.employeeId = employeeId;
        this.employeeGender = employeeGender;
        this.employeePay = employeePay;
        this.employeeSalaryPaid = employeeSalaryPaid;
        this.tags.add(new Tag(employeeGender.gender));
        this.tags.addAll(tags);
    }

    /**
     * Creates a new Employee Object and instantiates a new {@code EmployeeSalaryPaid} object within.
     * Called by the Add and Edit Command.
     */
    public Employee(EmployeeId employeeId, EmployeeName employeeName, EmployeeGender employeeGender,
                    EmployeePay employeePay, EmployeePhone employeePhone, EmployeeEmail employeeEmail,
                    EmployeeAddress employeeAddress, Set<Tag> tags) {
        this(employeeId, employeeName, employeeGender, employeePay, new EmployeeSalaryPaid(),
                employeePhone, employeeEmail, employeeAddress, tags);
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

    public EmployeeSalaryPaid getEmployeeSalaryPaid() {
        return employeeSalaryPaid;
    }


    public EmployeePay getEmployeePay() {
        return employeePay;
    }

    public EmployeeGender getEmployeeGender() {
        return employeeGender;
    }


    public void addSalaryPaid(double salaryPaid) {
        employeeSalaryPaid.add(salaryPaid);
    }

    public void undoSalaryPaid(double salaryPaid) {
        employeeSalaryPaid.min(salaryPaid);
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
                && otherEmployee.getEmployeePay().equals(getEmployeePay())
                && otherEmployee.getEmployeePhone().equals(getEmployeePhone())
                && otherEmployee.getEmployeeEmail().equals(getEmployeeEmail())
                && otherEmployee.getEmployeeAddress().equals(getEmployeeAddress())
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
                .append(" EmployeePay: ")
                .append(getEmployeePay())
                .append(" Phone: ")
                .append(getEmployeePhone())
                .append(" Email: ")
                .append(getEmployeeEmail())
                .append(" Address: ")
                .append(getEmployeeAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
