package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfxtras.icalendarfx.components.VEvent;
import seedu.address.commons.core.index.Index;

/**
 * Stores events and provides functionality to map from events to vEvents.
 */
public class EventRecord implements ReadOnlyVEvents, ReadOnlyEvents, Iterable<VEvent> {
    private final String dailyRecurRUleString = "FREQ=DAILY;INTERVAL=1";
    private final String weeklyRecurRuleString = "FREQ=WEEKLY;INTERVAL=1";
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
            vEvent.setDescription("testDescription");
            eventList.add(vEventToEventMapper(vEvent));
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
            resultVEventList.add(eventToVEventMapper(event));
        }
        return resultVEventList;
    }

    /**
     * Maps a event to VEvent
     */
    private VEvent eventToVEventMapper(Event eventToMap) {
        VEvent resultVEvent = new VEvent();
        resultVEvent.setDateTimeStart(eventToMap.getStartDateTime());
        resultVEvent.setDateTimeEnd(eventToMap.getEndDateTime());
        resultVEvent.setUniqueIdentifier(eventToMap.getUniqueIdentifier());
        resultVEvent.setSummary(eventToMap.getEventName());

        if (eventToMap.getRecurrenceType() == RecurrenceType.DAILY) {
            resultVEvent.setRecurrenceRule(dailyRecurRUleString);
        } else if (eventToMap.getRecurrenceType() == RecurrenceType.WEEKLY) {
            resultVEvent.setRecurrenceRule(weeklyRecurRuleString);
        }

        resultVEvent.withCategories(eventToMap.getColorCategory());

        return resultVEvent;
    }

    /**
     * Maps a vEvent to event
     */
    private Event vEventToEventMapper(VEvent vEventToMap) {
        Event resultEvent = new Event();
        LocalDateTime startDateTime = LocalDateTime.parse(vEventToMap.getDateTimeStart().getValue().toString());
        LocalDateTime endDateTime = LocalDateTime.parse(vEventToMap.getDateTimeEnd().getValue().toString());
        String uniqueIdentifier = vEventToMap.getUniqueIdentifier().getValue();
        String eventName = vEventToMap.getSummary().getValue();
        resultEvent.setStartDateTime(startDateTime);
        resultEvent.setEndDateTime(endDateTime);
        resultEvent.setUniqueIdentifier(uniqueIdentifier);
        resultEvent.setEventName(eventName);

        if (vEventToMap.getRecurrenceRule() == null) {
            resultEvent.setRecurrenceType(RecurrenceType.NONE);
        } else if (vEventToMap.getRecurrenceRule().toString().contains("DAILY")) {
            resultEvent.setRecurrenceType(RecurrenceType.DAILY);
        } else if (vEventToMap.getRecurrenceRule().toString().contains("WEEKLY")) {
            resultEvent.setRecurrenceType(RecurrenceType.WEEKLY);
        }

        String colorCategory = vEventToMap.getCategories().get(0).getValue().get(0);
        resultEvent.setColorCategory(colorCategory);

        return resultEvent;
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
        this.vEvents.add(vEvent);
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
            //throw new EventNotFoundException();
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
            //throw new VEventNotFoundException();
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
        return vEvents.stream().anyMatch(toCheck::equals);
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

    @Override
    public Iterator<VEvent> iterator() {
        return vEvents.iterator();
    }
}
