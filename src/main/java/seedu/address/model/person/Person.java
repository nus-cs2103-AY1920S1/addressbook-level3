package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the category book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Question question;
    private final Difficulty difficulty;

    // Data fields
    private final Category category;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Question question, Difficulty difficulty, Category category, Set<Tag> tags) {
        requireAllNonNull(question, difficulty, category, tags);
        this.question = question;
        this.difficulty = difficulty;
        this.category = category;
        this.tags.addAll(tags);
    }

    public Question getQuestion() {
        return question;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Category getCategory() {
        return category;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same question have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getQuestion().equals(getQuestion()) && (otherPerson.getDifficulty().equals(getDifficulty()));
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
        return otherPerson.getQuestion().equals(getQuestion())
                && otherPerson.getDifficulty().equals(getDifficulty())
                && otherPerson.getCategory().equals(getCategory())
                && otherPerson.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(question, difficulty, category, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getQuestion())
                .append(" Difficulty: ")
                .append(getDifficulty())
                .append(" Category: ")
                .append(getCategory())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
