package seedu.address.model.incident;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.incident.exceptions.DuplicateIncidentException;
import seedu.address.model.incident.exceptions.IncidentNotFoundException;

/**
 * A list of Incidents that enforces uniqueness between its elements and does not allow nulls.
 * A Incident is considered unique by comparing using {@code Incident#isSameIncident(Incident)}.
 * As such, adding and updating of Incidents uses Incident#isSameIncident(Incident) for equality,
 * so as to ensure that the Incident being added or updated is unique
 * in terms of identity in the UniqueIncidentList.
 * However, the removal of a Incident uses Incident#equals(Object)
 * so as to ensure that the Incident with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Incident#isSameIncident(Incident)
 */
public class UniqueIncidentList implements Iterable<Incident> {

    private final ObservableList<Incident> internalList = FXCollections.observableArrayList();
    private final ObservableList<Incident> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent Incident as the given argument.
     */
    public boolean contains(Incident toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameIncident);
    }

    /**
     * Adds a Incident to the list.
     * The Incident must not already exist in the list.
     */
    public void add(Incident toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateIncidentException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the Incident {@code target} in the list with {@code editedIncident}.
     * {@code target} must exist in the list.
     * The Incident identity of {@code editedIncident} must not be the same as another existing Incident in the list.
     */
    public void setIncident(Incident target, Incident editedIncident) {
        requireAllNonNull(target, editedIncident);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new IncidentNotFoundException();
        }

        if (!target.isSameIncident(editedIncident) && contains(editedIncident)) {
            throw new DuplicateIncidentException();
        }

        internalList.set(index, editedIncident);
    }

    /**
     * Removes the equivalent Incident from the list.
     * The Incident must exist in the list.
     */
    public void remove(Incident toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new IncidentNotFoundException();
        }
    }

    public void setIncidents(UniqueIncidentList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code Incidents}.
     * {@code Incidents} must not contain duplicate Incidents.
     */
    public void setIncidents(List<Incident> incidents) {
        requireAllNonNull(incidents);
        if (!incidentsAreUnique(incidents)) {
            throw new DuplicateIncidentException();
        }

        internalList.setAll(incidents);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Incident> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Incident> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueIncidentList // instanceof handles nulls
                && internalList.equals(((UniqueIncidentList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code Incidents} contains only unique Incidents.
     */
    private boolean incidentsAreUnique(List<Incident> incidents) {
        for (int i = 0; i < incidents.size() - 1; i++) {
            for (int j = i + 1; j < incidents.size(); j++) {
                if (incidents.get(i).isSameIncident(incidents.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

