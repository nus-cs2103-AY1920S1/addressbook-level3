package seedu.address.model.person;

import seedu.address.model.tag.Tag;
import seedu.address.model.timetable.Timetable;

import java.util.*;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final ProfilePicture profilePicture;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final List<String> projects = new ArrayList<>();
    private final Timetable timeTable;
    private final Performance performance;

    /**
     * Every field must be present and not null, except for timeTable which can be null.
     */
    public Person(Name name, Phone phone, Email email, ProfilePicture profilePicture, Address address, Set<Tag> tags, Timetable timeTable, Performance performance) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.profilePicture = profilePicture;
        this.address = address;
        this.tags.addAll(tags);
        this.timeTable = timeTable;
        this.performance = performance;
    }

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, ProfilePicture profilePicture, Address address, Set<Tag> tags) {
        this(name, phone, email, profilePicture, address, tags, new Timetable(new ArrayList<>()), new Performance());
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public ProfilePicture getProfilePicture() {
        return profilePicture;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public List<String> getProjects() {
        return this.projects;
    }

    public Timetable getTimeTable() {
        return timeTable;
    }

    public Performance getPerformance() {
        return performance;
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName())
                && (otherPerson.getPhone().equals(getPhone()) || otherPerson.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     * Timetable field is not included in comparison of equality.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getProfilePicture().equals(getProfilePicture())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getTags().equals(getTags())
                && ((this.getTimeTable() == null && otherPerson.getTimeTable() == null)
                    || otherPerson.getTimeTable().equals(getTimeTable()))
                && otherPerson.getPerformance().equals(getPerformance());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        //changed to .hash(name) instead of .hash(name, phone, email, profilePicture, address, tags, timeTable) to better get contact given a name.
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");

        getTags().forEach(builder::append);
        builder.append(" TimeTable: ")
                .append(getTimeTable());

        return builder.toString();
    }

}
