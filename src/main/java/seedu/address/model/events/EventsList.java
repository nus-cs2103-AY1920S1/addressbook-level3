package seedu.address.model.events;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.events.exceptions.EventNotFoundException;
import seedu.address.model.person.exceptions.DuplicatePersonException;

public class EventsList implements Iterable<EventSource> {

    private final ObservableList<EventSource> internalList = FXCollections.observableArrayList();
    private final ObservableList<EventSource> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);

    public boolean contains(EventSource toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    public void add(EventSource toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    public void setEvent(EventSource target, EventSource editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new EventNotFoundException();
        }

        internalList.set(index, editedPerson);
    }

    public void remove(EventSource toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EventNotFoundException();
        }
    }

    public void setEvents(EventsList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    public void setEvents(List<EventSource> events) {
        requireAllNonNull(events);
        internalList.setAll(events);
    }

    public ObservableList<EventSource> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<EventSource> iterator() {
        return internalList.iterator();
    }
}
