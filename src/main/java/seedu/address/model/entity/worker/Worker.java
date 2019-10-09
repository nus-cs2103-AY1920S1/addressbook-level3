package seedu.address.model.entity.worker;

import static java.util.Objects.requireNonNull;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import seedu.address.model.entity.Entity;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.PhoneNumber;
import seedu.address.model.entity.Sex;
import seedu.address.model.person.Name;

//@@author shaoyi
/**
 * Represents a worker entry in Mortago.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Worker implements Entity {

    // Identity fields
    private final IdentificationNumber workerIdNum;
    private final Name name;
    private Sex sex;

    // Data fields
    private Date dateJoined;
    private Optional<String> designation;
    private Optional<Date> dateOfBirth;
    private Optional<PhoneNumber> phone;
    private Optional<String> employmentStatus;

    public Worker(Name name, PhoneNumber phone, Sex sex, String employmentStatus, Date dateOfBirth, Date dateJoined,
                  String designation) {
        this.workerIdNum = IdentificationNumber.generateNewWorkerId();
        this.name = name;
        this.phone = Optional.ofNullable(phone);
        this.sex = sex;
        this.employmentStatus = Optional.ofNullable(employmentStatus);
        this.dateOfBirth = Optional.ofNullable(dateOfBirth);
        this.dateJoined = dateJoined;
        this.designation = Optional.ofNullable(designation);
    }

    public Worker(Name name, PhoneNumber phone, Sex sex, String employmentStatus, Date dateOfBirth, Date dateJoined,
                  String designation, boolean isTestWorker) {
        if (isTestWorker) {
            this.workerIdNum = IdentificationNumber.customGenerateId("W", 1);
        } else {
            this.workerIdNum = IdentificationNumber.generateNewWorkerId();
        }
        this.name = name;
        this.phone = Optional.ofNullable(phone);
        this.sex = sex;
        this.employmentStatus = Optional.ofNullable(employmentStatus);
        this.dateOfBirth = Optional.ofNullable(dateOfBirth);
        this.dateJoined = dateJoined;
        this.designation = Optional.ofNullable(designation);
    }


    public IdentificationNumber getWorkerIdNum() {
        return workerIdNum;
    }

    public Name getName() {
        return name;
    }

    public Optional<PhoneNumber> getPhone() {
        return phone;
    }

    public Sex getSex() {
        return sex;
    }

    public Optional<Date> getDateOfBirth() {
        return dateOfBirth;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public Optional<String> getDesignation() {
        return designation;
    }

    public Optional<String> getEmploymentStatus() {
        return employmentStatus;
    }

    public void setPhone(PhoneNumber phone) {
        requireNonNull(phone);
        this.phone = Optional.ofNullable(phone);
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = Optional.ofNullable(dateOfBirth);
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

    public void setDesignation(String designation) {
        this.designation = Optional.ofNullable(designation);
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = Optional.ofNullable(employmentStatus);
    }

    /**
     * Returns true if both workers have the same identity fields.
     * This defines a weaker notion of equality between two workers.
     */
    public boolean isSamePerson(Object o) {
        if (o == this) {
            return true;
        } else if (o instanceof Worker) {
            Worker otherWorker = (Worker) o;
            return otherWorker != null
                && otherWorker.getName().equals(getName())
                && otherWorker.getSex().equals(getSex())
                && (otherWorker.getPhone().equals(getPhone()));
        } else {
            return false;
        }
    }

    @Override
    public boolean isSameEntity(Object o) {
        return isSamePerson(o);
    }

    /**
     * Returns true if both workers have the same identity and data fields.
     * This defines a stronger notion of equality between two workers.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Worker)) {
            return false;
        }

        Worker otherPerson = (Worker) other;
        return otherPerson.getName().equals(getName())
            && otherPerson.getSex().equals(getSex())
            && otherPerson.getPhone().equals(getPhone())
            && otherPerson.getDateJoined().equals(getDateJoined())
            && otherPerson.getDateOfBirth().equals(getDateOfBirth())
            && otherPerson.getDesignation().equals(getDesignation());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, sex, workerIdNum, employmentStatus, dateJoined, dateOfBirth, designation);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
            .append(" Worker ID: ")
            .append(getWorkerIdNum())
            .append(" Sex: ")
            .append(getSex())
            .append(" Phone: ")
            .append(phone.isPresent() ? phone.get() : OPTIONAL_FIELD_EMPTY)
            .append(" Date of Birth: ")
            .append(dateOfBirth.isPresent() ? dateOfBirth.get() : OPTIONAL_FIELD_EMPTY)
            .append(" Date Joined: ")
            .append(getDateJoined())
            .append(" Designation: ")
            .append(designation.isPresent() ? designation.get() : OPTIONAL_FIELD_EMPTY)
            .append(" Employment Status: ")
            .append(employmentStatus.isPresent() ? employmentStatus.get() : OPTIONAL_FIELD_EMPTY);
        return builder.toString();
    }
}
