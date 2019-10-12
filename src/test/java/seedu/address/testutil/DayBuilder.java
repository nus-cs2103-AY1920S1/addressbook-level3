package seedu.address.testutil;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;

import seedu.address.model.itinerary.Description;
import seedu.address.model.itinerary.Expenditure;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.day.Day;
import seedu.address.model.itinerary.event.EventList;

/**
 * Builder class to accommodate optional properties using builder pattern.
 * Can be used to construct {@link Day} without optional fields.
 */
public class DayBuilder {
    private Name name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Description description;
    private Location destination;
    private Expenditure totalBudget;
    private EventList eventList;

    private DayBuilder() {
        eventList = new EventList();
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
        requireAllNonNull(day.getName(), day.getStartDate(), day.getEndDate(), day.getDestination());
        return DayBuilder.newInstance()
                .setName(day.getName())
                .setStartDate(day.getStartDate())
                .setEndDate(day.getEndDate())
                .setLocation(day.getDestination())
                .setTotalBudget(day.getTotalBudget().get())
                .setDescription(day.getDescription().get())
                .setEventList(day.getEventList());
    }


    public DayBuilder setName(Name name) {
        this.name = name;
        return this;
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

    public DayBuilder setTotalBudget(Expenditure totalBudget) {
        this.totalBudget = totalBudget;
        return this;
    }

    public DayBuilder setEventList(EventList eventList) {
        this.eventList = eventList;
        return this;
    }

    public Day build() {
        return new Day(name, startDate, endDate, description, destination, totalBudget, eventList);
    }

}
