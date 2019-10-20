package seedu.address.storage;

import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_DATE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.PhoneNumber;
import seedu.address.model.entity.Sex;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.body.BodyStatus;
import seedu.address.model.entity.body.Nric;
import seedu.address.model.entity.body.Religion;
import seedu.address.model.person.Name;

//@@author ambervoong
/**
 * Jackson-friendly version of {@link Body}.
 */
class JsonAdaptedBody {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Body's %s field is missing!";

    // These fields can't be null.
    private final String bodyIdNum;
    private final String dateOfAdmission;
    private final String bodyStatus; // Should always be present since default status is ARRIVED.

    // Identity fields.
    private final String name;
    private final String sex;
    private final String nric;
    private final String religion;

    private final String causeOfDeath;
    private final String fridgeId;
    private final String dateOfBirth;
    private final String dateOfDeath;

    private final String nextOfKin;
    private final String relationship;
    private final String kinPhoneNumber;

    private final List<String> organsForDonation = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedBody} with the given body details.
     */
    @JsonCreator
    public JsonAdaptedBody(@JsonProperty("bodyIdNum") String bodyIdNum,
                           @JsonProperty("dateOfAdmission") String dateOfAdmission,
                           @JsonProperty("bodyStatus") String bodyStatus,
                           @JsonProperty("name") String name,
                           @JsonProperty("sex") String sex,
                           @JsonProperty("nric") String nric,
                           @JsonProperty("religion") String religion,
                           @JsonProperty("causeOfDeath") String causeOfDeath,
                           @JsonProperty("fridgeId") String fridgeId,
                           @JsonProperty("dateOfBirth") String dateOfBirth,
                           @JsonProperty("dateOfDeath") String dateOfDeath,
                           @JsonProperty("nextOfKin") String nextOfKin,
                           @JsonProperty("relationship") String relationship,
                           @JsonProperty("kinPhoneNumber") String kinPhoneNumber,
                           @JsonProperty("organsForDonation") List<String> organsForDonation) {
        this.bodyIdNum = bodyIdNum;
        this.dateOfAdmission = dateOfAdmission;
        this.bodyStatus = bodyStatus;
        this.name = name;
        this.sex = sex;
        this.nric = nric;
        this.religion = religion;
        this.causeOfDeath = causeOfDeath;
        this.fridgeId = fridgeId;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.nextOfKin = nextOfKin;
        this.relationship = relationship;
        this.kinPhoneNumber = kinPhoneNumber;

        if (organsForDonation != null) {
            this.organsForDonation.addAll(organsForDonation);
        }
    }

    /**
     * Converts a given {@code Body} into this class for Jackson use.
     */
    public JsonAdaptedBody(Body source) {
        bodyIdNum = Integer.toString(source.getIdNum().getIdNum());
        bodyStatus = source.getBodyStatus().get().toString();
        name = source.getName().toString();
        sex = source.getSex().toString();
        nric = source.getNric().map(Nric::toString).orElse(null);
        religion = source.getReligion().map(Religion::toString).orElse(null);
        causeOfDeath = source.getCauseOfDeath().orElse(null);
        nextOfKin = source.getNextOfKin().map(Name::toString).orElse(null);
        relationship = source.getRelationship().orElse(null);
        kinPhoneNumber = source.getKinPhoneNumber().map(PhoneNumber::toString).orElse(null);

        // Dates
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        dateOfAdmission = formatter.format(source.getDateOfAdmission());
        dateOfBirth = source.getDateOfBirth().map(x -> formatter.format(x)).orElse(null);
        dateOfDeath = source.getDateOfDeath().map(x -> formatter.format(x)).orElse(null);

        if (source.getFridgeId() != null) {
            fridgeId = Integer.toString(source.getIdNum().getIdNum());
        } else {
            fridgeId = null;
        }

        if (!(source.getOrgansForDonation().isEmpty())) {
            organsForDonation.addAll(source.getOrgansForDonation().get());
        }
    }

    /**
     * Converts this Jackson-friendly adapted body object into the model's {@code Body} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted body.
     */
    public Body toModelType() throws IllegalValueException {
        // Convert ID number.
        if (bodyIdNum == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    IdentificationNumber.class.getSimpleName()));
        }
        final int idNumber;
        idNumber = Integer.parseInt(bodyIdNum);

        // Convert BodyStatus
        if (bodyStatus == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    BodyStatus.class.getSimpleName()));
        }

        String removedUnderscores = bodyStatus.replace('_', ' ');
        final BodyStatus actualStatus = ParserUtil.parseBodyStatus(removedUnderscores);

        // Convert Name
        final Name actualName;
        if (name == null) {
            actualName = null;
        } else {
            if (!Name.isValidName(name)) {
                throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
            }
            actualName = new Name(name);
        }

        // Convert Sex
        final Sex actualSex;
        if (sex == null) {
            actualSex = null;
        } else {
            actualSex = ParserUtil.parseSex(sex);
        }

        // Convert Nric
        // NRIC can be null
        final Nric actualNric;
        if (nric == null) {
            actualNric = null;
        } else {
            if (!Nric.isValidNric(nric)) {
                throw new IllegalValueException(Nric.VALID_NRIC);
            }
            actualNric = new Nric(nric);
        }

        // Convert Religion
        final Religion actualReligion;
        if (religion == null) {
            actualReligion = null;
        } else {
            actualReligion = ParserUtil.parseReligion(religion);
        }

        // Convert causeOfDeath
        final String actualCauseOfDeath;
        if (causeOfDeath == null) {
            actualCauseOfDeath = null;
        } else {
            actualCauseOfDeath = causeOfDeath;
        }

        // Convert fridgeId
        final IdentificationNumber actualFridgeId;
        if (fridgeId == null) {
            actualFridgeId = null;
        } else {
            try {
                int parsedInt = Integer.parseInt(fridgeId);
                actualFridgeId = IdentificationNumber.customGenerateId("F", parsedInt);
            } catch (NumberFormatException e) {
                throw new IllegalValueException(IdentificationNumber.MESSAGE_CONSTRAINTS);
            }
        }

        // Get nextOfKin
        final Name actualNextOfKin;
        if (nextOfKin == null) {
            actualNextOfKin = null;
        } else {
            if (!Name.isValidName(nextOfKin)) {
                throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
            }
            actualNextOfKin = new Name(nextOfKin);
        }

        // Get relationship
        final String actualRelationship;
        if (relationship == null) {
            actualRelationship = null;
        } else {
            actualRelationship = relationship;
        }

        // Get kinPhoneNumber
        final PhoneNumber actualKinPhoneNumber;
        if (kinPhoneNumber == null) {
            actualKinPhoneNumber = null;
        } else {
            if (!PhoneNumber.isValidPhoneNumber(kinPhoneNumber)) {
                throw new IllegalValueException(PhoneNumber.VALID_NUMBER);
            }
            actualKinPhoneNumber = new PhoneNumber(kinPhoneNumber);
        }

        // Get organsForDonation
        final List<String> actualOrgansForDonation;
        if (organsForDonation == null) {
            actualOrgansForDonation = null;
        } else {
            actualOrgansForDonation = new ArrayList<>(organsForDonation);
        }

        // Convert dates
        if (dateOfAdmission == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        final Date parsedDate;
        try {
            parsedDate = formatter.parse(dateOfAdmission);
        } catch (java.text.ParseException e) {
            throw new ParseException(MESSAGE_INVALID_DATE);
        }

        final Date actualDob;
        final Date actualDod;
        if (dateOfBirth == null) {
            actualDob = null;
        } else {
            try {
                actualDob = formatter.parse(dateOfBirth);
            } catch (java.text.ParseException e) {
                throw new ParseException(MESSAGE_INVALID_DATE);
            }
        }

        if (dateOfDeath == null) {
            actualDod = null;
        } else {
            try {
                actualDod = formatter.parse(dateOfDeath);
            } catch (java.text.ParseException e) {
                throw new ParseException(MESSAGE_INVALID_DATE);
            }
        }


        Body body = Body.generateNewStoredBody(idNumber, parsedDate);
        body.setBodyStatus(actualStatus);
        body.setName(actualName);
        body.setSex(actualSex);
        body.setNric(actualNric);
        body.setReligion(actualReligion);
        body.setCauseOfDeath(actualCauseOfDeath);
        body.setFridgeId(actualFridgeId);
        body.setDateOfBirth(actualDob);
        body.setDateOfDeath(actualDod);
        body.setNextOfKin(actualNextOfKin);
        body.setRelationship(actualRelationship);
        body.setKinPhoneNumber(actualKinPhoneNumber);
        body.setOrgansForDonation(actualOrgansForDonation);
        return body;
    }
}

