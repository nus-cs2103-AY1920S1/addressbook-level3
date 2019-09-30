package seedu.address.testutil;

import java.text.ParseException;
import java.util.Date;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.entity.worker.Designation;
import seedu.address.model.entity.worker.Worker;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

/**
 * A utility class to help with building Worker objects.
 */
public class WorkerBuilder {

    public static final String MESSAGE_INVALID_TEST_DATE = "Test parameters for dates are invalid";
    public static final String DEFAULT_NAME = "John Doe";
    public static final String DEFAULT_PHONE = "91234567";
    public static final String DEFAULT_SEX = "Male";
    public static final String DEFAULT_DATE_OF_BIRTH = "01/01/1997";
    public static final String DEFAULT_DATE_JOINED = "01/01/2019";
    public static final Designation DEFAULT_DESIGNATION = Designation.TECHNICIAN;
    public static final String DEFAULT_EMPLOYMENT_STATUS = "cleaning";

    private Name name;
    private Phone phone;
    private String sex;
    private Date dateOfBirth;
    private Date dateJoined;
    private Designation designation;
    private String employmentStatus;

    public WorkerBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        sex = DEFAULT_SEX;
        try {
            dateOfBirth = ParserUtil.parseDate(DEFAULT_DATE_OF_BIRTH);
            dateJoined = ParserUtil.parseDate(DEFAULT_DATE_JOINED);
        } catch (ParseException e) {
            System.out.println(MESSAGE_INVALID_TEST_DATE);
        }
        designation = DEFAULT_DESIGNATION;
        employmentStatus = DEFAULT_EMPLOYMENT_STATUS;
    }

    /**
     * Initializes the WorkerBuilder with the data of {@code workerToCopy}.
     */
    public WorkerBuilder(Worker workerToCopy) {
        name = workerToCopy.getName();
        phone = workerToCopy.getPhone();
        sex = workerToCopy.getSex();
        dateOfBirth = workerToCopy.getDateOfBirth();
        dateJoined = workerToCopy.getDateJoined();
        designation = workerToCopy.getDesignation();
        employmentStatus = workerToCopy.getEmploymentStatus();
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
        this.sex = sex;
        return this;
    }

    /**
     * Sets the {@code employmentStatus} of the {@code Worker} that we are building.
     */
    public WorkerBuilder withEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Worker} that we are building.
     */
    public WorkerBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code dateOfBirth} of the {@code Worker} that we are building.
     */
    public WorkerBuilder withDateOfBirth(String dateOfBirth) {
        try {
            this.dateOfBirth = ParserUtil.parseDate(dateOfBirth);
        } catch (ParseException e) {
            System.out.println(MESSAGE_INVALID_TEST_DATE);
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
            System.out.println(MESSAGE_INVALID_TEST_DATE);
        }
        return this;
    }

    /**
     * Sets the {@code designation} of the {@code Worker} that we are building.
     */
    public WorkerBuilder withDesignation(Designation designation) {
        this.designation = designation;
        return this;
    }
    public Worker build() {
        return new Worker(name, phone, sex, employmentStatus, dateJoined);
    }
}
