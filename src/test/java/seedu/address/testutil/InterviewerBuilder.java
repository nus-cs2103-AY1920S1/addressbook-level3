package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.Interviewer;
import seedu.address.model.person.Person;
import seedu.address.model.person.Slot;

/**
 * A utility class to help with building Interviewer objects from string input.
 */
public class InterviewerBuilder extends PersonBuilder {

    private static final String DEFAULT_DEPARTMENT = "Logistics";
    private static final String DEFAULT_EMAIL = "default_interviewee@gmail.com";

    private List<Slot> availabilities;
    private Department department;
    private Email email;

    /**
     * Partially initializes the InterviewerBuilder with {@code p}'s data. Faculty will be {@code DEFAULT_DEPARTMENT}
     * and year of study will be {@code DEFAULT_EMAIL}, with all other fields empty but not null.
     */
    public InterviewerBuilder(Person p) {
        super(p);
        this.department = new Department(DEFAULT_DEPARTMENT);
        this.email = new Email(DEFAULT_EMAIL);
        this.availabilities = new ArrayList<>();
    }

    /**
     * Initializes the InterviewerBuilder with the data of {@code i}.
     */
    public InterviewerBuilder(Interviewer i) {
        super(i.getName().fullName,
                i.getPhone().value,
                i.getTags().stream().map(x -> x.tagName).toArray(String[]::new));
        department = i.getDepartment();
        email = i.getEmail();
        availabilities = i.getAvailabilities();
    }

    /**
     * Sets the {@code Department} of the {@code Interviewer} that we are building.
     */
    public InterviewerBuilder withDepartment(String department) {
        try {
            this.department = ParserUtil.parseDepartment(department);
        } catch (ParseException e) {
            throw new AssertionError(Messages.MESSAGE_CRITICAL_ERROR, e);
        }
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Interviewer} that we are building.
     */
    public InterviewerBuilder withEmail(String email) {
        try {
            this.email = ParserUtil.parseEmail(email);
        } catch (ParseException e) {
            throw new AssertionError(Messages.MESSAGE_CRITICAL_ERROR, e);
        }
        return this;
    }

    /**
     * Sets the {@code Slot}s of the {@code Interviewer} that we are building.
     */
    public InterviewerBuilder withAvailabilities(String... availabilities) {
        try {
            this.availabilities = ParserUtil.parseSlots(Arrays.asList(availabilities));
        } catch (ParseException e) {
            throw new AssertionError(Messages.MESSAGE_CRITICAL_ERROR, e);
        }
        return this;
    }

    /**
     * Clears all tags from the parent class and replaces it with the supplied tags.
     */
    @Override
    public InterviewerBuilder withTags(String... tags) {
        try {
            super.getTags().clear();
            super.getTags().addAll(ParserUtil.parseTags(Arrays.asList(tags)));
        } catch (ParseException e) {
            throw new AssertionError(Messages.MESSAGE_CRITICAL_ERROR, e);
        }
        return this;
    }

    /**
     * Builds the Interviewer.
     */
    public Interviewer build() {
        return new Interviewer.InterviewerBuilder(getName(), getPhone(), getTags())
                .department(department)
                .email(email)
                .availabilities(availabilities)
                .build();
    }
}
