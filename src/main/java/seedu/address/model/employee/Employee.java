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
    /**
     * Suggested Improvements:
     * Rename 'EmployeePay' as 'EmployeeSalary' (More accurate variable naming)
     * Abstract EmployeeTotalSalary and EmployeePendingPay to EmployeeFinances (its already there)
     */

    private EmployeeTotalSalary employeeTotalSalary; //remove
    private EmployeePendingPay employeePendingPay; //remove
    private EmployeePay employeePay;


    // Identity fields
    private final EmployeeName employeeName;
    private final EmployeePhone employeePhone;
    private final EmployeeEmail employeeEmail;
    private final EmployeeId employeeId;
    private final EmployeeGender employeeGender;


    // Data fields
    private final EmployeeAddress employeeAddress;
    private final Set<Tag> tags = new HashSet<>();
    private final EmployeeJoinDate employeeJoinDate;
    private final EmployeeFinances employeeFinances = new EmployeeFinances();

    /**
     * Every field must be present and not null.
     */
    public Employee(EmployeeId employeeId, EmployeeName employeeName, EmployeeGender employeeGender,
                    EmployeePay employeePay, EmployeePendingPay employeePendingPay,
                    EmployeeTotalSalary employeeTotalSalary,
                    EmployeePhone employeePhone, EmployeeEmail employeeEmail,
                    EmployeeAddress employeeAddress, EmployeeJoinDate employeeJoinDate, Set<Tag> tags) {
        this.employeeName = employeeName;
        this.employeePhone = employeePhone;
        this.employeeEmail = employeeEmail;
        this.employeeAddress = employeeAddress;
        this.employeeId = employeeId;
        this.employeeGender = employeeGender;
        this.employeeJoinDate = employeeJoinDate;
        this.employeePay = employeePay;
        this.employeePendingPay = employeePendingPay;
        this.employeeTotalSalary = employeeTotalSalary;
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
        this.employeePay = null;
        this.employeePendingPay = null;
        this.employeeTotalSalary = null;
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

    public EmployeePay getEmployeePay() {
        return employeePay;
    }

    public EmployeeTotalSalary getEmployeeTotalsalary() {
        EmployeeTotalSalary e = new EmployeeTotalSalary("10000");
//        return employeeFinances.getTotalPay(); use this
        return e;

    }

    public EmployeePendingPay getEmployeePendingPay() {
        return employeePendingPay;
//        return employeeFinances.getPendingPay(); use this
    }

    public EmployeeGender getEmployeeGender() {
        return employeeGender;
    }

    public EmployeeJoinDate getEmployeeJoinDate() {
        return employeeJoinDate;
    }

    public void setEmployeePay(EmployeePay employeePay) { //shouldnt need this
        this.employeePay = employeePay;
    }

    public void setEmployeeTotalSalary(EmployeeTotalSalary employeeTotalSalary) { //shouldnt need this
        this.employeeTotalSalary = employeeTotalSalary;
    }

    public void setEmployeePendingPay(EmployeePendingPay employeePendingPay) { //shouldnt need this
        this.employeePendingPay = employeePendingPay;
    }

    //use this pls!
    public void updateEmployeeFinances(EmployeeTotalSalary totalSalary, EmployeePendingPay pendingSalary) {
        employeeFinances.setFinances(totalSalary, pendingSalary);
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
                && otherEmployee.getEmployeeName().equals(getEmployeeName());
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
                && otherEmployee.getEmployeePendingPay().equals(getEmployeePendingPay())
                && otherEmployee.getEmployeeTotalsalary().equals(getEmployeeTotalsalary())
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
                .append(" EmployeeID: ")
                .append(getEmployeeId())
                .append(" EmployeeGender: ")
                .append(getEmployeeGender())
                .append(" EmployeePay: ")
                .append(getEmployeePay())
                .append(" EmployeePendingPay: ")
                .append(getEmployeePendingPay())
                .append(" EmployeeTotalSalary: ")
                .append(getEmployeeTotalsalary())
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
