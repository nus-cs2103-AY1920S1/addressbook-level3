package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.exceptions.EventClashException;
import seedu.address.model.person.exceptions.EventNotFoundException;
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
        this.personId = new PersonId(counter);
        this.schedule = new Schedule(this.getPersonId());
        counter += 1;
    }

    public Person(PersonId personId, Name name, Phone phone, Email email,
                  Address address, Remark remark, Schedule schedule, Set<Tag> tags) {
        requireAllNonNull(name);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.remark = remark;
        this.tags.addAll(tags);
        this.personId = personId;
        this.schedule = schedule;
    }

    public Person(PersonDescriptor personDescriptor) {
        this.name = personDescriptor.getName();
        this.phone = personDescriptor.getPhone();
        this.email = personDescriptor.getEmail();
        this.address = personDescriptor.getAddress();
        this.remark = personDescriptor.getRemark();
        if (personDescriptor.getTags() != null) {
            this.tags.addAll(personDescriptor.getTags());
        }
        this.personId = new PersonId(counter);
        this.schedule = new Schedule(this.getPersonId());
        counter += 1;
    }

    public Person(PersonDescriptor personDescriptor, int personId) {
        this.name = personDescriptor.getName();
        this.phone = personDescriptor.getPhone();
        this.email = personDescriptor.getEmail();
        this.address = personDescriptor.getAddress();
        this.remark = personDescriptor.getRemark();
        if (personDescriptor.getTags() != null) {
            this.tags.addAll(personDescriptor.getTags());
        }
        this.personId = new PersonId(personId);
        this.schedule = new Schedule(this.getPersonId());
    }

    /**
     * Resets the counter for testing.
     */
    public static void counterReset() {
        counter = 0;
    }

    /**
     * Sets the counter to a specific value.
     * @param i value to be set to
     */
    public static void setCounter(int i) {
        counter = i;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons are the same instance of person.
     */
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else {
            Person otherPerson = null;
            if (other instanceof Person) {
                otherPerson = (Person) other;
            }

            if (otherPerson != null) {
                return this.isSamePerson(otherPerson) && otherPerson.getPersonId().equals(this.getPersonId());
            }
        }
        return false;
    }

    /**
     * Returns only if the two person's details are the same. Does not check if IDs are the same.
     */
    public boolean isSamePerson(Person otherPerson) {
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                //&& otherPerson.getTags().equals(getTags())
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

    public void addTags(Set<Tag> tags) {
        this.tags.addAll(tags);
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public void addEvent(Event event) throws EventClashException {
        this.schedule.addEvent(event);
    }

    public void deleteEvent(String eventName) throws EventNotFoundException {
        this.schedule.deleteEvent(eventName);
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

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

}
