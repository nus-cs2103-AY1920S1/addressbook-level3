package seedu.address.model.event;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.employee.Employee;
import seedu.address.model.tag.Tag;

/**
 * Represents an Event in the AddMin app.
 */
public class Event {

    //Identity Fields
    private final EventName name;
    private final EventDate startDate;
    private final EventDate endDate;

    //data fields
    private final EventVenue venue;
    private final EventManpowerNeeded manpowerNeeded;
    private final EventManpowerAllocatedList manpowerAllocatedList;
    private final EventDateTimeMap eventDateTimeMap;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Basic Constructor, called for a new Event Creation.
     */
    public Event(EventName name, EventVenue venue,
                 EventManpowerNeeded manpowerNeeded, EventDate startDate,
                 EventDate endDate, Set<Tag> tags) {
        this.name = name;
        this.venue = venue;
        this.manpowerNeeded = manpowerNeeded;
        this.manpowerAllocatedList = new EventManpowerAllocatedList();
        this.eventDateTimeMap = new EventDateTimeMap();
        this.startDate = startDate;
        this.endDate = endDate;
        this.tags.addAll(tags);
        initalizeDateTime(startDate, endDate); //inserts default mapping for start and end date
    }

    /**
     * Constructor with ManpowerList and EventDateTimeMap.
     */
    public Event(EventName name, EventVenue venue,
                 EventManpowerNeeded manpowerNeeded, EventDate startDate,
                 EventDate endDate, EventManpowerAllocatedList manpowerAllocatedList,
                 EventDateTimeMap eventDateTimeMap, Set<Tag> tags) {
        this.name = name;
        this.venue = venue;
        this.manpowerNeeded = manpowerNeeded;
        this.manpowerAllocatedList = manpowerAllocatedList;
        this.eventDateTimeMap = eventDateTimeMap;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tags.addAll(tags);
    }

    public EventName getName() {
        return name;
    }

    public EventDate getStartDate() {
        return startDate;
    }

    public EventDate getEndDate() {
        return endDate;
    }

    public EventVenue getVenue() {
        return venue;
    }

    public EventManpowerNeeded getManpowerNeeded() {
        return manpowerNeeded;
    }

    public EventManpowerAllocatedList getManpowerAllocatedList() {
        return manpowerAllocatedList;
    }

    public EventDateTimeMap getEventDateTimeMap() {
        return eventDateTimeMap;
    }

    public List<EventDate> getListOfEventDates() {
        return eventDateTimeMap.getDateMappedList();
    }

    /**
     * Checks if an employee is available for this event.
     *
     * @param employee          to check
     * @param filteredEventList a complete event list.
     */
    public boolean isAvailableForEvent(Employee employee, List<Event> filteredEventList) {
        List<Event> containsEmployeeEventList = filteredEventList.stream()
                .filter(x -> x.manpowerAllocatedList.getManpowerList().contains(employee.getEmployeeId()))
                .collect(Collectors.toList());
        long nonOverlapEventsCount = containsEmployeeEventList.stream()
                .filter(x -> (startDate.getDate().compareTo(x.getEndDate().getDate()) > 0
                        && endDate.getDate().compareTo(x.getEndDate().getDate()) > 0)
                        || (startDate.getDate().compareTo(x.getStartDate().getDate()) < 0
                        && endDate.getDate().compareTo(x.getStartDate().getDate()) < 0)).count();
        return nonOverlapEventsCount == containsEmployeeEventList.size();
    }

    /**
     * Checks if an Employee is allocated to this Event.
     */
    public boolean employeeIsAllocated(Employee employee) {
        return manpowerAllocatedList.containsEmployee(employee);
    }

    /**
     * Assigns the EventDate-EventDayTime mapping to the EventDateTimeMap object.
     *
     * @param eventDate       Target Date to be assigned.
     * @param eventTimePeriod Time Period Event is being held.
     */
    public void assignDateTime(EventDate eventDate, EventDayTime eventTimePeriod) {
        this.eventDateTimeMap.mapDateTime(eventDate, eventTimePeriod);
    }

    /**
     * When Event object is first created, auto-initialize DateTime mapping for Start&End Date.
     */
    public void initalizeDateTime(EventDate startDate, EventDate endDate) {
        EventDayTime standard = EventDayTime.defaultEventDayTime();
        assignDateTime(startDate, standard);
        assignDateTime(endDate, standard);
    }

    /**
     * Clears all DateTime Mappings associated with the
     */
    public void clearDateTimeMapping() {
        this.eventDateTimeMap.clearMapping();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getName().equals(getName())
                && otherEvent.getStartDate().equals(getStartDate())
                && otherEvent.getEndDate().equals(getEndDate());
    }

    /**
     * Returns true if both events have the same identity and data fields.
     * This defines a stronger notion of equality between two events.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getName().equals(getName())
                && otherEvent.getVenue().equals(getVenue())
                && otherEvent.getManpowerNeeded().equals(getManpowerNeeded())
                && otherEvent.getStartDate().equals(getStartDate())
                && otherEvent.getEndDate().equals(getEndDate())
                && otherEvent.getManpowerAllocatedList().equals(getManpowerAllocatedList())
                && otherEvent.getEventDateTimeMap().equals(getEventDateTimeMap())
                && otherEvent.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, venue, manpowerNeeded,
                startDate, endDate, manpowerAllocatedList, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        builder.append(" Event Venue: ").append(getVenue());
        builder.append(" Event Manpower Needed: ").append(getManpowerNeeded());
        builder.append(" Event Start Date: ").append(getStartDate());
        builder.append(" Event End Date: ").append(getEndDate());
        builder.append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Returns a similar toString() variation with new line for GUI display.
     */
    public String toStringWithNewLine() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Event Name: ").append(getName());
        builder.append(" \nEvent Venue: ").append(getVenue());
        builder.append(" \nEvent Manpower Count: ").append(getCurrentManpowerCount() + " / ")
                .append(getManpowerNeeded());
        builder.append(" \nEvent Date: ").append(getStartDate()).append(" - " + getEndDate());
        builder.append(" \nAll Event Date/Time: \n").append(getEventDateTimeMap().toStringWithNewLine());
        builder.append(" \nTags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    public int getCurrentManpowerCount() {
        return manpowerAllocatedList.getManpowerList().size();
    }
}
