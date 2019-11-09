package seedu.address.testutil;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Description;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.day.Day;
import seedu.address.model.itinerary.event.EventList;

/**
 * Builder class to accommodate optional properties using builder pattern.
 * Can be used to construct {@link Day} without optional fields.
 */
public class DayBuilder {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Description description;
    private Location destination;
    private Budget totalBudget;
    private EventList eventList;

    /*
    TODO
     */
    private DayBuilder() {
        eventList = new EventList(LocalDate.now().atStartOfDay());
    }

    public static DayBuilder newInstance() {
        return new DayBuilder();
    }

    /**
     * Constructs a {@code DayBuilder} from the specified day.
     *
     * @param day Day instance to use.
     * @return new DayBuilder instance.
     */
    public static DayBuilder of(Day day) {
        requireAllNonNull(day.getStartDate(), day.getEndDate(), day.getDestination(), day.getEventList());
        return DayBuilder.newInstance()
                .setStartDate(day.getStartDate())
                .setEndDate(day.getEndDate())
                .setLocation(day.getDestination())
                .setTotalBudget(day.getTotalBudget().orElse(null))
                .setDescription(day.getDescription().orElse(null))
                .setEventList(day.getEventList());
    }


    public DayBuilder setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public DayBuilder setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public DayBuilder setDescription(Description description) {
        this.description = description;
        return this;
    }

    public DayBuilder setLocation(Location location) {
        this.destination = location;
        return this;
    }

    public DayBuilder setTotalBudget(Budget totalBudget) {
        this.totalBudget = totalBudget;
        return this;
    }

    public DayBuilder setEventList(EventList eventList) {
        this.eventList = eventList;
        return this;
    }

    public Day build() {
        return new Day(startDate, endDate, description, destination, totalBudget, eventList, null);
    }

}
