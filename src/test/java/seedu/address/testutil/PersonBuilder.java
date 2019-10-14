package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.Employee.*;
import seedu.address.model.Employee.EmployeeAddress;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Employee objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private EmployeeName employeeName;
    private EmployeePhone employeePhone;
    private EmployeeEmail employeeEmail;
    private EmployeeAddress employeeAddress;
    private Set<Tag> tags;

    public PersonBuilder() {
        employeeName = new EmployeeName(DEFAULT_NAME);
        employeePhone = new EmployeePhone(DEFAULT_PHONE);
        employeeEmail = new EmployeeEmail(DEFAULT_EMAIL);
        employeeAddress = new EmployeeAddress(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code employeeToCopy}.
     */
    public PersonBuilder(Employee employeeToCopy) {
        employeeName = employeeToCopy.getEmployeeName();
        employeePhone = employeeToCopy.getEmployeePhone();
        employeeEmail = employeeToCopy.getEmployeeEmail();
        employeeAddress = employeeToCopy.getEmployeeAddress();
        tags = new HashSet<>(employeeToCopy.getTags());
    }

    /**
     * Sets the {@code EmployeeName} of the {@code Employee} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.employeeName = new EmployeeName(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Employee} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code EmployeeAddress} of the {@code Employee} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.employeeAddress = new EmployeeAddress(address);
        return this;
    }

    /**
     * Sets the {@code EmployeePhone} of the {@code Employee} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.employeePhone = new EmployeePhone(phone);
        return this;
    }

    /**
     * Sets the {@code EmployeeEmail} of the {@code Employee} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.employeeEmail = new EmployeeEmail(email);
        return this;
    }

    public Employee build() {
        return new Employee(employeeName, employeePhone, employeeEmail, employeeAddress, tags);
    }

}
