package seedu.address.model.entity.body;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import seedu.address.model.entity.Entity;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.PhoneNumber;
import seedu.address.model.entity.Sex;
import seedu.address.model.person.Name;

//@@author ambervoong
/**
 * Represents a Body in Mortago.
 * Guarantees: dateofAdmission and bodyIdNum is guaranteed to be present.
 */
public class Body implements Entity {
    private final IdentificationNumber bodyIdNum;
    private final Date dateOfAdmission;

    // Identity fields.
    private Name name;
    private Sex sex;
    private Nric nric;
    private Religion religion;

    private String causeOfDeath;
    private List<String> organsForDonation;
    private BodyStatus bodyStatus;
    private IdentificationNumber fridgeId;
    private Date dateOfBirth;
    private Date dateOfDeath;

    // Next of kin details
    private Name nextOfKin;
    private String relationship;
    private PhoneNumber kinPhoneNumber;

    public Body(Date dateOfAdmission) {
        this.bodyIdNum = IdentificationNumber.generateNewBodyId();
        this.dateOfAdmission = dateOfAdmission;
    }

    public Body(boolean isTestUnit, int identificationNumber, Date dateOfAdmission, Name name, Sex sex, Nric nric,
                Religion religion, String causeOfDeath, List<String> organsForDonation, BodyStatus bodyStatus,
                IdentificationNumber fridgeId, Date dateOfBirth, Date dateOfDeath, Name nextOfKin,
                String relationship, PhoneNumber kinPhoneNumber) {
        if (isTestUnit) {
            this.bodyIdNum = IdentificationNumber.customGenerateId("B",
                    identificationNumber);
        } else {
            this.bodyIdNum = IdentificationNumber.generateNewBodyId();
        }
        this.dateOfAdmission = dateOfAdmission;
        this.name = name;
        this.sex = sex;
        this.nric = nric;
        this.religion = religion;
        this.causeOfDeath = causeOfDeath;
        this.organsForDonation = organsForDonation;
        this.bodyStatus = bodyStatus;
        this.fridgeId = fridgeId;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.nextOfKin = nextOfKin;
        this.relationship = relationship;
        this.kinPhoneNumber = kinPhoneNumber;
    }

    // Getters and Setters
    public IdentificationNumber getBodyIdNum() {
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(Date dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public Nric getNric() {
        return nric;
    }

    public void setNric(Nric nric) {
        this.nric = nric;
    }

    public Religion getReligion() {
        return religion;
    }

    public void setReligion(Religion religion) {
        this.religion = religion;
    }

    public Name getNextOfKin() {
        return nextOfKin;
    }

    public void setNextOfKin(Name nextOfKin) {
        this.nextOfKin = nextOfKin;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public PhoneNumber getKinPhoneNumber() {
        return kinPhoneNumber;
    }

    public void setKinPhoneNumber(PhoneNumber kinPhoneNumber) {
        this.kinPhoneNumber = kinPhoneNumber;
    }

    public String getCauseOfDeath() {
        return causeOfDeath;
    }

    public void setCauseOfDeath(String causeOfDeath) {
        this.causeOfDeath = causeOfDeath;
    }

    public List<String> getOrgansForDonation() {
        return organsForDonation;
    }

    public void setOrgansForDonation(List<String> organsForDonation) {
        this.organsForDonation = organsForDonation;
    }

    public BodyStatus getBodyStatus() {
        return bodyStatus;
    }

    public void setBodyStatus(BodyStatus bodyStatus) {
        this.bodyStatus = bodyStatus;
    }

    public IdentificationNumber getFridgeId() {
        return fridgeId;
    }

    public void setFridgeId(IdentificationNumber fridgeId) {
        this.fridgeId = fridgeId;
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
        return getBodyIdNum().equals(body.getBodyIdNum())
                && getDateOfAdmission().equals(body.getDateOfAdmission())
                && Objects.equals(getName(), body.getName())
                && getSex() == body.getSex()
                && Objects.equals(getNric(), body.getNric())
                && getReligion() == body.getReligion()
                && Objects.equals(getCauseOfDeath(), body.getCauseOfDeath())
                && Objects.equals(getOrgansForDonation(), body.getOrgansForDonation())
                && getBodyStatus() == body.getBodyStatus()
                && Objects.equals(getFridgeId(), body.getFridgeId())
                && Objects.equals(getDateOfBirth(), body.getDateOfBirth())
                && Objects.equals(getDateOfDeath(), body.getDateOfDeath())
                && Objects.equals(getNextOfKin(), body.getNextOfKin())
                && Objects.equals(getRelationship(), body.getRelationship())
                && Objects.equals(getKinPhoneNumber(), body.getKinPhoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBodyIdNum(), getDateOfAdmission(), getName(), getSex(), getNric(),
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
        return Objects.equals(getNric(), body.getNric());
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
        return getBodyIdNum().equals(body.getBodyIdNum());
    }
}

