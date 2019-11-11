package seedu.address.logic.parser.utility;

import static seedu.address.commons.core.Messages.MESSAGE_DATEJOINED_BEFORE_DOB;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.PhoneNumber;
import seedu.address.model.entity.Sex;
import seedu.address.model.entity.worker.Photo;
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
    private Photo photo;

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
        setPhoto(toCopy.photo);
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
        this.photo = worker.getPhoto().orElse(null);
    }

    /**
     * Checks whether two Dates make sense with each other, in the context of UpdateWorkerDescriptor.
     *
     * @param dateJoined the date the Worker joined the mortuary
     * @param dateOfBirth the Worker's date of birth
     * @return true if the dates make sense
     * @throws CommandException if the dates don't make sense
     */
    public static boolean checkDateSensibility(Date dateJoined, Date dateOfBirth) throws CommandException {
        if (dateJoined != null && dateOfBirth != null && dateJoined.before(dateOfBirth)) {
            throw new CommandException(MESSAGE_DATEJOINED_BEFORE_DOB);
        }
        return true;
    }

    @Override
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(phone, sex, dateJoined, dateOfBirth, designation, employmentStatus, photo);
    }

    @Override
    public Entity apply(Entity entity) throws CommandException {
        assert entity != null;
        Worker worker = (Worker) entity;
        Date dateJoined = this.getDateJoined().orElse(worker.getDateJoined());
        Date dateOfBirth = this.getDateOfBirth().orElse(worker.getDateOfBirth().orElse(null));
        checkDateSensibility(dateJoined, dateOfBirth);

        worker.setPhone(this.getPhone().orElse(worker.getPhone().orElse(null)));
        worker.setSex(this.getSex().orElse(worker.getSex()));
        worker.setDateOfBirth(dateOfBirth);
        worker.setDateJoined(dateJoined);
        worker.setDesignation(this.getDesignation().orElse(worker.getDesignation().orElse(null)));
        worker.setEmploymentStatus(this.getEmploymentStatus().orElse(worker.getEmploymentStatus().orElse(null)));
        worker.setPhoto(this.getPhoto().orElse(worker.getPhoto().orElse(null)));

        return entity;
    }

    @Override
    public Entity applyOriginal(Entity entity) {
        assert entity != null;
        Worker worker = (Worker) entity;
        worker.setPhone(this.getPhone().orElse(null));
        worker.setSex(this.getSex().orElse(null));
        worker.setDateOfBirth(this.getDateOfBirth().orElse(null));
        worker.setDateJoined(this.getDateJoined().orElse(null));
        worker.setDesignation(this.getDesignation().orElse(null));
        worker.setEmploymentStatus(this.getEmploymentStatus().orElse(null));
        worker.setPhoto(this.getPhoto().orElse(null));
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

    public Optional<Photo> getPhoto() {
        return Optional.ofNullable(photo);
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
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
        return getPhone().equals(that.getPhone())
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
