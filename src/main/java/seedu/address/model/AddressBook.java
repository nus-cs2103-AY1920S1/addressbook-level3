package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.worker.Worker;
import seedu.address.model.notif.Notif;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniqueEntityLists;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueEntityLists entities;
    private final UniqueNotifsList notifs;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        entities = new UniqueEntityLists();
        notifs = new UniqueNotifsList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
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
    }

    /**
     * Replaces the contents of the person list with {@code entities}.
     * {@code entities} must not contain duplicate entities.
     */
    public void setNotif(List<Notif> notifs) {
        this.notifs.setNotifs(notifs);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setWorkers(newData.getWorkerList());
        setBodies(newData.getBodyList());
    }

    //// person-level operations

    /**
     * Returns true if an entity with the same identity as {@code entity} exists in Mortago.
     */
    public boolean hasEntity(Entity entity) {
        requireNonNull(entity);
        return entities.contains(entity);
    }

    /**
     * Adds an entity to Mortago.
     * The entity must not already exist in Mortago.
     */
    public void addEntity(Entity e) {
        entities.add(e);
    }

    /**
     * Replaces the given entity {@code target} in the list with {@code editedEntity}.
     * {@code target} must exist in Mortago.
     * The person identity of {@code editedEntity} must not be the same as another existing entity in Mortago.
     */
    public void setEntity(Entity target, Entity editedEntity) {
        requireNonNull(editedEntity);

        entities.setEntity(target, editedEntity);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeEntity(Entity key) {
        entities.remove(key);
    }


    /**
     * Returns true if a notif with the same identity as {@code notif} exists in Mortago.
     */
    public boolean hasNotif(Notif notif) {
        requireNonNull(notif);
        return notifs.contains(notif);
    }

    /**
     * Adds a notif to Mortago.
     * The notif must not already exist in Mortago.
     */
    public void addNotif(Notif n) {
        notifs.add(n);
    }

    /**
     * Replaces the given notif {@code target} in the list with {@code editedNotif}.
     * {@code target} must exist in Mortago.
     * The notif identity of {@code editedNotif} must not be the same as another existing notif in Mortago.
     */
    public void setNotif(Notif target, Notif editedNotif) {
        requireNonNull(editedNotif);

        notifs.setNotif(target, editedNotif);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeNotif(Notif key) {
        notifs.remove(key);
    }


    //// util methods

    @Override
    public String toString() {
        return entities.asUnmodifiableObservableListPerson().size() + " entities";
        // TODO: refine later
    }

    @Override
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
    }

    @Override
    public ObservableList<Notif> getNotifList() {
        return notifs.asUnmodifiableObservableListNotif();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && entities.equals(((AddressBook) other).entities));
    }

    @Override
    public int hashCode() {
        return entities.hashCode();
    }
}
