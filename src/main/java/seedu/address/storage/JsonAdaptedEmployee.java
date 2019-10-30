package seedu.address.storage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.EmployeeAddress;
import seedu.address.model.employee.EmployeeEmail;
import seedu.address.model.employee.EmployeeGender;
import seedu.address.model.employee.EmployeeId;
import seedu.address.model.employee.EmployeeJoinDate;
import seedu.address.model.employee.EmployeeName;
import seedu.address.model.employee.EmployeePay;
import seedu.address.model.employee.EmployeePendingPay;
import seedu.address.model.employee.EmployeeTotalSalary;
import seedu.address.model.employee.EmployeePhone;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Employee}.
 */
class JsonAdaptedEmployee {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Employee's %s field is missing!";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String id;
    private final String pay;
    private final String pendingpay;
    private final String totalsalary;
    private final String gender;
    private final String joindate;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedEmployee} with the given employee details.
     */
    @JsonCreator
    public JsonAdaptedEmployee(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                               @JsonProperty("email") String email, @JsonProperty("address") String address,
                               @JsonProperty("id") String id, @JsonProperty("Pay") String pay,
                               @JsonProperty("PendingPay") String pendingpay, @JsonProperty("TotalSalary") String totalsalary,
                               @JsonProperty("gender") String gender, @JsonProperty("joindate") String joindate,
                               @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.id = id;
        this.joindate = joindate;
        this.gender = gender;
        this.pay = pay;
        this.pendingpay = pendingpay;
        this.totalsalary = totalsalary;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    public JsonAdaptedEmployee(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                               @JsonProperty("email") String email, @JsonProperty("address") String address,
                               @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.id = "000";
        this.joindate = "11/11/2011";
        this.gender = "male";
        this.pay = "0";
        this.pendingpay = "0";
        this.totalsalary = "0";
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Employee} into this class for Jackson use.
     */
    public JsonAdaptedEmployee(Employee source) {
        name = source.getEmployeeName().fullName;
        phone = source.getEmployeePhone().value;
        email = source.getEmployeeEmail().value;
        address = source.getEmployeeAddress().value;
        id = source.getEmployeeId().id;
        pay = source.getEmployeePay().value;
        pendingpay = source.getEmployeePendingPay().value;
        totalsalary = source.getEmployeeTotalsalary().value;
        gender = source.getEmployeeGender().gender;
        joindate = source.getEmployeeJoinDate().toString();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted employee object into the model's {@code Employee} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted employee.
     */
    public Employee toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EmployeeId.class.getSimpleName()));
        }
        if (!EmployeeId.isValidId(id)) {
            throw new IllegalValueException(EmployeeId.MESSAGE_CONSTRAINTS);
        }
        final EmployeeId modelEmployeeId = new EmployeeId(id);

        if (!EmployeePay.isValidPay(pay)) {
            throw new IllegalValueException(EmployeePay.MESSAGE_CONSTRAINTS);
        }
        final EmployeePay modelEmployeePay = new EmployeePay(pay);

        if (!EmployeePendingPay.isValidPay(pendingpay)) {
            throw new IllegalValueException(EmployeePendingPay.MESSAGE_CONSTRAINTS);
        }
        final EmployeePendingPay modelEmployeePendingPay = new EmployeePendingPay(pendingpay);

        if (!EmployeeTotalSalary.isValidPay(totalsalary)) {
            throw new IllegalValueException(EmployeeTotalSalary.MESSAGE_CONSTRAINTS);
        }
        final EmployeeTotalSalary modelEmployeeTotalSalary = new EmployeeTotalSalary(totalsalary);

        if (gender == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EmployeeGender.class.getSimpleName()));
        }
        if (!EmployeeGender.isValidGender(gender)) {
            throw new IllegalValueException(EmployeeGender.MESSAGE_CONSTRAINTS);
        }
        final EmployeeGender modelEmployeeGender = new EmployeeGender(gender);

        if (joindate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EmployeeJoinDate.class.getSimpleName()));
        }
        if (!EmployeeJoinDate.isValidJoinDate(joindate)) {
            throw new IllegalValueException(EmployeeJoinDate.MESSAGE_CONSTRAINTS);
        }
        LocalDate newJoinDate = LocalDate.parse(joindate, FORMATTER);
        final EmployeeJoinDate modelEmployeeJoinDate = new EmployeeJoinDate(newJoinDate);

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EmployeeName.class.getSimpleName()));
        }
        if (!EmployeeName.isValidName(name)) {
            throw new IllegalValueException(EmployeeName.MESSAGE_CONSTRAINTS);
        }
        final EmployeeName modelEmployeeName = new EmployeeName(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EmployeePhone.class.getSimpleName()));
        }
        if (!EmployeePhone.isValidPhone(phone)) {
            throw new IllegalValueException(EmployeePhone.MESSAGE_CONSTRAINTS);
        }
        final EmployeePhone modelEmployeePhone = new EmployeePhone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EmployeeEmail.class.getSimpleName()));
        }
        if (!EmployeeEmail.isValidEmail(email)) {
            throw new IllegalValueException(EmployeeEmail.MESSAGE_CONSTRAINTS);
        }
        final EmployeeEmail modelEmployeeEmail = new EmployeeEmail(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EmployeeAddress.class.getSimpleName()));
        }
        if (!EmployeeAddress.isValidAddress(address)) {
            throw new IllegalValueException(EmployeeAddress.MESSAGE_CONSTRAINTS);
        }
        final EmployeeAddress modelEmployeeAddress = new EmployeeAddress(address);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Employee(modelEmployeeId, modelEmployeeName, modelEmployeeGender, modelEmployeePay,
                modelEmployeePendingPay,modelEmployeeTotalSalary,
                modelEmployeePhone, modelEmployeeEmail, modelEmployeeAddress, modelEmployeeJoinDate, modelTags);
    }

}
