package seedu.address.testutil;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TEST_PARAMETERS;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.PhoneNumber;
import seedu.address.model.entity.Sex;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.body.BodyStatus;
import seedu.address.model.entity.body.Nric;
import seedu.address.model.person.Name;

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
    public static final String DEFAULT_RELIGION = "Non-religious";
    public static final String DEFAULT_CAUSE_OF_DEATH = "Heart Attack";
    public static final String DEFAULT_ORGANS_FOR_DONATION = "Liver Cornea Kidney";
    public static final BodyStatus DEFAULT_BODY_STATUS = BodyStatus.ARRIVED;
    public static final int DEFAULT_FRIDGE_ID = 1;
    public static final String DEFAULT_DATE_OF_BIRTH = "12/12/2012";
    public static final String DEFAULT_DATE_OF_DEATH = "02/10/2019";
    public static final String DEFAULT_DATE_OF_ADMISSION = "03/10/2019";

    public static final String DEFAULT_NEXT_OF_KIN = "Jane Doe";
    public static final String DEFAULT_RELATIONSHIP = "Mother";
    public static final String DEFAULT_KIN_PHONE = "81234568";
    public static final String DEFAULT_DETAILS = "note";

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
    private Date dateOfAdmission;

    // Next of kin details
    private Name nextOfKin;
    private String relationship;
    private PhoneNumber kinPhoneNumber;

    private String details;


    public BodyBuilder() {
        name = new Name(DEFAULT_NAME);
        sex = DEFAULT_SEX;
        nric = new Nric(DEFAULT_NRIC);
        religion = DEFAULT_RELIGION;
        causeOfDeath = DEFAULT_CAUSE_OF_DEATH;
        organsForDonation = Arrays.asList(DEFAULT_ORGANS_FOR_DONATION.split(" "));
        bodyStatus = DEFAULT_BODY_STATUS;
        fridgeId = null;

        nextOfKin = new Name(DEFAULT_NEXT_OF_KIN);
        relationship = DEFAULT_RELATIONSHIP;
        kinPhoneNumber = new PhoneNumber(DEFAULT_KIN_PHONE);
        details = DEFAULT_DETAILS;

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
        nric = bodyToCopy.getNric().orElse(null);
        religion = bodyToCopy.getReligion().orElse(null);
        causeOfDeath = bodyToCopy.getCauseOfDeath().orElse(null);
        organsForDonation = bodyToCopy.getOrgansForDonation();
        bodyStatus = bodyToCopy.getBodyStatus().orElse(null);
        fridgeId = bodyToCopy.getFridgeId().orElse(null);
        nextOfKin = bodyToCopy.getNextOfKin().orElse(null);
        relationship = bodyToCopy.getRelationship().orElse(null);
        kinPhoneNumber = bodyToCopy.getKinPhoneNumber().orElse(null);

        dateOfDeath = bodyToCopy.getDateOfDeath().orElse(null);
        dateOfBirth = bodyToCopy.getDateOfBirth().orElse(null);
        dateOfAdmission = bodyToCopy.getDateOfAdmission();

        details = bodyToCopy.getDetails().orElse(null);
    }

    /**
     * Sets the {@code Name} of the {@code Body} that we are building.
     */
    public BodyBuilder withName(String name) {
        try {
            this.name = ParserUtil.parseName(name);
        } catch (ParseException e) {
            System.out.println(e.getMessage() + MESSAGE_INVALID_TEST_PARAMETERS);
        }
        return this;
    }

    /**
     * Sets the {@code sex} of the {@code Body} that we are building.
     */
    public BodyBuilder withSex(String sex) {
        this.sex = Sex.valueOf(sex);
        return this;
    }

    /**
     * Sets the {@code nric} of the {@code Body} that we are building.
     */
    public BodyBuilder withNric(String nric) {
        try {
            this.nric = ParserUtil.parseNric(nric);
        } catch (ParseException e) {
            System.out.println(e.getMessage() + MESSAGE_INVALID_TEST_PARAMETERS);
        }
        return this;
    }

    /**
     * Sets the {@code causeOfDeath} of the {@code Body} that we are building.
     */
    public BodyBuilder withCauseOfDeath(String causeOfDeath) {
        this.causeOfDeath = ParserUtil.parseStringFields(causeOfDeath);
        return this;
    }

    /**
     * Sets the list of {@code organsForDonation} of the {@code Body} that we are building.
     */
    public BodyBuilder withOrgansForDonation(String organsForDonation) {
        this.organsForDonation = ParserUtil.parseOrgansForDonation(organsForDonation);
        return this;
    }

    /**
     * Sets the {@code status} of the {@code Body} that we are building.
     */
    public BodyBuilder withStatus(String bodyStatus) {
        try {
            this.bodyStatus = ParserUtil.parseBodyStatus(bodyStatus);
        } catch (ParseException e) {
            System.out.println(e.getMessage() + MESSAGE_INVALID_TEST_PARAMETERS);
        }

        return this;
    }

    /**
     * Sets the {@code fridgeId} of the {@code Body} that we are building.
     */
    public BodyBuilder withFridgeId(String fridgeId) {
        try {
            this.fridgeId = ParserUtil.parseIdentificationNumber(fridgeId);
        } catch (ParseException e) {
            System.out.println(e.getMessage() + MESSAGE_INVALID_TEST_PARAMETERS);
        }
        return this;
    }

    /**
     * Sets the {@code nextOfKin} of the {@code Body} that we are building.
     */
    public BodyBuilder withNextOfKin(String nextOfKin) {
        try {
            this.nextOfKin = ParserUtil.parseName(nextOfKin);
        } catch (ParseException e) {
            System.out.println(e.getMessage() + MESSAGE_INVALID_TEST_PARAMETERS);
        }
        return this;
    }

    /**
     * Sets the next-of-kin {@code relationship} of the {@code Body} that we are building.
     */
    public BodyBuilder withRelationship(String relationship) {
        this.relationship = ParserUtil.parseStringFields(relationship);
        return this;
    }

    /**
     * Sets the {@code religion} of the {@code Body} that we are building.
     */
    public BodyBuilder withReligion(String religion) {
        this.religion = ParserUtil.parseStringFields(religion);
        return this;
    }

    /**
     * Sets the {@code kinPhoneNumber} of the {@code Body} that we are building.
     */
    public BodyBuilder withKinPhoneNumber(String kinPhoneNumber) {
        try {
            this.kinPhoneNumber = ParserUtil.parsePhoneNumber(kinPhoneNumber);
        } catch (ParseException e) {
            System.out.println(e.getMessage() + MESSAGE_INVALID_TEST_PARAMETERS);
        }
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
     * Sets the details of the {@code Body} that we are building.
     * @param details of the body
     * @return BodyBuilder
     */
    public BodyBuilder withDetails(String details) {
        this.details = ParserUtil.parseStringFields(details);
        return this;
    }

    /**
     * Creates a Body object using the parameters currently in this BodyBuilder object.
     * @return the created Body
     */
    public Body build() {
        return new Body(dateOfAdmission, name, sex, nric, religion, causeOfDeath,
                organsForDonation, bodyStatus, fridgeId, dateOfBirth, dateOfDeath, nextOfKin, relationship,
                kinPhoneNumber, details);
    }

    /**
     * Creates a Body object using the parameters currently in this BodyBuilder object and a user-input custom ID.
     * @return
     */
    public Body build(int id) {
        Body body = Body.generateNewStoredBody(id, dateOfAdmission);
        body.setName(name);
        body.setSex(sex);
        body.setNric(nric);
        body.setReligion(religion);
        body.setCauseOfDeath(causeOfDeath);
        body.setOrgansForDonation(organsForDonation);
        body.setBodyStatus(bodyStatus);
        body.setFridgeId(fridgeId);
        body.setDateOfBirth(dateOfBirth);
        body.setDateOfDeath(dateOfDeath);
        body.setNextOfKin(nextOfKin);
        body.setRelationship(relationship);
        body.setKinPhoneNumber(kinPhoneNumber);
        body.setDetails(details);
        return body;
    }
}
