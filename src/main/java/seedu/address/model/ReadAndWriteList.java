package seedu.address.model;

import java.util.NoSuchElementException;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Address book that behaves like a list.
 * @param <T> Interviewee or Interviewer.
 */
public interface ReadAndWriteList<T extends Person> extends ReadOnlyList<T> {
    /**
     * Adds an entity to the book. This book will not contain duplicates.
     */
    void addEntity(T person);

    /**
     * Edits the target entity with details from the {@code editedTarget}.
     * @throws PersonNotFoundException if the target does not exist in the list.
     */
    void setEntity(T target, T editedTarget) throws PersonNotFoundException;

    /**
     * Removes the entity with specified name from the list. The entity must exist in the list.
     * @throws PersonNotFoundException if the person does not exist in the list.
     */
    void removeEntity(T person) throws PersonNotFoundException;

    /**
     * Returns true if the list contains {@code targe}, false otherwise.
     */
    boolean hasEntity(T target);

    /**
     * Gets the entity with specified name.
     * @throws NoSuchElementException if nobody with the name exists.
     */
    T getEntity(Name name) throws PersonNotFoundException;


}
