package seedu.address.model.entity.worker;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import seedu.address.model.entity.Entity;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.PhoneNumber;
import seedu.address.model.entity.Sex;
import seedu.address.model.person.Name;

//@@author shaoyi1997
/**
 * Represents a worker entry in Mortago.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Worker implements Entity {

    // Identity fields
    private final Name name;
    private Sex sex;
    private IdentificationNumber workerIdNum;
    private Date dateJoined;

    // Data fields
    private Optional<String> designation;
    private Optional<Date> dateOfBirth;
    private Optional<PhoneNumber> phone;
    private Optional<String> employmentStatus;
    private Optional<Photo> photo;

    public Worker(Name name, PhoneNumber phone, Sex sex, String employmentStatus, Date dateOfBirth, Date dateJoined,
                  String designation, Photo photo) {
        this.workerIdNum = IdentificationNumber.generateNewWorkerId(this);
        this.name = name;
        this.sex = sex;
        this.dateJoined = dateJoined;
        this.phone = Optional.ofNullable(phone);
        this.employmentStatus = Optional.ofNullable(employmentStatus);
        this.dateOfBirth = Optional.ofNullable(dateOfBirth);
        this.designation = Optional.ofNullable(designation);
        this.photo = Optional.ofNullable(photo);
    }

    public Worker(Name name, Sex sex, Date dateJoined) {
        this.name = name;
        this.sex = sex;
        this.dateJoined = dateJoined;
        this.employmentStatus = Optional.empty();
        this.designation = Optional.empty();
        this.phone = Optional.empty();
        this.dateOfBirth = Optional.empty();
        this.photo = Optional.empty();
    }

    //@@author ambervoong
    /**
     * Generates a new Worker with bare-minimum attributes and a custom ID. Only used for creating a Worker
     * from storage.
     * @param name name of the stored worker.
     * @param sex sex of the stored worker.
     * @param dateJoined dateJoined of the stored worker.
     * @param id ID of the stored worker.
     * @return Worker
     */
    public static Worker generateNewStoredWorker(Name name, Sex sex, Date dateJoined, int id) {
        requireAllNonNull(name, sex, dateJoined, id);
        if (id <= 0) {
            throw new IllegalArgumentException();
        }
        Worker worker = new Worker(name, sex, dateJoined);
        worker.workerIdNum = IdentificationNumber.generateNewWorkerId(worker, id);
        return worker;
    }
    //@@author

    public IdentificationNumber getIdNum() {
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

    public Optional<Photo> getPhoto() {
        return photo;
    }

    public void setPhone(PhoneNumber phone) {
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

    public void setPhoto(Photo photo) {
        this.photo = Optional.ofNullable(photo);
    }

    /**
     * Returns true if both workers have the same identity fields.
     * This defines a weaker notion of equality between two workers.
     */
    public boolean isSameWorker(Object o) {
        if (o == this) {
            return true;
        } else if (o instanceof Worker) {
            Worker otherWorker = (Worker) o;
            return otherWorker != null
                && otherWorker.getName().equals(getName())
                && otherWorker.getSex().equals(getSex())
                && otherWorker.getDateJoined().equals(getDateJoined());
        } else {
            return false;
        }
    }

    @Override
    public boolean isSameEntity(Object o) {
        return isSameWorker(o);
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
            && otherPerson.getDesignation().equals(getDesignation())
            && otherPerson.getPhoto().equals(getPhoto());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, sex, employmentStatus, dateJoined, dateOfBirth, designation);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
            .append(" Worker ID: ")
            .append(getIdNum())
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
//@@author
