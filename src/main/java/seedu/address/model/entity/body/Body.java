package seedu.address.model.entity.body;

import seedu.address.commons.core.index.Index;
import seedu.address.model.entity.PhoneNumber;
import seedu.address.model.entity.Sex;
import seedu.address.model.person.Name; // Currently using AB3 Name class until further discussion.

//@@author ambervoong
/**
 * Represents a Person in the address book.
 * Guarantees: name, sex and dateofAdmission are guaranteed to be present.
 */
public class Body {
    private final BodyIdentificationNumber bodyIdentificationNumber;

    // Identity fields.
    // NOTE: Name details not yet finalised.
    private final Name name;

    private final Sex sex;
    private Nric nric;
    private Religion religion;

    private String causeOfDeath;
    private DonationList organsForDonation;
    // NOTE: Status details not yet finalised.
    private Status status;
    private Index fridgeId;
    private String details;

    // NOTE: Date details not yet finalised.
    private final String dateOfAdmission;
    private String dateOfBirth;
    private String dateOfDeath;

    // Next of kin details
    private Name nextOfKin;
    private Relationship relationship;
    private PhoneNumber kinPhoneNumber;

    public Body(BodyIdentificationNumber bodyIdentificationNumber, Name name, Sex sex, String dateOfAdmission) {
        this.bodyIdentificationNumber = bodyIdentificationNumber;
        this.name = name;
        this.sex = sex;
        this.dateOfAdmission = dateOfAdmission;
    }

    // Getters and Setters
    public BodyIdentificationNumber getBodyIdentificationNumber() {
        return bodyIdentificationNumber;
    }

    public Name getName() {
        return name;
    }

    public Sex getSex() {
        return sex;
    }

    public String getDateOfAdmission() {
        return dateOfAdmission;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(String dateOfDeath) {
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

    public Relationship getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship relationship) {
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

    public DonationList getOrgansForDonation() {
        return organsForDonation;
    }

    public void setOrgansForDonation(DonationList organsForDonation) {
        this.organsForDonation = organsForDonation;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Index getFridgeId() {
        return fridgeId;
    }

    public void setFridgeId(Index fridgeId) {
        this.fridgeId = fridgeId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}

