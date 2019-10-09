package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents an NUStudy lecture note. Its title and content are guaranteed non-null.
 */
public class Person {
    private final Title title;
    private final Content content;

    /**
     * Constructs a new lecture note. Both fields must be present and non-null.
     *
     * @param title The lecture note's title, which must be unique among all lecture notes.
     * @param content The lecture note's content (newlines are supported), which do not have to be unique.
     */
    public Person(Title title, Content content) {
        requireAllNonNull(title, content);
        this.title = title;
        this.content = content;
    }

    public Title getTitle() {
        return title;
    }

    public Content getContent() {
        return content;
    }

    /**
     * Returns true if both lecture notes have the same title.
     * This defines a weaker notion of equality.
     */
    public boolean isSameNote(Person other) {
        if (other == this) {
            return true;
        }

        return other != null && other.getTitle().equals(getTitle());
    }

    /**
     * Returns true if both lecture notes have the same title and content.
     * This defines a stronger notion of equality.
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
        return otherPerson.getTitle().equals(getTitle()) && otherPerson.getContent().equals(getContent());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, content);
    }

    @Override
    public String toString() {
        return getTitle() + "\n" + getContent();
    }
}
