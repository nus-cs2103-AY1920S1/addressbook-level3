package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.EventUtil.eventToVEventMapper;
import static seedu.address.commons.util.EventUtil.isSameVEvent;
import static seedu.address.commons.util.StringUtil.calculateStringSimilarity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.math3.util.Pair;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfxtras.icalendarfx.components.VEvent;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.EventUtil;
import seedu.address.model.event.exceptions.DuplicateVEventException;
import seedu.address.model.event.exceptions.VEventNotFoundException;


/**
 * Stores events and provides functionality to map from events to vEvents.
 * VEvents with the same eventName, startDateTime and endDateTime are not allowed.
 */
public class EventRecord implements ReadOnlyVEvents, ReadOnlyEvents, Iterable<VEvent> {
    private final ObservableList<VEvent> vEvents = FXCollections.observableArrayList();
    private final ObservableList<VEvent> vEventsUnmodifiableList =
            FXCollections.unmodifiableObservableList(vEvents);

    public EventRecord() {
    }

    /**
     * Creates a list of VEvents using the events in the {@code toBeCopied}
     */
    public EventRecord(ReadOnlyEvents toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Creates a list of VEvents using the events in the {@param events}
     */
    public EventRecord(List<Event> events) {
        this();
        resetDataWithEventList(events);
    }

    /**
     * Get all Events backed by vEvents observable list.
     */
    public List<Event> getAllEvents() {
        return vEventsToEventsMapper(this.vEvents);
    }

    /**
     * Maps all @param vEvents to a list of Events
     */
    private List<Event> vEventsToEventsMapper(List<VEvent> vEvents) {
        ArrayList<Event> eventList = new ArrayList<>();
        for (VEvent vEvent : vEvents) {
            eventList.add(EventUtil.vEventToEventMapper(vEvent));
        }
        return eventList;
    }

    /**
     * Resets the existing data of this {@code EventRecord} with {@code newData}.
     */
    public void resetData(ReadOnlyEvents newData) {
        requireNonNull(newData);

        setVEvents(readOnlyEventsToVEventsMapper(newData));
    }

    /**
     * Resets the existing data of this {@code EventRecord} with {@code newData}.
     */
    public void resetDataWithEventList(List<Event> newData) {
        requireNonNull(newData);

        setVEvents(eventsToVEventsMapper(newData));
    }

    /**
     * Maps all Events in @param readOnlyEvents to a list of VEvents
     */
    private ArrayList<VEvent> readOnlyEventsToVEventsMapper(ReadOnlyEvents readOnlyEvents) {
        return eventsToVEventsMapper(readOnlyEvents.getAllEvents());
    }

    /**
     * Maps all Events in @param events to a list of VEvents
     */
    private ArrayList<VEvent> eventsToVEventsMapper(List<Event> events) {
        ArrayList<VEvent> resultVEventList = new ArrayList<>();
        for (Event event : events) {
            resultVEventList.add(eventToVEventMapper(event));
        }
        return resultVEventList;
    }

    /**
     * Replaces the contents of this list with {@code vEvents}.
     * vEvents must be unique.
     */
    public void setVEvents(List<VEvent> vEvents) {
        requireAllNonNull(vEvents);
        if (!vEventsAreUnique(vEvents)) {
            throw new DuplicateVEventException();
        }
        this.vEvents.setAll(vEvents);
    }

    /**
     * Add a new vEvent to the vEvents list.
     *
     * @param vEvent to add to the list.
     */
    public void addVEvent(VEvent vEvent) {
        requireNonNull(vEvent);
        if (contains(vEvent)) {
            throw new DuplicateVEventException();
        }
        vEvents.add(vEvent);
    }

    /**
     * Deletes the vEvent at the specified index in the list.
     *
     * @param index of the vEvent in the list.
     * @return VEvent object.
     */
    public VEvent deleteVEvent(Index index) {
        requireNonNull(index);
        return vEvents.remove(index.getZeroBased());
    }

    /**
     * Returns the VEvent object.
     *
     * @param index of the question in the list.
     * @return VEvent object.
     */
    public VEvent getVEvent(Index index) {
        requireNonNull(index);
        return vEvents.get(index.getZeroBased());
    }

    /**
     * Sets the vEvent object at the specified index in the list. This should not result in a duplicate
     * VEvent
     * @param index    of the vEvent in the list.
     * @param vEvent object.
     */
    public void setVEvent(Index index, VEvent vEvent) {
        VEvent targetVEvent = vEvents.get(index.getZeroBased());
        if (!isSameVEvent(targetVEvent, vEvent) && contains(vEvent)) {
            throw new DuplicateVEventException();
        }
        vEvents.set(index.getZeroBased(), vEvent);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<VEvent> getVEventList() {
        return this.vEventsUnmodifiableList;
    }

    /**
     * Returns true if the list contains an equivalent VEvent as the given argument.
     */
    public boolean contains(VEvent toCheck) {
        requireNonNull(toCheck);
        return vEvents.stream().anyMatch(vEvent -> isSameVEvent(vEvent, toCheck));
    }



    /**
     * Gets a list of pair of Index and VEvent which eventNames are equal to the desiredEventName
     *
     * @return List of pair of Indexs and VEvents which equal to desiredEventName
     */
    public List<Pair<Index, VEvent>> findVEvents(String desiredEventName) {
        requireNonNull(desiredEventName);

        List<Pair<Index, VEvent>> resultIndexList = new ArrayList<>();
        for (int i = 0; i < vEvents.size(); i++) {
            VEvent currentVEvent = vEvents.get(i);
            if (currentVEvent.getSummary().getValue().equalsIgnoreCase(desiredEventName)) {
                resultIndexList.add(new Pair(Index.fromZeroBased(i), currentVEvent));
            }
        }
        return resultIndexList;
    }

    /**
     * Finds the most similar event to the event name specified
     * @param desiredEventName desiredEventName to be found
     * @return a vEvent object that is closest to the event name specified
     * @throws VEventNotFoundException if there are no VEvents at all.
     */
    public Pair<Index, VEvent> findMostSimilarVEvent(String desiredEventName) throws VEventNotFoundException {
        requireNonNull(desiredEventName);

        if (vEvents.isEmpty()) {
            throw new VEventNotFoundException();
        }
        VEvent mostSimilarVEvent = vEvents.get(0);
        Integer mostSimilarIndex = 0;
        double highestSimilarityPercentage =
                calculateStringSimilarity(mostSimilarVEvent.getSummary().getValue(), desiredEventName);

        for (int i = 1; i < vEvents.size(); i++) {
            VEvent currentEvent = vEvents.get(i);
            double eventNameSimilarity =
                    calculateStringSimilarity(currentEvent.getSummary().getValue(), desiredEventName);
            if (eventNameSimilarity > highestSimilarityPercentage) {
                mostSimilarIndex = i;
                mostSimilarVEvent = currentEvent;
                highestSimilarityPercentage = eventNameSimilarity;
            }
        }

        return new Pair(Index.fromZeroBased(mostSimilarIndex), mostSimilarVEvent);
    }


    /**
     * Validates if all events in the list are unique
     * @param vEventList list to be checked
     * @return true if all events are unique
     */
    private boolean vEventsAreUnique(List<VEvent> vEventList) {
        for (int i = 0; i < vEventList.size() - 1; i++) {
            for (int j = i + 1; j < vEventList.size(); j++) {
                if (isSameVEvent(vEventList.get(i), vEventList.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Iterator<VEvent> iterator() {
        return vEvents.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EventRecord)) {
            return false;
        }

        EventRecord otherEventRecord = (EventRecord) other;
        ObservableList<VEvent> thisVEventList = this.getVEventList();
        ObservableList<VEvent> otherVEventList = otherEventRecord.getVEventList();

        if (thisVEventList.size() != (otherVEventList.size())) {
            return false;
        }

        for (int i = 0; i < thisVEventList.size(); i++) {
            VEvent thisVEvent = thisVEventList.get(i);
            VEvent otherVEvent = otherVEventList.get(i);
            if (!thisVEvent.getSummary().equals(otherVEvent.getSummary())
                    || !thisVEvent.getDateTimeEnd().equals(otherVEvent.getDateTimeEnd())
                    || !thisVEvent.getDateTimeStart().equals(otherVEvent.getDateTimeStart())
                    || !thisVEvent.getCategories().equals(otherVEvent.getCategories())) {
                return false;
            }

        }
        return true;
    }
}
