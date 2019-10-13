package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.Tag;
import seedu.address.model.visit.Visit;
import seedu.address.model.visittodo.VisitTodo;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    public static final List<Visit> PLACEHOLDER_NO_VISITS = Collections.unmodifiableList(new ArrayList<>());

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Collection<VisitTodo> visitTodos = new LinkedHashSet<>();
    private final List<Visit> visits = new ArrayList<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                  Collection<VisitTodo> visitTodos, List<Visit> visits) {
        requireAllNonNull(name, phone, email, address, tags, visitTodos);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.visitTodos.addAll(visitTodos);
        this.visits.addAll(visits);
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

    /**
     * Returns an immutable visitTodo collection, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Collection<VisitTodo> getVisitTodos() {
        return Collections.unmodifiableCollection(visitTodos);
    }

    /**
     * Returns an immutable visit list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Visit> getVisits() {
        return Collections.unmodifiableList(visits);
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

        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getTags().equals(getTags())
                && otherPerson.getVisits().equals(getVisits())
                && CollectionUtil.checkEqual(getVisitTodos(), otherPerson.getVisitTodos());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, visitTodos, visits);
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
        builder.append(" Visit Todos: ");
        getVisitTodos().forEach(builder::append);
        builder.append(" Visits: ");
        getVisits().forEach(builder::append);
        return builder.toString();
    }

}
