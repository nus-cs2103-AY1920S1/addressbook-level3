package seedu.address.testutil;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TEST_PARAMETERS;

import java.util.Date;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.PhoneNumber;
import seedu.address.model.entity.Sex;
import seedu.address.model.entity.worker.Worker;
import seedu.address.model.person.Name;

/**
 * A utility class to help with building Worker objects.
 */
public class WorkerBuilder {

    public static final String DEFAULT_NAME = "John Doe";
    public static final String DEFAULT_PHONE = "91234567";
    public static final String DEFAULT_SEX = "MALE";
    public static final String DEFAULT_DATE_OF_BIRTH = "01/01/1997";
    public static final String DEFAULT_DATE_JOINED = "01/01/2019";
    public static final String DEFAULT_DESIGNATION = "technician";
    public static final String DEFAULT_EMPLOYMENT_STATUS = "cleaning";

    private Name name;
    private PhoneNumber phone;
    private Sex sex;
    private Date dateOfBirth;
    private Date dateJoined;
    private String designation;
    private String employmentStatus;

    public WorkerBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new PhoneNumber(DEFAULT_PHONE);
        try {
            sex = ParserUtil.parseSex(DEFAULT_SEX);
            dateOfBirth = ParserUtil.parseDate(DEFAULT_DATE_OF_BIRTH);
            dateJoined = ParserUtil.parseDate(DEFAULT_DATE_JOINED);
        } catch (ParseException e) {
            System.out.println(MESSAGE_INVALID_TEST_PARAMETERS);
        }
        designation = DEFAULT_DESIGNATION;
        employmentStatus = DEFAULT_EMPLOYMENT_STATUS;
    }

    /**
     * Initializes the WorkerBuilder with the data of {@code workerToCopy}.
     */
    public WorkerBuilder(Worker workerToCopy) {
        name = workerToCopy.getName();
        phone = workerToCopy.getPhone().orElse(null);
        sex = workerToCopy.getSex();
        dateOfBirth = workerToCopy.getDateOfBirth().orElse(null);
        dateJoined = workerToCopy.getDateJoined();
        designation = workerToCopy.getDesignation().orElse(null);
        employmentStatus = workerToCopy.getEmploymentStatus().orElse(null);
    }

    /**
     * Sets the {@code Name} of the {@code Worker} that we are building.
     */
    public WorkerBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code sex} of the {@code Worker} that we are building.
     */
    public WorkerBuilder withSex(String sex) {
        this.sex = Sex.valueOf(sex);
        return this;
    }

    /**
     * Sets the {@code employmentStatus} of the {@code Worker} that we are building.
     */
    public WorkerBuilder withEmploymentStatus(String employmentStatus) {
        this.employmentStatus = ParserUtil.parseStringFields(employmentStatus);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Worker} that we are building.
     */
    public WorkerBuilder withPhone(String phone) {
        try {
            this.phone = ParserUtil.parsePhoneNumber(phone);
        } catch (ParseException e) {
            System.out.println(MESSAGE_INVALID_TEST_PARAMETERS);
        }
        return this;
    }

    /**
     * Sets the {@code dateOfBirth} of the {@code Worker} that we are building.
     */
    public WorkerBuilder withDateOfBirth(String dateOfBirth) {
        try {
            this.dateOfBirth = ParserUtil.parseDate(dateOfBirth);
        } catch (ParseException e) {
            System.out.println(MESSAGE_INVALID_TEST_PARAMETERS);
        }
        return this;
    }

    /**
     * Sets the {@code dateJoined} of the {@code Worker} that we are building.
     */
    public WorkerBuilder withDateJoined(String dateJoined) {
        try {
            this.dateJoined = ParserUtil.parseDate(dateJoined);
        } catch (ParseException e) {
            System.out.println(MESSAGE_INVALID_TEST_PARAMETERS);
        }
        return this;
    }

    /**
     * Sets the {@code designation} of the {@code Worker} that we are building.
     */
    public WorkerBuilder withDesignation(String designation) {
        this.designation = ParserUtil.parseStringFields(designation);
        return this;
    }
    public Worker build() {
        return new Worker(name, phone, sex, employmentStatus, dateOfBirth, dateJoined, designation, true);
    }
}
