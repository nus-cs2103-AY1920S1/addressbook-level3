package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.Schedule;

/**
 * A utility class containing all default values for the model.
 */
public class DefaultValues {

    // Person
    public static final Name DEFAULT_NAME = new Name("Anson Bellfield");
    public static final Phone DEFAULT_PHONE = new Phone("90091234");
    public static final Address DEFAULT_ADDRESS = new Address("23 Hillview Heights");

    // Interviewee
    public static final Email DEFAULT_PERSONAL_EMAIL = new Email("defaultPersonalEmail@example.com");
    public static final Email DEFAULT_NUS_EMAIL = new Email("defaultNusEmail@example.com");
    public static final Emails DEFAULT_EMAILS = new Emails()
                .addPersonalEmail(DEFAULT_PERSONAL_EMAIL)
                .addNusEmail(DEFAULT_NUS_EMAIL);
    public static final Faculty DEFAULT_FACULTY = new Faculty("Default faculty");
    public static final Integer DEFAULT_YEAR_OF_STUDY = 2019;
    public static final List<Department> DEFAULT_DEPARTMENTS = new ArrayList<>();
    public static final List<Slot> DEFAULT_TIMESLOTS = new ArrayList<>();

    // Interviewer
    public static final List<Schedule> DEFAULT_SCHEDULES = new ArrayList<>();
    public static final Department DEFAULT_DEPARTMENT = new Department("Default department");
}
