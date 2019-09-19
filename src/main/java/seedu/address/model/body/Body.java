package seedu.address.model.body;

import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Name; // Currently using AB3 Name class.

//@@author ambervoong
/**
 * Represents a Person in the address book.
 * Guarantees: name, sex and dateofAdmission are guaranteed to be present.
 */
public class Body {
    private final String id;

    // Identity fields.
    private final Name name;
    private final Name firstName;
    private final Name middleName;
    private final Name lastName;
    private final Sex sex;
    private final String dateOfAdmission;

    private String dateOfBirth;
    private String dateOfDeath;
    private Nric nric;
    private Religion religion;

    // Next of kin details
    private Name nextOfKin;
    private Relationship relationship;
    private PhoneNumber kinPhoneNumber;

    private CauseOfDeath cod;
    private DonationList organsForDonation;

    private Status status;
    private Index fridgeId;
    private String details;

    public Body(String id, Name name, Name firstName, Name middleName, Name lastName, Sex sex, String dateOfAdmission) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.sex = sex;
        this.dateOfAdmission = dateOfAdmission;
    }

    public String getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public Name getFirstName() {
        return firstName;
    }

    public Name getMiddleName() {
        return middleName;
    }

    public Name getLastName() {
        return lastName;
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

    public CauseOfDeath getCod() {
        return cod;
    }

    public void setCod(CauseOfDeath cod) {
        this.cod = cod;
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

