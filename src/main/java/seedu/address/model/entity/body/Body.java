package seedu.address.model.entity.body;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.model.entity.Entity;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.PhoneNumber;
import seedu.address.model.entity.Sex;
import seedu.address.model.entity.fridge.Fridge;
import seedu.address.model.person.Name;

//@@author ambervoong
/**
 * Represents a Body in Mortago.
 * Guarantees: dateofAdmission and bodyIdNum is guaranteed to be present.
 */
public class Body implements Entity {
    private final Date dateOfAdmission;
    private IdentificationNumber bodyIdNum;

    // Identity fields.
    private Name name;
    private Sex sex;
    private Optional<Nric> nric;
    private Optional<Religion> religion;

    private Optional<String> causeOfDeath;
    private List<String> organsForDonation;
    private Optional<BodyStatus> bodyStatus;
    private Optional<IdentificationNumber> fridgeId;
    private Optional<Date> dateOfBirth;
    private Optional<Date> dateOfDeath;

    // Next of kin details
    private Optional<Name> nextOfKin;
    private Optional<String> relationship;
    private Optional<PhoneNumber> kinPhoneNumber;

    public Body(Date dateOfAdmission) {
        this.dateOfAdmission = dateOfAdmission;
    }

    public Body(Date dateOfAdmission, Name name, Sex sex, Nric nric,
                Religion religion, String causeOfDeath, List<String> organsForDonation, BodyStatus bodyStatus,
                IdentificationNumber fridgeId, Date dateOfBirth, Date dateOfDeath, Name nextOfKin,
                String relationship, PhoneNumber kinPhoneNumber) {
        this.bodyIdNum = IdentificationNumber.generateNewBodyId(this);
        this.dateOfAdmission = dateOfAdmission;
        this.name = name;
        this.sex = sex;
        this.nric = Optional.ofNullable(nric);
        this.religion = Optional.ofNullable(religion);
        this.causeOfDeath = Optional.ofNullable(causeOfDeath);
        this.organsForDonation = organsForDonation;
        this.bodyStatus = Optional.ofNullable(bodyStatus);
        this.fridgeId = Optional.ofNullable(fridgeId);
        this.dateOfBirth = Optional.ofNullable(dateOfBirth);
        this.dateOfDeath = Optional.ofNullable(dateOfDeath);
        this.nextOfKin = Optional.ofNullable(nextOfKin);
        this.relationship = Optional.ofNullable(relationship);
        this.kinPhoneNumber = Optional.ofNullable(kinPhoneNumber);

        if (fridgeId != null) {
            Fridge fridge = (Fridge) fridgeId.getMapping();
            fridge.setBody(this);
        }
    }

    /**
     * Generates a new Body with bare-minimum attributes and a custom ID. Only used for creating a Body from storage.
     * @param id ID of the stored body.
     * @param dateOfAdmission of the stored body.
     * @return Body
     */
    public static Body generateNewStoredBody(int id, Date dateOfAdmission) {
        if (id <= 0 || dateOfAdmission == null) {
            throw new IllegalArgumentException();
        }
        Body body = new Body(dateOfAdmission);
        body.bodyIdNum = IdentificationNumber.generateNewBodyId(body, id);
        return body;
    }

    // Getters and Setters
    public IdentificationNumber getIdNum() {
        return bodyIdNum;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Date getDateOfAdmission() {
        return dateOfAdmission;
    }

    public Optional<Date> getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = Optional.ofNullable(dateOfBirth);
    }

    public Optional<Date> getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(Date dateOfDeath) {
        this.dateOfDeath = Optional.ofNullable(dateOfDeath);
    }

    public Optional<Nric> getNric() {
        return nric;
    }

    public void setNric(Nric nric) {
        this.nric = Optional.ofNullable(nric);
    }

    public Optional<Religion> getReligion() {
        return religion;
    }

    public void setReligion(Religion religion) {
        this.religion = Optional.ofNullable(religion);
    }

    public Optional<Name> getNextOfKin() {
        return nextOfKin;
    }

    public void setNextOfKin(Name nextOfKin) {
        this.nextOfKin = Optional.ofNullable(nextOfKin);
    }

    public Optional<String> getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = Optional.ofNullable(relationship);
    }

    public Optional<PhoneNumber> getKinPhoneNumber() {
        return kinPhoneNumber;
    }

    public void setKinPhoneNumber(PhoneNumber kinPhoneNumber) {
        this.kinPhoneNumber = Optional.ofNullable(kinPhoneNumber);
    }

    public Optional<String> getCauseOfDeath() {
        return causeOfDeath;
    }

    public void setCauseOfDeath(String causeOfDeath) {
        this.causeOfDeath = Optional.ofNullable(causeOfDeath);
    }

    public List<String> getOrgansForDonation() {
        return organsForDonation;
    }

    public void setOrgansForDonation(List<String> organsForDonation) {
        this.organsForDonation = organsForDonation;
    }

    public Optional<BodyStatus> getBodyStatus() {
        return bodyStatus;
    }

    public void setBodyStatus(BodyStatus bodyStatus) {
        this.bodyStatus = Optional.ofNullable(bodyStatus);
    }

    public Optional<IdentificationNumber> getFridgeId() {
        return fridgeId;
    }

    public void setFridgeId(IdentificationNumber fridgeId) {
        this.fridgeId = Optional.ofNullable(fridgeId);
    }

    /**
     * Returns whether another object is equal to this object. Equality is defined as having identical attributes. Null
     * objects are not considered equal.
     * @param o An object.
     * @return whether the object is equal to this object.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Body body = (Body) o;
        return getDateOfAdmission().equals(body.getDateOfAdmission())
                && getName().equals(body.getName())
                && getSex().equals(body.getSex())
                && getNric().equals(body.getNric())
                && getReligion().equals(body.getReligion())
                && getCauseOfDeath().equals(body.getCauseOfDeath())
                && getOrgansForDonation().equals(body.getOrgansForDonation())
                && getBodyStatus().equals(body.getBodyStatus())
                && getFridgeId().equals(body.getFridgeId())
                && getDateOfBirth().equals(body.getDateOfBirth())
                && getDateOfDeath().equals(body.getDateOfDeath())
                && getNextOfKin().equals(body.getNextOfKin())
                && getRelationship().equals(body.getRelationship())
                && getKinPhoneNumber().equals(body.getKinPhoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDateOfAdmission(), getName(), getSex(), getNric(),
                getReligion(), getCauseOfDeath(), getOrgansForDonation(), getBodyStatus(), getFridgeId(),
                getDateOfBirth(), getDateOfDeath(), getNextOfKin(), getRelationship(), getKinPhoneNumber());
    }

    /**
     * Returns whether an object is equal to this body. The definition of equality is relaxed here to only include
     * Nric.
     * @param o An object.
     * @return whether the object is equal to this object.
     */
    public boolean isSameBody(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Body body = (Body) o;
        return getNric().equals(body.getNric())
            && getName().equals(body.getName());
    }

    @Override
    public boolean isSameEntity(Object o) {
        return isSameBody(o);
    }

    /**
     * Returns whether an object is equal to this body. The definition of equality is relaxed here to only include
     * bodyIdNum.
     * @param o An object.
     * @return whether the object is equal to this object.
     */
    public boolean isSameBodyIdNum(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Body body = (Body) o;
        return this.getIdNum().equals(body.getIdNum());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
            .append(" Body ID: ")
            .append(getIdNum())
            .append(" Sex: ")
            .append(getSex())
            .append(" NRIC: ")
            .append(nric.isPresent() ? nric.get() : OPTIONAL_FIELD_EMPTY)
            .append(" Religion: ")
            .append(religion.isPresent() ? religion.get() : OPTIONAL_FIELD_EMPTY)
            .append(" Date of Admission: ")
            .append(dateOfAdmission)
            .append(" Date of Death: ")
            .append(dateOfDeath)
            .append(" Cause of Death: ")
            .append(causeOfDeath.isPresent() ? causeOfDeath.get() : OPTIONAL_FIELD_EMPTY)
            .append(" Date of Birth: ")
            .append(dateOfBirth.isPresent() ? dateOfBirth.get() : OPTIONAL_FIELD_EMPTY)
            .append(" Organs for Donation: ")
            .append(organsForDonation)
            .append(" Fridge ID: ")
            .append(fridgeId.isPresent() ? fridgeId.get() : OPTIONAL_FIELD_EMPTY)
            .append(" Body Status: ")
            .append(bodyStatus.isPresent() ? bodyStatus.get() : OPTIONAL_FIELD_EMPTY)
            .append(" Name of Next Of Kin: ")
            .append(nextOfKin.isPresent() ? nextOfKin.get() : OPTIONAL_FIELD_EMPTY)
            .append(" Relationship of Next of Kin: ")
            .append(relationship.isPresent() ? relationship.get() : OPTIONAL_FIELD_EMPTY)
            .append(" Phone of Next of Kin: ")
            .append(kinPhoneNumber.isPresent() ? kinPhoneNumber.get() : OPTIONAL_FIELD_EMPTY);
        return builder.toString();
    }
}

