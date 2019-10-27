package seedu.address.testutil;

import static seedu.address.testutil.TypicalIds.ID_FIRST_MENTOR;

import seedu.address.model.entity.Email;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Phone;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.SubjectName;

/**
 * Builds a valid {@link Mentor} to facilitate testing.
 */
public class MentorBuilder {

    public static final Id DEFAULT_ID = ID_FIRST_MENTOR;
    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ORGANIZATION = "Alfred Org";
    public static final String DEFAULT_SUBJECT = "ENVIRONMENTAL";

    private Id id;
    private Name name;
    private Phone phone;
    private Email email;
    private Name organization;
    private SubjectName subject;

    public MentorBuilder() {
        id = DEFAULT_ID;
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        organization = new Name(DEFAULT_ORGANIZATION);
        subject = SubjectName.valueOf(DEFAULT_SUBJECT);
    }

    /**
     * Initializes the MentorBuilder with the data of {@code mentorToCopy}.
     */
    public MentorBuilder(Mentor mentorToCopy) {
        id = mentorToCopy.getId();
        name = mentorToCopy.getName();
        phone = mentorToCopy.getPhone();
        email = mentorToCopy.getEmail();
        organization = mentorToCopy.getOrganization();
        subject = mentorToCopy.getSubject();
    }

    /**
     * Sets the {@code Id} of the {@code Mentor} that we are building.
     */
    public MentorBuilder withId(int id) {
        this.id = new Id(PrefixType.M, id);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Mentor} that we are building.
     */
    public MentorBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Mentor} that we are building.
     */
    public MentorBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Mentor} that we are building.
     */
    public MentorBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code organization} of the {@code Mentor} that we are building.
     */
    public MentorBuilder withOrganization(String organization) {
        this.organization = new Name(organization);
        return this;
    }

    /**
     * Sets the {@code SubjectName} of the {@code Mentor} that we are building.
     */
    public MentorBuilder withSubject(String subject) {
        this.subject = SubjectName.valueOf(subject.toUpperCase());
        return this;
    }

    /**
     * This builds a standard {@code Mentor} object.
     */
    public Mentor build() {
        return new Mentor(name, id, phone, email, organization, subject);
    }

}
