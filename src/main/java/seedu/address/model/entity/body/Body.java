package seedu.address.model.entity.body;

import java.util.ArrayList;
import java.util.Date;

import seedu.address.commons.core.index.Index;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.PhoneNumber;
import seedu.address.model.entity.Sex;
import seedu.address.model.person.Name;

//@@author ambervoong
/**
 * Represents a Body in Mortago.
 * Guarantees: dateofAdmission is guaranteed to be present.
 */
public class Body {
    private final IdentificationNumber bodyIdentificationNumber;
    private final Date dateOfAdmission;

    // Identity fields.
    // NOTE: Name details not yet finalised.
    private Name name;

    private Sex sex;
    private Nric nric;
    private Religion religion;

    private String causeOfDeath;
    private ArrayList<String> organsForDonation;
    private Status status;
    private Index fridgeId;
    private String details;

    private Date dateOfBirth;
    private Date dateOfDeath;

    // Next of kin details
    private Name nextOfKin;
    private String relationship;
    private PhoneNumber kinPhoneNumber;

    public Body(Date dateOfAdmission) {
        this.bodyIdentificationNumber = IdentificationNumber.generateNewBodyId();
        this.dateOfAdmission = dateOfAdmission;
    }

    // Getters and Setters
    public IdentificationNumber getBodyIdentificationNumber() {
        return bodyIdentificationNumber;
    }

    public Name getName() {
        return name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setName(Name name) {
        this.name = name;
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

    public ArrayList<String> getOrgansForDonation() {
        return organsForDonation;
    }

    public void setOrgansForDonation(ArrayList<String> organsForDonation) {
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

