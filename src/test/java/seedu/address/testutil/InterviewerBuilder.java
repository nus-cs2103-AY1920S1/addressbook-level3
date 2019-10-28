package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.Emails;
import seedu.address.model.person.Faculty;
import seedu.address.model.person.Interviewer;
import seedu.address.model.person.Person;
import seedu.address.model.person.Slot;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Interviewer objects.
 */
public class InterviewerBuilder extends PersonBuilder {

    public static final String DEFAULT_DEPARTMENT = "Technical";
    public static final String DEFAULT_EMAIL = "test@example.com";

    private Department department;
    private Email email;
    private List<Slot> availabilities;

    /**
     * Partially initializes the InterviewerBuilder with {@code p}'s data. Department will be
     * {@code DEFAULT_DEPARTMENT}, Email will be {@code DEFAULT_EMAIL}, with all other fields empty but not
     * null.
     *
     * @param p the person to copy.
     */
    public InterviewerBuilder(Person p) {
        super(p);
        this.department = new Department(DEFAULT_DEPARTMENT);
        this.email = new Email(DEFAULT_EMAIL);
        this.availabilities = new ArrayList<>();
    }

    /**
     * Initializes the InterviewerBuilder with the data of {@code toCopy}.
     */
    public InterviewerBuilder(Interviewer i) {
        super(i.getName().fullName,
                i.getPhone().value,
                i.getTags().stream().map(x -> x.tagName).toArray(String[]::new));
        this.department = i.getDepartment();
        this.email = i.getEmail();
        this.availabilities = i.getAvailabilities();
    }

    /**
     * Sets the {@code Department} of the {@code Interviewer} that we are building.
     */
    public InterviewerBuilder withDepartment(String department) {
        this.department = new Department(department);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Interviewer} that we are building.
     */
    public InterviewerBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Slot}s of the {@code Interviewer} that we are building.
     */
    public InterviewerBuilder withAvailabilities(String... timeslots) {
        this.availabilities = SampleDataUtil.getTimeslotList(timeslots);
        return this;
    }

    /**
     * Clears all tags from the parent class and replaces it with the supplied tags.
     */
    @Override
    public InterviewerBuilder withTags(String... tags) {
        super.getTags().clear();
        super.getTags().addAll(Arrays.stream(tags).map(Tag::new).collect(Collectors.toList()));
        return this;
    }

    /**
     * Builds the Interviewer.
     */
    public Interviewer build() {
        return new Interviewer.InterviewerBuilder(getName(), getPhone(), getTags())
                .department(department)
                .availabilities(availabilities)
                .email(email)
                .build();
    }

}
