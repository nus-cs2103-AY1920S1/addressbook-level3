package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.math3.util.Pair;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfxtras.icalendarfx.components.VEvent;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.EventUtil;
import seedu.address.commons.util.StringUtil;
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
    private String targetExportPath;

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
        resetData(events);
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
    public void resetData(List<Event> newData) {
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
            resultVEventList.add(EventUtil.eventToVEventMapper(event));
        }
        return resultVEventList;
    }

    /**
     * Replaces the contents of this list with {@code vEvents}.
     */
    public void setVEvents(List<VEvent> vEvents) {
        requireAllNonNull(vEvents);

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
        return vEvents.remove(index.getZeroBased());
    }

    /**
     * Deletes the vEvent object from the list.
     *
     * @param vEvent object.
     */
    public void deleteVEvent(VEvent vEvent) {
        requireNonNull(vEvent);
        if (!vEvents.remove(vEvent)) {
            throw new VEventNotFoundException();
        }
    }

    /**
     * Returns the question object.
     *
     * @param index of the question in the list.
     * @return Question object.
     */
    public VEvent getVEvent(Index index) {
        return vEvents.get(index.getZeroBased());
    }

    /**
     * Sets the vEvent object at the specified index in the list.
     *
     * @param index    of the vEvent in the list.
     * @param vEvent object.
     */
    public void setVEvent(Index index, VEvent vEvent) {
        vEvents.set(index.getZeroBased(), vEvent);
    }

    /**
     * Sets the vEvent object in the list using a specified question object.
     *
     * @param target         of the vEvent in the list.
     * @param editedVEvent to replace target.
     */
    public void setVEvent(VEvent target, VEvent editedVEvent) {
        requireAllNonNull(target, editedVEvent);

        int index = vEvents.indexOf(target);
        if (index == -1) {
            throw new VEventNotFoundException();
        }

        vEvents.set(index, editedVEvent);
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

    private boolean isSameVEvent(VEvent vEvent1, VEvent vEvent2) {
        return vEvent1.getSummary().equals(vEvent2.getSummary())
                && vEvent1.getDateTimeStart().equals(vEvent2.getDateTimeStart())
                && vEvent1.getDateTimeEnd().equals(vEvent2.getDateTimeEnd());
    }

    /**
     * Printing out the list of VEvents and how many are there.
     *
     * @return Summary of VEvents.
     */
    public String getVEventSummary() {
        String summary = "There are currently " + vEvents.size() + " vEvents saved.\n"
                + "Here is the list of vEVents:\n";

        for (int i = 0; i < vEvents.size(); i++) {
            summary += (i + 1) + ". " + "\"" + vEvents.get(i) + "\"";

            if ((i + 1) != vEvents.size()) {
                summary += "\n";
            }
        }

        return summary;
    }

    /**
     * Gets a list of pair of Index and VEvent which eventNames are equal to the desiredEventName
     *
     * @return List of pair of Indexs and VEvents which equal to desiredEventName
     */
    public List<Pair<Index, VEvent>> findVEventsIndex(String desiredEventName) {
        List<Pair<Index, VEvent>> resultIndexList = new ArrayList<>();
        for (int i = 0; i < vEvents.size(); i++) {
            VEvent currentVEvent = vEvents.get(i);
            if (currentVEvent.getSummary().getValue().equals(desiredEventName)) {
                resultIndexList.add(new Pair(Index.fromZeroBased(i), currentVEvent));
            }
        }
        return resultIndexList;
    }

    /**
     * Gets most similar VEvent to that of desiredEventName
     *
     * @return List a pair representing the most similar VEvent and its Index
     *
     * Will default to first VEvent in list if cannot be found
     */
    public Pair<Index, VEvent> findMostSimilarVEvent(String desiredEventName) {
        VEvent mostSimilarVEvent = vEvents.get(0);
        Integer mostSimilarIndex = 0;
        double highestSimilarityPercentage =
                StringUtil.calculateStringSimilarity(mostSimilarVEvent.getSummary().getValue(), desiredEventName);

        for (int i = 1; i < vEvents.size(); i++) {
            VEvent currentEvent = vEvents.get(i);
            double eventNameSimilarity =
                    StringUtil.calculateStringSimilarity(currentEvent.getSummary().getValue(), desiredEventName);
            if (eventNameSimilarity > highestSimilarityPercentage) {
                mostSimilarIndex = i;
                mostSimilarVEvent = currentEvent;
                highestSimilarityPercentage = eventNameSimilarity;
            }
        }

        return new Pair(Index.fromZeroBased(mostSimilarIndex), mostSimilarVEvent);
    }

    /**
     * Sets the target export file path of the events
     * @param targetExportPath string representation of the target file path
     */
    public void setEventExportPath(String targetExportPath) {
        this.targetExportPath = targetExportPath;
    }

    /**
     * gets the target event export file path
     * @return targetExportPath of eventRecord
     */
    public String getEventExportPath() {
        return this.targetExportPath;
    }

    @Override
    public Iterator<VEvent> iterator() {
        return vEvents.iterator();
    }
}
