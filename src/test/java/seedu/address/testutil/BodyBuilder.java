package seedu.address.testutil;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.Sex;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.body.Nric;
import seedu.address.model.entity.body.Religion;
import seedu.address.model.entity.body.Status;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

//@@author ambervoong
/**
 * A utility class to build Body objects.
 */
public class BodyBuilder {

    public static final String MESSAGE_INVALID_TEST_DATE = "Invalid date parameters. A correct date should have a "
            + "format of DD/MM/YYYY";
    public static final String DEFAULT_NAME = "John Doe";
    public static final Sex DEFAULT_SEX = Sex.MALE;
    public static final String DEFAULT_NRIC = "S8765432A";
    public static final Religion DEFAULT_RELIGION = Religion.NONRELIGIOUS;
    public static final String DEFAULT_CAUSE_OF_DEATH = "Heart Attack";
    public static final String DEFAULT_ORGANS_FOR_DONATION = "Liver Cornea Kidney";
    public static final Status DEFAULT_STATUS = Status.ARRIVED;
    public static final int DEFAULT_FRIDGE_ID = 1;
    public static final String DEFAULT_DATE_OF_BIRTH = "12/12/2012";
    public static final String DEFAULT_DATE_OF_DEATH = "02/10/2019";
    public static final String DEFAULT_DATE_OF_ADMISSION = "03/10/2019";

    public static final String DEFAULT_NEXT_OF_KIN = "Jane Doe";
    public static final String DEFAULT_RELATIONSHIP = "Mother";
    public static final String DEFAULT_KIN_PHONE = "81234568";

    private Name name;
    private Sex sex;
    private Nric nric;
    private Religion religion;

    private String causeOfDeath;
    private List<String> organsForDonation;
    private Status status;
    private IdentificationNumber fridgeId;

    private Date dateOfBirth;
    private Date dateOfDeath;
    private Date dateOfAdmission;

    // Next of kin details
    private Name nextOfKin;
    private String relationship;
    private Phone kinPhoneNumber;


    public BodyBuilder() {
        name = new Name(DEFAULT_NAME);
        sex = DEFAULT_SEX;
        nric = new Nric(DEFAULT_NRIC);
        religion = DEFAULT_RELIGION;
        causeOfDeath = DEFAULT_CAUSE_OF_DEATH;
        organsForDonation = Arrays.asList(DEFAULT_ORGANS_FOR_DONATION.split(" "));
        status = DEFAULT_STATUS;
        fridgeId = IdentificationNumber.customGenerateId("F", DEFAULT_FRIDGE_ID);

        nextOfKin = new Name(DEFAULT_NEXT_OF_KIN);
        relationship = DEFAULT_RELATIONSHIP;
        kinPhoneNumber = new Phone(DEFAULT_KIN_PHONE);

        try {
            dateOfBirth = ParserUtil.parseDate(DEFAULT_DATE_OF_BIRTH);
            dateOfDeath = ParserUtil.parseDate(DEFAULT_DATE_OF_DEATH);
            dateOfAdmission = ParserUtil.parseDate(DEFAULT_DATE_OF_ADMISSION);
        } catch (ParseException e) {
            System.out.println(MESSAGE_INVALID_TEST_DATE);
        }
    }

    /**
     * Initializes the BodyBuilder with the data of {@code bodyToCopy}.
     */
    public BodyBuilder(Body bodyToCopy) {
        name = bodyToCopy.getName();
        sex = bodyToCopy.getSex();
        nric = bodyToCopy.getNric();
        religion = bodyToCopy.getReligion();
        causeOfDeath = bodyToCopy.getCauseOfDeath();
        organsForDonation = bodyToCopy.getOrgansForDonation();
        status = bodyToCopy.getStatus();
        fridgeId = bodyToCopy.getFridgeId();
        nextOfKin = bodyToCopy.getNextOfKin();
        relationship = bodyToCopy.getRelationship();
        kinPhoneNumber = bodyToCopy.getKinPhoneNumber();

        dateOfDeath = bodyToCopy.getDateOfDeath();
        dateOfBirth = bodyToCopy.getDateOfBirth();
        dateOfAdmission = bodyToCopy.getDateOfAdmission();
    }

    /**
     * Sets the {@code Name} of the {@code Body} that we are building.
     */
    public BodyBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code sex} of the {@code Body} that we are building.
     */
    public BodyBuilder withSex(Sex sex) {
        this.sex = sex;
        return this;
    }

    /**
     * Sets the {@code nric} of the {@code Body} that we are building.
     */
    public BodyBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }

    /**
     * Sets the {@code causeOfDeath} of the {@code Body} that we are building.
     */
    public BodyBuilder withCauseOfDeath(String causeOfDeath) {
        this.causeOfDeath = causeOfDeath;
        return this;
    }

    /**
     * Sets the list of {@code organsForDonation} of the {@code Body} that we are building.
     */
    public BodyBuilder withOrgansForDonation(List<String> organsForDonation) {
        this.organsForDonation = organsForDonation;
        return this;
    }

    /**
     * Sets the {@code status} of the {@code Body} that we are building.
     */
    public BodyBuilder withStatus(Status status) {
        this.status = status;
        return this;
    }

    /**
     * Sets the {@code fridgeId} of the {@code Body} that we are building.
     */
    public BodyBuilder withFridgeId(int fridgeId) {
        this.fridgeId = IdentificationNumber.customGenerateId("F", fridgeId);
        return this;
    }

    /**
     * Sets the {@code nextOfKin} of the {@code Body} that we are building.
     */
    public BodyBuilder withNextOfKin(String nextOfKin) {
        this.nextOfKin = new Name(nextOfKin);
        return this;
    }

    /**
     * Sets the next-of-kin {@code relationship} of the {@code Body} that we are building.
     */
    public BodyBuilder withRelationship(String relationship) {
        this.relationship = relationship;
        return this;
    }

    /**
     * Sets the {@code religion} of the {@code Body} that we are building.
     */
    public BodyBuilder withReligion(Religion religion) {
        this.religion = religion;
        return this;
    }

    /**
     * Sets the {@code kinPhoneNumber} of the {@code Body} that we are building.
     */
    public BodyBuilder withKinPhoneNumber(String kinPhoneNumber) {
        this.kinPhoneNumber = new Phone(kinPhoneNumber);
        return this;
    }

    /**
     * Sets the {@code dateOfBirth} of the {@code Body} that we are building.
     */
    public BodyBuilder withDateOfBirth(String dateOfBirth) {
        try {
            this.dateOfBirth = ParserUtil.parseDate(dateOfBirth);
        } catch (ParseException e) {
            System.out.println(MESSAGE_INVALID_TEST_DATE);
        }
        return this;
    }

    /**
     * Sets the {@code dateOfDeath} of the {@code Body} that we are building.
     */
    public BodyBuilder withDateOfDeath(String dateOfDeath) {
        try {
            this.dateOfDeath = ParserUtil.parseDate(dateOfDeath);
        } catch (ParseException e) {
            System.out.println(MESSAGE_INVALID_TEST_DATE);
        }
        return this;
    }

    /**
     * Sets the {@code dateOfAdmission} of the {@code Body} that we are building.
     */
    public BodyBuilder withDateOfAdmission(String dateOfAdmission) {
        try {
            this.dateOfAdmission = ParserUtil.parseDate(dateOfAdmission);
        } catch (ParseException e) {
            System.out.println(MESSAGE_INVALID_TEST_DATE);
        }
        return this;
    }

    /**
     * Creates a Body object using the parameters currently in this BodyBuilder object.
     * @return
     */
    public Body build() {
        return new Body(true, 1, dateOfAdmission, name, sex, nric, religion, causeOfDeath,
                organsForDonation, status, fridgeId, dateOfBirth, dateOfDeath, nextOfKin, relationship, kinPhoneNumber);
    }
}
