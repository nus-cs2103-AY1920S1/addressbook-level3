package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.Emails;
import seedu.address.model.person.Faculty;
import seedu.address.model.person.Interviewee;
import seedu.address.model.person.Person;
import seedu.address.model.person.Slot;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Interviewee objects.
 */
public class IntervieweeBuilder extends PersonBuilder {

    public static final String DEFAULT_FACULTY = "School of Computing";
    public static final String DEFAULT_YEAR_OF_STUDY = "2019";

    private Faculty faculty;
    private Integer yearOfStudy;
    private List<Department> departmentChoices;
    private List<Slot> allocatedTimeslots;
    private Emails emails;

    /**
     * Partially initializes the IntervieweeBuilder with {@code p}'s data. Faculty will be {@code DEFAULT_FACULTY} and
     * year of study will be {@code DEFAULT_YEAR_OF_STUDY}, with all other fields empty but not null.
     *
     * @param p the person to copy.
     */
    public IntervieweeBuilder(Person p) {
        super(p);
        this.faculty = new Faculty(DEFAULT_FACULTY);
        this.yearOfStudy = Integer.parseInt(DEFAULT_YEAR_OF_STUDY);
        this.departmentChoices = new ArrayList<>();
        this.allocatedTimeslots = new ArrayList<>();
        this.emails = new Emails();
    }

    /**
     * Initializes the IntervieweeBuilder with the data of {@code toCopy}.
     */
    public IntervieweeBuilder(Interviewee i) {
        super(i.getName().fullName,
                i.getPhone().value,
                i.getTags().stream().map(x -> x.tagName).toArray(String[]::new));
        faculty = i.getFaculty();
        yearOfStudy = i.getYearOfStudy();
        departmentChoices = i.getDepartmentChoices();
        allocatedTimeslots = i.getAvailableTimeslots();
        emails = i.getEmails();
    }

    /**
     * Sets the {@code Faculty} of the {@code Interviewee} that we are building.
     */
    public IntervieweeBuilder withFaculty(String faculty) {
        this.faculty = new Faculty(faculty);
        return this;
    }

    /**
     * Sets the stud year of the {@code Interviewee} that we are building.
     */
    public IntervieweeBuilder withYearOfStudy(String yearOfStudy) {
        this.yearOfStudy = Integer.parseInt(yearOfStudy);
        return this;
    }

    /**
     * Sets the {@code Department}s of the {@code Interviewee} that we are building.
     */
    public IntervieweeBuilder withDepartmentChoices(String... departments) {
        this.departmentChoices = SampleDataUtil.getDepartmentList(departments);
        return this;
    }

    /**
     * Sets the {@code Slot}s of the {@code Interviewee} that we are building.
     */
    public IntervieweeBuilder withTimeslots(String... timeslots) {
        this.allocatedTimeslots = SampleDataUtil.getTimeslotList(timeslots);
        return this;
    }

    /**
     * Sets the personal email of the {@code Interviewee} that we are building.
     */
    public IntervieweeBuilder withPersonalEmail(String email) {
        Email toAdd = new Email(email);
        if (emails != null) {
            this.emails.addPersonalEmail(toAdd);
        } else {
            this.emails = new Emails().addPersonalEmail(toAdd);
        }
        return this;
    }

    /**
     * Sets the Nus work email of the {@code Interviewee} that we are building.
     */
    public IntervieweeBuilder withNusWorkEmail(String email) {
        Email toAdd = new Email(email);
        if (emails != null) {
            this.emails.addNusEmail(toAdd);
        } else {
            this.emails = new Emails().addNusEmail(toAdd);
        }
        return this;
    }

    /**
     * Clears all tags from the parent class and replaces it with the supplied tags.
     */
    @Override
    public IntervieweeBuilder withTags(String... tags) {
        super.getTags().clear();
        super.getTags().addAll(Arrays.stream(tags).map(Tag::new).collect(Collectors.toList()));
        return this;
    }

    /**
     * Builds the Interviewee.
     */
    public Interviewee build() {
        return new Interviewee.IntervieweeBuilder(getName(), getPhone(), getTags())
                    .faculty(faculty)
                    .yearOfStudy(yearOfStudy)
                    .departmentChoices(departmentChoices)
                    .availableTimeslots(allocatedTimeslots)
                    .emails(emails)
                    .build();
    }

}
