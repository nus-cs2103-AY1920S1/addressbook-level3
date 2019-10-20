package seedu.address.storage;

import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_DATE;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.PhoneNumber;
import seedu.address.model.entity.Sex;
import seedu.address.model.entity.worker.Worker;
import seedu.address.model.person.Name;


//@@author ambervoong
/**
 * Jackson-friendly version of {@link Worker}.
 */
class JsonAdaptedWorker {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Worker's %s field is missing!";
    // These fields can't be null.
    private final String workerIdNum;
    private final String name;
    private final String sex;

    private final String dateJoined;
    private final String designation;
    private final String dateOfBirth;
    private final String phone;
    private final String employmentStatus;


    /**
     * Constructs a {@code JsonAdaptedWorker} with the given worker details.
     */
    @JsonCreator
    public JsonAdaptedWorker(@JsonProperty("workerIdNum") String workerIdNum,
                           @JsonProperty("name") String name,
                           @JsonProperty("sex") String sex,
                           @JsonProperty("dateJoined") String dateJoined,
                           @JsonProperty("designation") String designation,
                           @JsonProperty("dateOfBirth") String dateOfBirth,
                           @JsonProperty("phone") String phone,
                           @JsonProperty("employmentStatus") String employmentStatus) {
        this.workerIdNum = workerIdNum;
        this.name = name;
        this.sex = sex;
        this.dateJoined = dateJoined;
        this.designation = designation;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.employmentStatus = employmentStatus;
    }

    /**
     * Converts a given {@code Worker} into this class for Jackson use.
     */
    public JsonAdaptedWorker(Worker source) {
        workerIdNum = Integer.toString(source.getIdNum().getIdNum());
        name = source.getName().toString();
        sex = source.getSex().toString();

        // Can be null
        designation = source.getDesignation().orElse(null);
        phone = source.getPhone().map(PhoneNumber::toString).orElse(null);
        employmentStatus = source.getEmploymentStatus().orElse(null);

        // Dates
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        dateJoined = formatter.format(source.getDateJoined());
        dateOfBirth = source.getDateOfBirth().map(x -> formatter.format(x)).orElse(null);
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Worker} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Worker toModelType() throws IllegalValueException {
        // Convert ID number.
        if (workerIdNum == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    IdentificationNumber.class.getSimpleName()));
        }
        final int idNumber;
        try {
            idNumber = Integer.parseInt(workerIdNum);
        } catch (NumberFormatException e) {
            throw new IllegalValueException(IdentificationNumber.MESSAGE_CONSTRAINTS);
        }

        // Convert Name
        final Name actualName;
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
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

        // Convert designation
        final String actualDesignation;
        if (designation == null) {
            actualDesignation = null;
        } else {
            actualDesignation = designation;
        }

        // Convert employmentStatus
        final String actualEmploymentStatus;
        if (employmentStatus == null) {
            actualEmploymentStatus = null;
        } else {
            actualEmploymentStatus = employmentStatus;
        }

        // Get phoneNumber
        final PhoneNumber actualPhoneNumber;
        if (phone == null) {
            actualPhoneNumber = null;
        } else {
            if (!PhoneNumber.isValidPhoneNumber(phone)) {
                throw new IllegalValueException(PhoneNumber.VALID_NUMBER);
            }
            actualPhoneNumber = new PhoneNumber(phone);
        }

        // Convert dates
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        final Date actualDateJoined;
        final Date actualDateOfBirth;

        if (dateJoined == null) {
            actualDateJoined = null;
        } else {
            try {
                actualDateJoined = formatter.parse(dateJoined);
            } catch (java.text.ParseException e) {
                throw new ParseException(MESSAGE_INVALID_DATE);
            }
        }

        if (dateOfBirth == null) {
            actualDateOfBirth = null;
        } else {
            try {
                actualDateOfBirth = formatter.parse(dateOfBirth);
            } catch (java.text.ParseException e) {
                throw new ParseException(MESSAGE_INVALID_DATE);
            }
        }

        Worker worker = Worker.generateNewStoredWorker(actualName, actualSex, actualDateJoined, idNumber);
        worker.setDesignation(actualDesignation);
        worker.setDateOfBirth(actualDateOfBirth);
        worker.setPhone(actualPhoneNumber);
        worker.setEmploymentStatus(actualEmploymentStatus);
        return worker;
    }
}

