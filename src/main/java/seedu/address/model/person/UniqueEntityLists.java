package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.body.Body;
import seedu.address.model.entity.exceptions.DuplicateEntityException;
import seedu.address.model.entity.exceptions.EntityNotFoundException;
import seedu.address.model.entity.worker.Worker;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Lists of entities that enforces uniqueness between its elements and does not allow nulls.
 * An entity is considered unique by comparing using {@code Entity#isSameEntity(Entity)}. As such, adding and updating
 * of entities uses Entity#isSameEntity(Entity) for equality so as to ensure that the entity being added or updated is
 * unique in terms of identity in the UniqueEntityList. However, the removal of an entity uses Entity#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 */
public class UniqueEntityLists {

    private final ObservableList<Person> internalListPerson = FXCollections.observableArrayList();
    private final ObservableList<Person> internalUnmodifiableListPerson =
        FXCollections.unmodifiableObservableList(internalListPerson);

    private final ObservableList<Worker> internalListWorker = FXCollections.observableArrayList();
    private final ObservableList<Worker> internalUnmodifiableListWorker =
        FXCollections.unmodifiableObservableList(internalListWorker);

    private final ObservableList<Body> internalListBody = FXCollections.observableArrayList();
    private final ObservableList<Body> internalUnmodifiableListBody =
        FXCollections.unmodifiableObservableList(internalListBody);

    /**
     * Returns true if the respective list contains an equivalent entity as the given argument.
     */
    public boolean contains(Entity toCheck) {
        requireNonNull(toCheck);
        ObservableList list;
        if (toCheck instanceof Worker) {
            list = internalListWorker;
        } else if (toCheck instanceof Body) {
            list = internalListBody;
        } else {
            list = internalListPerson;
        }
        return list.stream().anyMatch(toCheck::isSameEntity);
    }

    /**
     * Adds an entity to the respective list.
     * The entity must not already exist in the list.
     */
    public void add(Entity toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateEntityException();
        }
        if (toAdd instanceof Worker) {
            internalListWorker.add((Worker) toAdd);
        } else if (toAdd instanceof Body) {
            internalListBody.add((Body) toAdd);
        } else {
            internalListPerson.add((Person) toAdd);
        }
    }

    /**
     * Replaces the entity {@code target} in the list with {@code editedEntity}.
     * {@code target} must exist in the list.
     * The entity identity of {@code editedEntity} must not be the same as another existing entity in the list.
     */
    public void setEntity(Entity target, Entity editedEntity) {
        requireAllNonNull(target, editedEntity);

        if (!target.isSameEntity(editedEntity) && contains(editedEntity)) {
            throw new DuplicatePersonException();
        }

        int index;
        if (target instanceof Worker) {
            index = internalListWorker.indexOf((Worker) target);
        } else if (target instanceof Body) {
            index = internalListBody.indexOf((Body) target);
        } else {
            index = internalListPerson.indexOf((Person) target);
        }
        if (index == -1) {
            throw new EntityNotFoundException();
        }

        if (editedEntity instanceof Worker) {
            internalListWorker.set(index, (Worker) editedEntity);
        } else if (editedEntity instanceof Body) {
            internalListBody.set(index, (Body) editedEntity);
        } else {
            internalListPerson.set(index, (Person) editedEntity);
        }
    }

    /**
     * Removes the equivalent entity from the list.
     * The entity must exist in the list.
     */
    public void remove(Entity toRemove) {
        requireNonNull(toRemove);
        boolean isRemoved;
        if (toRemove instanceof Worker) {
            isRemoved = internalListWorker.remove((Worker) toRemove);
        } else if (toRemove instanceof Body) {
            isRemoved = internalListBody.remove((Body) toRemove);
        } else {
            isRemoved = internalListPerson.remove((Person) toRemove);
        }
        if (!isRemoved) {
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(UniqueEntityLists replacement) {
        requireNonNull(replacement);
        internalListBody.setAll(replacement.internalListBody);
        internalListWorker.setAll(replacement.internalListWorker);
        internalListPerson.setAll(replacement.internalListPerson);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        requireAllNonNull(persons);
        if (!entitiesAreUnique(persons)) {
            throw new DuplicatePersonException();
        }

        internalListPerson.setAll(persons);
    }

    /**
     * Replaces the contents of this list with {@code workers}.
     * {@code workers} must not contain duplicate workers.
     */
    public void setWorkers(List<Worker> workers) {
        requireAllNonNull(workers);
        if (!entitiesAreUnique(workers)) {
            throw new DuplicateEntityException();
        }

        internalListWorker.setAll(workers);
    }

    /**
     * Replaces the contents of this list with {@code bodies}.
     * {@code bodies} must not contain duplicate bodies.
     */
    public void setBodies(List<Body> bodies) {
        requireAllNonNull(bodies);
        if (!entitiesAreUnique(bodies)) {
            throw new DuplicateEntityException();
        }

        internalListBody.setAll(bodies);
    }

    /**
     * Returns the backing Person list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Person> asUnmodifiableObservableListPerson() {
        return internalUnmodifiableListPerson;
    }

    /**
     * Returns the backing Worker list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Worker> asUnmodifiableObservableListWorker() {
        return internalUnmodifiableListWorker;
    }

    /**
     * Returns the backing Body list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Body> asUnmodifiableObservableListBody() {
        return internalUnmodifiableListBody;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueEntityLists // instanceof handles nulls
            && (internalListPerson.equals(((UniqueEntityLists) other).internalListPerson))
            && (internalListWorker.equals(((UniqueEntityLists) other).internalListWorker))
            && internalListBody.equals(((UniqueEntityLists) other).internalListBody));
    }

    @Override
    public int hashCode() {
        return Objects.hash(internalListBody, internalListWorker, internalListPerson);
    }

    /**
     * Returns true if {@code entities} contains only unique entities.
     */
    private boolean entitiesAreUnique(List<? extends Entity> entities) {
        for (int i = 0; i < entities.size() - 1; i++) {
            for (int j = i + 1; j < entities.size(); j++) {
                if (entities.get(i).isSameEntity(entities.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
