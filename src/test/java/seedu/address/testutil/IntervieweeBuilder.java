package seedu.address.testutil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.model.person.Department;
import seedu.address.model.person.Faculty;
import seedu.address.model.person.Interviewee;
import seedu.address.model.person.Person;
import seedu.address.model.person.Slot;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Interviewee objects.
 */
public class IntervieweeBuilder extends PersonBuilder {

    public static final String DEFAULT_FACULTY = "School of Computing";
    public static final String DEFAULT_YEAR_OF_STUDY = "2019";
    public static final String DEFAULT_DEPARTMENT = "Marketing";
    public static final String DEFAULT_SLOT_DATE = "16/10/2019";
    public static final String DEFAULT_SLOT_START = "00:00";
    public static final String DEFAULT_SLOT_END = "23:59";

    private Faculty faculty;
    private Integer yearOfStudy;
    private List<Department> departmentChoices;
    private List<Slot> allocatedTimeslots;

    /**
     * Initializes the IntervieweeBuilder with all default data.
     */
    public IntervieweeBuilder() {
        this(new PersonBuilder().build());
    }

    /**
     * Partially initializes the IntervieweeBuilder with the data of {@code p}.
     * @param p
     */
    public IntervieweeBuilder(Person p) {
        super(p);
        this.faculty = new Faculty(DEFAULT_FACULTY);
        this.yearOfStudy = Integer.parseInt(DEFAULT_YEAR_OF_STUDY);
        this.departmentChoices = Stream.of(new Department(DEFAULT_DEPARTMENT)).collect(Collectors.toList());
        this.allocatedTimeslots = Stream.of(
                new Slot(String.format(Slot.STRING_FORMAT, DEFAULT_SLOT_DATE, DEFAULT_SLOT_START, DEFAULT_SLOT_END)))
                .collect(Collectors.toList());
    }

    /**
     * Initializes the IntervieweeBuilder with the data of {@code toCopy}.
     */
    public IntervieweeBuilder(Interviewee i) {
        super(i.getName().fullName,
                i.getPhone().value,
                i.getAddress().value,
                i.getTags().stream().map(x -> x.tagName).toArray(String[]::new));
        faculty = i.getFaculty();
        yearOfStudy = i.getYearOfStudy();
        departmentChoices = i.getDepartmentChoices();
        allocatedTimeslots = i.getAvailableTimeslots();
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
     * Builds the Interviewee.
     */
    public Interviewee build() {
        return new Interviewee.IntervieweeBuilder(getName(), getPhone(), getAddress(), getTags())
                    .faculty(faculty)
                    .yearOfStudy(yearOfStudy)
                    .departmentChoices(departmentChoices)
                    .availableTimeslots(allocatedTimeslots)
                    .build();
    }

}
