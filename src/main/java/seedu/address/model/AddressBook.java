package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
<<<<<<< HEAD
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.body.UniqueBodyList;
=======
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.worker.Worker;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniqueEntityLists;
>>>>>>> d8120571da238197ac44b5e50f6178bf830b78b4

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameBody comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

<<<<<<< HEAD
    private final UniqueBodyList bodies;
=======
    private final UniqueEntityLists entities;
>>>>>>> d8120571da238197ac44b5e50f6178bf830b78b4

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
<<<<<<< HEAD
        bodies = new UniqueBodyList();
=======
        entities = new UniqueEntityLists();
>>>>>>> d8120571da238197ac44b5e50f6178bf830b78b4
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Bodies in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
<<<<<<< HEAD
     * Replaces the contents of the body list with {@code bodies}.
     * {@code bodies} must not contain duplicate bodies.
     */
    public void setBodies(List<Body> bodies) {
        this.bodies.setBodies(bodies);
=======
     * Replaces the contents of the person list with {@code entities}.
     * {@code entities} must not contain duplicate entities.
     */
    public void setPersons(List<Person> persons) {
        this.entities.setPersons(persons);
    }

    /**
     * Replaces the contents of the workers list with {@code workers}.
     * {@code workers} must not contain duplicate workers.
     */
    public void setWorkers(List<Worker> workers) {
        this.entities.setWorkers(workers);
    }

    /**
     * Replaces the contents of the bodies list with {@code bodies}.
     * {@code bodies} must not contain duplicate bodies.
     */
    public void setBodies(List<Body> bodies) {
        this.entities.setBodies(bodies);
>>>>>>> d8120571da238197ac44b5e50f6178bf830b78b4
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

<<<<<<< HEAD
=======
        setPersons(newData.getPersonList());
        setWorkers(newData.getWorkerList());
>>>>>>> d8120571da238197ac44b5e50f6178bf830b78b4
        setBodies(newData.getBodyList());
    }

    //// body-level operations

    /**
<<<<<<< HEAD
     * Returns true if a body with the same identity as {@code body} exists in the address book.
     */
    public boolean hasBody(Body body) {
        requireNonNull(body);
        return bodies.contains(body);
    }

    /**
     * Adds a body to the address book.
     * The body must not already exist in the address book.
     */
    public void addBody(Body p) {
        bodies.add(p);
    }

    /**
     * Replaces the given body {@code target} in the list with {@code editedBody}.
     * {@code target} must exist in the address book.
     * The body identity of {@code editedBody} must not be the same as another existing body in the address book.
     */
    public void setBody(Body target, Body editedBody) {
        requireNonNull(editedBody);

        bodies.setBody(target, editedBody);
=======
     * Returns true if an identity with the same identity as {@code identity} exists in the address book.
     */
    public boolean hasEntity(Entity entity) {
        requireNonNull(entity);
        return entities.contains(entity);
    }

    /**
     * Adds an entity to the address book.
     * The person must not already exist in the address book.
     */
    public void addEntity(Entity e) {
        entities.add(e);
    }

    /**
     * Replaces the given entity {@code target} in the list with {@code editedEntity}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedEntity} must not be the same as another existing entity in the address book.
     */
    public void setEntity(Entity target, Entity editedEntity) {
        requireNonNull(editedEntity);

        entities.setEntity(target, editedEntity);
>>>>>>> d8120571da238197ac44b5e50f6178bf830b78b4
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
<<<<<<< HEAD
    public void removeBody(Body key) {
        bodies.remove(key);
=======
    public void removeEntity(Entity key) {
        entities.remove(key);
>>>>>>> d8120571da238197ac44b5e50f6178bf830b78b4
    }

    //// util methods

    @Override
    public String toString() {
<<<<<<< HEAD
        return bodies.asUnmodifiableObservableList().size() + " bodies";
=======
        return entities.asUnmodifiableObservableListPerson().size() + " entities";
>>>>>>> d8120571da238197ac44b5e50f6178bf830b78b4
        // TODO: refine later
    }

    @Override
<<<<<<< HEAD
    public ObservableList<Body> getBodyList() {
        return bodies.asUnmodifiableObservableList();
=======
    public ObservableList<Person> getPersonList() {
        return entities.asUnmodifiableObservableListPerson();
    }

    @Override
    public ObservableList<Worker> getWorkerList() {
        return entities.asUnmodifiableObservableListWorker();
    }

    @Override
    public ObservableList<Body> getBodyList() {
        return entities.asUnmodifiableObservableListBody();
>>>>>>> d8120571da238197ac44b5e50f6178bf830b78b4
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
<<<<<<< HEAD
                && bodies.equals(((AddressBook) other).bodies));
=======
                && entities.equals(((AddressBook) other).entities));
>>>>>>> d8120571da238197ac44b5e50f6178bf830b78b4
    }

    @Override
    public int hashCode() {
<<<<<<< HEAD
        return bodies.hashCode();
=======
        return entities.hashCode();
>>>>>>> d8120571da238197ac44b5e50f6178bf830b78b4
    }
}
