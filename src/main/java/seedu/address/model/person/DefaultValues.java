package seedu.address.model.person;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.Schedule;
import seedu.address.model.tag.Tag;

/**
 * A utility class containing all default values for the model.
 */
public class DefaultValues {

    // Person
    public static final Name DEFAULT_NAME = new Name("John Cena");
    public static final Phone DEFAULT_PHONE = new Phone("88888888");

    // Interviewee
    public static final Role DEFAULT_INTERVIEWEE_ROLE = new Role("interviewee");
    public static final Email DEFAULT_PERSONAL_EMAIL = new Email("default@example.com");
    public static final Email DEFAULT_NUS_WORK_EMAIL = new Email("default@u.nus.edu");
    public static final Emails DEFAULT_EMAILS = new Emails()
                .addPersonalEmail(DEFAULT_PERSONAL_EMAIL)
                .addNusEmail(DEFAULT_NUS_WORK_EMAIL);
    public static final Faculty DEFAULT_FACULTY = new Faculty("School of Computing");
    public static final Integer DEFAULT_YEAR_OF_STUDY = 2019;
    public static final Slot DEFAULT_SLOT = Slot.fromString("17/10/2019 13:00-14:00");
    public static final List<Department> DEFAULT_DEPARTMENTS = new ArrayList<>();
    public static final List<Slot> DEFAULT_TIMESLOTS = new ArrayList<>();
    public static final Set<Tag> DEFAULT_TAGS = new HashSet<>();

    // Interviewer
    public static final Role DEFAULT_INTERVIEWER_ROLE = new Role("interviewer");
    public static final List<Schedule> DEFAULT_SCHEDULES = new ArrayList<>();
    public static final Department DEFAULT_DEPARTMENT = new Department("Marketing");
}
