package seedu.address.logic.parser.utility;

import static seedu.address.commons.core.Messages.MESSAGE_DOA_BEFORE_DOB;
import static seedu.address.commons.core.Messages.MESSAGE_DOA_BEFORE_DOD;
import static seedu.address.commons.core.Messages.MESSAGE_DOD_BEFORE_DOB;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.PhoneNumber;
import seedu.address.model.entity.Sex;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.body.BodyStatus;
import seedu.address.model.entity.body.Nric;
import seedu.address.model.person.Name;

//@@author ambervoong

/**
 * Stores the details to update the body with. Each non-empty field value will replace the
 * corresponding field value of the body when applied.
 */
public class UpdateBodyDescriptor implements UpdateEntityDescriptor {
    private Name name;
    private Sex sex;
    private Nric nric;
    private String religion;

    private String causeOfDeath;
    private List<String> organsForDonation;
    private BodyStatus bodyStatus;
    private IdentificationNumber fridgeId;
    private Date dateOfBirth;
    private Date dateOfDeath;

    private Name nextOfKin;
    private String relationship;
    private PhoneNumber kinPhoneNumber;

    private String details;

    public UpdateBodyDescriptor() {

    }

    /**
     * Makes a copy of an existing UpdateBodyDescriptor.
     */
    public UpdateBodyDescriptor(UpdateBodyDescriptor toCopy) {
        setName(toCopy.name);
        setSex(toCopy.sex);
        setNric(toCopy.nric);
        setReligion(toCopy.religion);
        setCauseOfDeath(toCopy.causeOfDeath);
        setOrgansForDonation(toCopy.organsForDonation);
        setBodyStatus(toCopy.bodyStatus);
        setFridgeId(toCopy.fridgeId);
        setDateOfBirth(toCopy.dateOfBirth);
        setDateOfDeath(toCopy.dateOfDeath);
        setNextOfKin(toCopy.nextOfKin);
        setRelationship(toCopy.relationship);
        setKinPhoneNumber(toCopy.kinPhoneNumber);
        setDetails(toCopy.details);
    }

    /**
     * Makes a copy of a Body's current fields.
     *
     * @param body the body to be copied.
     * @returns UpdateBodyDescriptor that contains the body's current fields.
     */
    public UpdateBodyDescriptor(Body body) {
        this.name = body.getName();
        this.sex = body.getSex();
        this.nric = body.getNric().orElse(null);
        this.religion = body.getReligion().orElse(null);
        this.causeOfDeath = body.getCauseOfDeath().orElse(null);
        this.organsForDonation = body.getOrgansForDonation();
        this.bodyStatus = body.getBodyStatus().orElse(null);
        this.fridgeId = body.getFridgeId().orElse(null);
        this.dateOfBirth = body.getDateOfBirth().orElse(null);
        this.dateOfDeath = body.getDateOfDeath().orElse(null);
        this.nextOfKin = body.getNextOfKin().orElse(null);
        this.relationship = body.getRelationship().orElse(null);
        this.kinPhoneNumber = body.getKinPhoneNumber().orElse(null);
        this.details = body.getDetails().orElse(null);
    }

    /**
     * Checks whether two Dates logically make sense with each other, in the context of UpdateBodyDescriptor.
     * For instance, the dateOfBirth of a Body should not be after the dateOfAdmission.
     *
     * @param dateOfAdmission the body's date of admission
     * @param dateOfDeath     the body's date of death
     * @param dateOfBirth     the body's date of birth
     * @return true if the 3 dates make sense in the context of a body.
     * @throws CommandException if any one of the 3 dates do not make sense.
     */
    public static boolean checkDateSensibility(Date dateOfAdmission, Date dateOfDeath, Date dateOfBirth)
            throws CommandException {
        assert dateOfAdmission != null;
        if (dateOfDeath != null && dateOfAdmission.before(dateOfDeath)) {
            throw new CommandException(MESSAGE_DOA_BEFORE_DOD);
        } else if (dateOfBirth != null && dateOfAdmission.before(dateOfBirth)) {
            throw new CommandException(MESSAGE_DOA_BEFORE_DOB);
        } else if (dateOfDeath != null && dateOfBirth != null && dateOfDeath.before(dateOfBirth)) {
            throw new CommandException(MESSAGE_DOD_BEFORE_DOB);
        }
        return true;
    }

    /**
     * Returns true if at least one field is edited.
     */
    @Override
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(name, sex, nric, religion, causeOfDeath, organsForDonation, bodyStatus,
                fridgeId, dateOfBirth, dateOfDeath, nextOfKin, relationship, kinPhoneNumber, details);
    }

    /**
     * Changes a {@code Body}'s fields according to the descriptor to the updated values in the
     * {@code UpdateEntityDescriptor} object if they are present and uses the existing values in the body otherwise.
     * Guarantees: the given entity exists.
     */
    @Override
    public Entity apply(Entity entity) throws CommandException {
        assert entity != null;
        Body body = (Body) entity;

        Date dateOfBirth = this.getDateOfBirth().orElse(body.getDateOfBirth().orElse(null));
        Date dateOfDeath = this.getDateOfDeath().orElse(body.getDateOfDeath().orElse(null));
        checkDateSensibility(body.getDateOfAdmission(), dateOfDeath, dateOfBirth);

        body.setName(this.getName().orElse(body.getName()));
        body.setSex(this.getSex().orElse(body.getSex()));
        body.setNric(this.getNric().orElse(body.getNric().orElse(null)));
        body.setReligion(this.getReligion().orElse(body.getReligion().orElse(null)));
        body.setCauseOfDeath(this.getCauseOfDeath().orElse(body.getCauseOfDeath().orElse(null)));
        body.setOrgansForDonation(this.getOrgansForDonation().orElse(body.getOrgansForDonation()));
        body.setBodyStatus(this.getBodyStatus().orElse(body.getBodyStatus().orElse(null)));
        body.setFridgeId(this.getFridgeId().orElse(body.getFridgeId().orElse(null)));
        body.setDateOfBirth(dateOfBirth);
        body.setDateOfDeath(dateOfDeath);
        body.setNextOfKin(this.getNextOfKin().orElse(body.getNextOfKin().orElse(null)));
        body.setRelationship(this.getRelationship().orElse(body.getRelationship().orElse(null)));
        body.setKinPhoneNumber(this.getKinPhoneNumber().orElse(body.getKinPhoneNumber().orElse(null)));
        body.setDetails(this.getDetails().orElse(body.getDetails().orElse(null)));
        return entity;
    }

    @Override
    public Entity applyOriginal(Entity entity) {
        assert entity != null;
        Body body = (Body) entity;
        body.setName(this.getName().orElse(null));
        body.setSex(this.getSex().orElse(null));
        body.setNric(this.getNric().orElse(null));
        body.setReligion(this.getReligion().orElse(null));
        body.setCauseOfDeath(this.getCauseOfDeath().orElse(null));
        body.setOrgansForDonation(this.getOrgansForDonation().orElse(null));
        body.setBodyStatus(this.getBodyStatus().orElse(null));
        body.setFridgeId(this.getFridgeId().orElse(null));
        body.setDateOfBirth(this.getDateOfBirth().orElse(null));
        body.setDateOfDeath(this.getDateOfDeath().orElse(null));
        body.setNextOfKin(this.getNextOfKin().orElse(null));
        body.setRelationship(this.getRelationship().orElse(null));
        body.setKinPhoneNumber(this.getKinPhoneNumber().orElse(null));
        body.setDetails(this.getDetails().orElse(null));
        return entity;
    }

    // Getters and Setters
    public Optional<Name> getName() {
        return Optional.ofNullable(name);
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Optional<Sex> getSex() {
        return Optional.ofNullable(sex);
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Optional<Nric> getNric() {
        return Optional.ofNullable(nric);
    }

    public void setNric(Nric nric) {
        this.nric = nric;
    }

    public Optional<String> getReligion() {
        return Optional.ofNullable(religion);
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public Optional<String> getCauseOfDeath() {
        return Optional.ofNullable(causeOfDeath);
    }

    public void setCauseOfDeath(String causeOfDeath) {
        this.causeOfDeath = causeOfDeath;
    }

    public Optional<List<String>> getOrgansForDonation() {
        return Optional.ofNullable(organsForDonation);
    }

    public void setOrgansForDonation(List<String> organsForDonation) {
        this.organsForDonation = organsForDonation;
    }

    public Optional<BodyStatus> getBodyStatus() {
        return Optional.ofNullable(bodyStatus);
    }

    public void setBodyStatus(BodyStatus bodyStatus) {
        this.bodyStatus = bodyStatus;
    }

    public Optional<IdentificationNumber> getFridgeId() {
        return Optional.ofNullable(fridgeId);
    }

    public void setFridgeId(IdentificationNumber fridgeId) {
        this.fridgeId = fridgeId;
    }

    public Optional<Date> getDateOfBirth() {
        return Optional.ofNullable(dateOfBirth);
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Optional<Date> getDateOfDeath() {
        return Optional.ofNullable(dateOfDeath);
    }

    public void setDateOfDeath(Date dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public Optional<Name> getNextOfKin() {
        return Optional.ofNullable(nextOfKin);
    }

    public void setNextOfKin(Name nextOfKin) {
        this.nextOfKin = nextOfKin;
    }

    public Optional<String> getRelationship() {
        return Optional.ofNullable(relationship);
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public Optional<PhoneNumber> getKinPhoneNumber() {
        return Optional.ofNullable(kinPhoneNumber);
    }

    public void setKinPhoneNumber(PhoneNumber kinPhoneNumber) {
        this.kinPhoneNumber = kinPhoneNumber;
    }

    public Optional<String> getDetails() {
        return Optional.ofNullable(details);
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UpdateBodyDescriptor)) {
            return false;
        }
        UpdateBodyDescriptor that = (UpdateBodyDescriptor) o;
        return getName().equals(that.getName())
                && getSex().equals(that.getSex())
                && getNric().equals(that.getNric())
                && getReligion().equals(that.getReligion())
                && getCauseOfDeath().equals(that.getCauseOfDeath())
                && getOrgansForDonation().equals(that.getOrgansForDonation())
                && getBodyStatus().equals(that.getBodyStatus())
                && getFridgeId().equals(that.getFridgeId())
                && getDateOfBirth().equals(that.getDateOfBirth())
                && getDateOfDeath().equals(that.getDateOfDeath())
                && getNextOfKin().equals(that.getNextOfKin())
                && getRelationship().equals(that.getRelationship())
                && getKinPhoneNumber().equals(that.getKinPhoneNumber());

    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSex(), getNric(), getReligion(), getCauseOfDeath(), getOrgansForDonation(),
                getBodyStatus(), getFridgeId(), getDateOfBirth(), getDateOfDeath(), getNextOfKin(), getRelationship(),
                getKinPhoneNumber());
    }
}
