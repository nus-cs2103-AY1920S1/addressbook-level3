package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.schedule.Event;
import seedu.address.model.person.schedule.Schedule;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private static Integer counter = 0;
    private final PersonId personId;
    // Data fields
    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Remark remark;
    private Set<Tag> tags = new HashSet<>();
    private Schedule schedule;

    public Person(Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags) {
        requireAllNonNull(name);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.remark = remark;
        this.tags.addAll(tags);
        this.schedule = new Schedule();
        this.personId = new PersonId(counter);
        counter += 1;
    }

    public Person(PersonDescriptor personDescriptor) {
        this.name = personDescriptor.getName();
        this.phone = personDescriptor.getPhone();
        this.email = personDescriptor.getEmail();
        this.address = personDescriptor.getAddress();
        this.remark = personDescriptor.getRemark();
        this.tags.addAll(personDescriptor.getTags());
        this.schedule = new Schedule();
        this.personId = new PersonId(counter);
        counter += 1;
    }

    public void addTags(Set<Tag> tags) {
        this.tags.addAll(tags);
    }

    public void addEvent(Event event) {
        this.schedule.addEvent(event);
    }

    public PersonId getPersonId() {
        return this.personId;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Remark getRemark() {
        return remark;
    }

    public void setRemark(Remark remark) {
        this.remark = remark;
    }

    public Schedule getSchedule() {
        return this.schedule;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
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
        return otherPerson.getPersonId().equals(getPersonId())
                && otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getTags().equals(getTags())
                && otherPerson.getRemark().equals(getRemark());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, remark);
    }

    @Override
    public String toString() {
        String output = "";
        output += "(" + personId.toString() + ") ";
        output += name.toString();

        return output;
    }

    /**
     * Prints of all details of a person.
     * @return String
     */
    public String details() {
        String output = "";
        String notAvailable = "NOT AVAILABLE";
        output += this.toString() + "\n";

        output += "Phone: ";
        if (phone == null) {
            output += notAvailable + "\n";
        } else {
            output += phone.toString() + "\n";
        }

        output += "Email: ";
        if (email == null) {
            output += notAvailable + "\n";
        } else {
            output += email.toString() + "\n";
        }

        output += "Address: ";
        if (address == null) {
            output += notAvailable + "\n";
        } else {
            output += address.toString() + "\n";
        }

        output += "Remark: ";
        if (remark == null) {
            output += notAvailable + "\n";
        } else {
            output += remark.toString() + "\n";
        }

        output += "Tags: " + tags.toString() + "\n\n";
        output += schedule.toString();

        return output;
    }


}
