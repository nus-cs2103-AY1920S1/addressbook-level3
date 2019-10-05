package seedu.address.model.entity.worker;

import java.util.Date;
import java.util.Objects;

import seedu.address.model.entity.Entity;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.Sex;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

//@@author shaoyi
/**
 * Represents a worker entry in Mortago.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Worker implements Entity {

    // Identity fields
    private final IdentificationNumber workerIdNum;
    private final Name name;
    private Phone phone;
    private Sex sex;

    // Data fields
    private Date dateOfBirth;
    private Date dateJoined;
    private String designation;
    private String employmentStatus;

    public Worker(Name name, Phone phone, Sex sex, String employmentStatus, Date dateOfBirth, Date dateJoined,
                  String designation) {
        this.workerIdNum = IdentificationNumber.generateNewWorkerId();
        this.name = name;
        this.phone = phone;
        this.sex = sex;
        this.employmentStatus = employmentStatus;
        this.dateOfBirth = dateOfBirth;
        this.dateJoined = dateJoined;
        this.designation = designation;
    }

    public Worker(Name name, Phone phone, Sex sex, String employmentStatus, Date dateOfBirth, Date dateJoined,
                  String designation, boolean isTestWorker) {
        if (isTestWorker) {
            this.workerIdNum = IdentificationNumber.customGenerateId("W", 1);
        } else {
            this.workerIdNum = IdentificationNumber.generateNewWorkerId();
        }
        this.name = name;
        this.phone = phone;
        this.sex = sex;
        this.employmentStatus = employmentStatus;
        this.dateOfBirth = dateOfBirth;
        this.dateJoined = dateJoined;
        this.designation = designation;
    }


    public IdentificationNumber getWorkerIdNum() {
        return workerIdNum;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Sex getSex() {
        return sex;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public String getDesignation() {
        return designation;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    /**
     * Returns true if both workers have the same identity fields.
     * This defines a weaker notion of equality between two workers.
     */
    public boolean isSamePerson(Worker otherWorker) {
        if (otherWorker == this) {
            return true;
        }

        return otherWorker != null
            && otherWorker.getName().equals(getName())
            && otherWorker.getSex().equals(getSex())
            && (otherWorker.getPhone().equals(getPhone()));
    }

    @Override
    public boolean isSameEntity(Object o) {
        return isSameEntity(o);
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
            && otherPerson.getPhone().equals(getPhone())
            && otherPerson.getSex().equals(getSex());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, sex);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
            .append(" Sex: ")
            .append(getSex())
            .append(" Phone: ")
            .append(getPhone())
            .append(" Date of Birth: ")
            .append(getDateOfBirth())
            .append(" Date Joined: ")
            .append(getDateJoined())
            .append(" Designation: ")
            .append(getDesignation())
            .append(" Employment Status: ")
            .append(getEmploymentStatus());
        return builder.toString();
    }

}
