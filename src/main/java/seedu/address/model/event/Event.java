package seedu.address.model.event;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents an Event in the AddMin app.
 */
public class Event {

    //Identity Fields
    private final EventId id;
    private final EventName name;

    //Data Fields
    private final EventVenue venue;
    private final EventHoursNeeded hoursNeeded;
    private final EventManpowerNeeded manpowerNeeded;
    private final EventStartDate startDate;
    private final EventEndDate endDate;
    private final EventManpowerAllocatedList manpowerAllocatedList;
    private final Set<Tag> tags = new HashSet<>();

    public Event(EventId id, EventName name, EventVenue venue, EventHoursNeeded hoursNeeded,
                 EventManpowerNeeded manpowerNeeded, EventStartDate startDate, EventEndDate endDate, Set<Tag> tags) {
        this.id = id;
        this.name = name;
        this.venue = venue;
        this.hoursNeeded = hoursNeeded;
        this.manpowerNeeded = manpowerNeeded;
        this.startDate = startDate;
        this.endDate = endDate;
        this.manpowerAllocatedList = new EventManpowerAllocatedList();
        this.tags.addAll(tags);
    }
}
