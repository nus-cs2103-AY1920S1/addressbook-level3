package seedu.address.logic.parser.utility;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.PhoneNumber;
import seedu.address.model.entity.Sex;
import seedu.address.model.entity.worker.Worker;

//@@author ambervoong
/**
 * Stores the details to update the worker with. Each non-empty field value will replace the
 * corresponding field value of the worker.
 */
public class UpdateWorkerDescriptor implements UpdateEntityDescriptor {

    private PhoneNumber phone;
    private Sex sex;
    private Date dateOfBirth;
    private Date dateJoined;
    private String designation;
    private String employmentStatus;

    public UpdateWorkerDescriptor() {
    }

    /**
     * Makes a copy of an existing UpdateWorkerDescriptor.
     */
    public UpdateWorkerDescriptor(UpdateWorkerDescriptor toCopy) {
        setSex(toCopy.sex);
        setPhone(toCopy.phone);
        setDateOfBirth(toCopy.dateOfBirth);
        setDateJoined(toCopy.dateJoined);
        setDesignation(toCopy.designation);
        setEmploymentStatus(toCopy.employmentStatus);
    }

    /**
     * Makes a copy of a Worker's current fields.
     * @param worker the worker to be copied.
     * @returns UpdateWorkerDescriptor that contains the worker's current fields.
     */
    public UpdateWorkerDescriptor(Worker worker) {
        this.sex = worker.getSex();
        this.phone = worker.getPhone().orElse(null);
        this.dateOfBirth = worker.getDateOfBirth().orElse(null);
        this.dateJoined = worker.getDateJoined();
        this.designation = worker.getDesignation().orElse(null);
        this.employmentStatus = worker.getEmploymentStatus().orElse(null);
    }

    @Override
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(phone, sex, dateJoined, dateOfBirth, designation, employmentStatus);
    }

    @Override
    public Entity apply(Entity entity) {
        assert entity != null;
        Worker worker = (Worker) entity;
        worker.setPhone(this.getPhone().orElse(worker.getPhone().orElse(null)));
        worker.setSex(this.getSex().orElse(worker.getSex()));
        worker.setDateOfBirth(this.getDateOfBirth().orElse(worker.getDateOfBirth().orElse(null)));
        worker.setDateJoined(this.getDateJoined().orElse(worker.getDateJoined()));
        worker.setDesignation(this.getDesignation().orElse(worker.getDesignation().orElse(null)));
        worker.setEmploymentStatus(this.getEmploymentStatus().orElse(worker.getEmploymentStatus().orElse(null)));
        return entity;
    }

    // Getters and Setters
    public Optional<PhoneNumber> getPhone() {
        return Optional.ofNullable(phone);
    }

    public void setPhone(PhoneNumber phone) {
        this.phone = phone;
    }

    public Optional<Sex> getSex() {
        return Optional.ofNullable(sex);
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Optional<Date> getDateOfBirth() {
        return Optional.ofNullable(dateOfBirth);
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Optional<Date> getDateJoined() {
        return Optional.ofNullable(dateJoined);
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

    public Optional<String> getDesignation() {
        return Optional.ofNullable(designation);
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Optional<String> getEmploymentStatus() {
        return Optional.ofNullable(employmentStatus);
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UpdateWorkerDescriptor)) {
            return false;
        }
        UpdateWorkerDescriptor that = (UpdateWorkerDescriptor) o;
        return Objects.equals(getPhone(), that.getPhone())
                && getSex().equals(that.getSex())
                && getDateOfBirth().equals(that.getDateOfBirth())
                && getDateJoined().equals(that.getDateJoined())
                && getDesignation().equals(that.getDesignation())
                && getEmploymentStatus().equals(that.getEmploymentStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPhone(), getSex(), getDateOfBirth(), getDateJoined(), getDesignation(),
                getEmploymentStatus());
    }
}
