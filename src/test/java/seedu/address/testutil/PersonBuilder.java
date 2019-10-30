package seedu.address.testutil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.employee.Employee;
import seedu.address.model.employee.EmployeeAddress;
import seedu.address.model.employee.EmployeeEmail;
import seedu.address.model.employee.EmployeeGender;
import seedu.address.model.employee.EmployeeId;
import seedu.address.model.employee.EmployeeJoinDate;
import seedu.address.model.employee.EmployeeName;
import seedu.address.model.employee.EmployeePay;
import seedu.address.model.employee.EmployeePendingPay;
import seedu.address.model.employee.EmployeePhone;
import seedu.address.model.employee.EmployeeTotalSalary;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Employee objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Pauline";
    public static final String DEFAULT_PAY = "0";
    public static final String DEFAULT_PENDINGPAY = "0";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_TOTALSALARY= "0";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private EmployeeName employeeName;
    private EmployeePay employeePay;
    private EmployeePendingPay employeePendingPay;
    private EmployeePhone employeePhone;
    private EmployeeTotalSalary employeeTotalSalary;
    private EmployeeEmail employeeEmail;
    private EmployeeAddress employeeAddress;
    private Set<Tag> tags;

    public PersonBuilder() {
        employeeName = new EmployeeName(DEFAULT_NAME);
        employeePay = new EmployeePay(DEFAULT_PAY);
        employeePendingPay = new EmployeePendingPay(DEFAULT_PENDINGPAY);
        employeePhone = new EmployeePhone(DEFAULT_PHONE);
        employeeTotalSalary = new EmployeeTotalSalary((DEFAULT_TOTALSALARY));
        employeeEmail = new EmployeeEmail(DEFAULT_EMAIL);
        employeeAddress = new EmployeeAddress(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code employeeToCopy}.
     */
    public PersonBuilder(Employee employeeToCopy) {
        employeeName = employeeToCopy.getEmployeeName();
        employeePay = employeeToCopy.getEmployeePay();
        employeePendingPay = employeeToCopy.getEmployeePendingPay();
        employeePhone = employeeToCopy.getEmployeePhone();
        employeeTotalSalary = employeeToCopy.getEmployeeTotalsalary();
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
     * Sets the {@code EmployeePhone} of the {@code Employee} that we are building.
     */
    public PersonBuilder withPay(String pay) {
        this.employeePay = new EmployeePay(pay);
        return this;
    }

    /**
     * Sets the {@code EmployeePhone} of the {@code Employee} that we are building.
     */
    public PersonBuilder withPendingPay(String pay) {
        this.employeePendingPay = new EmployeePendingPay(pay);
        return this;
    }

    /**
     * Sets the {@code EmployeePhone} of the {@code Employee} that we are building.
     */
    public PersonBuilder withTotalSalary(String pay) {
        this.employeeTotalSalary = new EmployeeTotalSalary(pay);
        return this;
    }

    /**
     * Sets the {@code EmployeeEmail} of the {@code Employee} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.employeeEmail = new EmployeeEmail(email);
        return this;
    }

    /**
     * creates an draft Employee Objects and returns it
     * @return Employee Object
     */
    public Employee build() {
        return new Employee(new EmployeeId("000"), employeeName, new EmployeeGender("male"),
                        new EmployeePay("0"), new EmployeePendingPay("0"), new EmployeeTotalSalary("0"), employeePhone,
                        employeeEmail, employeeAddress,
                        new EmployeeJoinDate(LocalDate.parse("11/12/2011",
                                DateTimeFormatter.ofPattern("dd/MM/yyyy"))), tags);

    }

}
