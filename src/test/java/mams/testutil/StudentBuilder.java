package mams.testutil;

import java.util.HashSet;
import java.util.Set;

import mams.model.student.Email;
import mams.model.student.MatricId;
import mams.model.student.Name;
import mams.model.student.Phone;
import mams.model.student.Student;
import mams.model.tag.Tag;
import mams.model.util.SampleDataUtil;

/**
 * A utility class to help with building Student objects.
 */
public class StudentBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_MATRICID = "A0179930Y";

    private Name name;
    private Phone phone;
    private Email email;
    private MatricId matricId;
    private Set<Tag> tags;

    public StudentBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        matricId = new MatricId(DEFAULT_MATRICID);
        tags = new HashSet<>();
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        phone = studentToCopy.getPhone();
        email = studentToCopy.getEmail();
        matricId = studentToCopy.getMatricId();
        tags = new HashSet<>(studentToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     */
    public StudentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Student} that we are building.
     */
    public StudentBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code MatricId} of the {@code Student} that we are building.
     */
    public StudentBuilder withMatricId(String matricId) {
        this.matricId = new MatricId(matricId);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Student} that we are building.
     */
    public StudentBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Student} that we are building.
     */
    public StudentBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Student build() {
        return new Student(name, phone, email, matricId, tags);
    }

}
