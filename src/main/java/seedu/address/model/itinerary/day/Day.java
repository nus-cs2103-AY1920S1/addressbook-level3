package seedu.address.model.itinerary.day;

import seedu.address.model.itinerary.Description;
import seedu.address.model.itinerary.Expenditure;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.day.exceptions.CompulsoryFieldEmptyException;
import seedu.address.model.itinerary.event.EventList;

import java.time.LocalDateTime;
import java.util.Optional;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Day in TravelPal.
 * Compulsory fields: name, startDate, endDate, destination, eventList.
 * Optional fields: totalBudget, description.
 */
public class Day {
    // Compulsory Fields
    private final Name name;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Location destination;
    private final EventList eventList;

    // Optional Fields
    private final Expenditure totalBudget;
    private final Description description;

    /** Constructs a Day */
    public Day (Name name, LocalDateTime startDate, LocalDateTime endDate, Description description
            , Location destination, Expenditure totalBudget, EventList eventList) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.destination = destination;
        this.totalBudget = totalBudget;
        this.eventList = eventList;
    }

    /** Constructs a day with optional fields */
    public Day (Name name, LocalDateTime startDate, LocalDateTime endDate, Optional<Description> description
            , Location destination, Optional<Expenditure> totalBudget, EventList eventList) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description.isPresent() ? description.get() : null;
        this.destination = destination;
        this.totalBudget = totalBudget.isPresent() ? totalBudget.get() : null;
        this.eventList = eventList;
    }

    /**Constructor for {@link Builder} */
    private Day (Builder builder) {
        try {
            requireAllNonNull(builder.name, builder.startDate, builder.endDate);
        } catch (NullPointerException e) {
            throw new CompulsoryFieldEmptyException();
        }
        this.name = builder.name;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.description = builder.description;
        this.destination = builder.destination;
        this.totalBudget = builder.totalBudget;
        this.eventList = builder.eventList;
    }


    // Compulsory Field getters
    public Name getName() {
        return name;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public EventList getEventList() {
        return eventList;
    }

    public Location getDestination() {
        return destination;
    }

    // Optional field getters
    public Optional<Description> getDescription() {
        return Optional.of(description);
    }

    public Optional<Expenditure> getTotalBudget() {
        return Optional.of(totalBudget);
    }

    public boolean isSameDay(Day otherDay) {
        if (otherDay == this) {
            return true;
        } else {
            return otherDay.getName().equals(getName())
                    && otherDay.getStartDate().equals(getStartDate())
                    && otherDay.getEndDate().equals(getEndDate())
                    && otherDay.getDestination().equals(getDestination());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Day)) {
            return false;
        }

        Day otherDay = (Day) other;
        return otherDay.getName().equals(getName())
                && otherDay.getStartDate().equals(getStartDate())
                && otherDay.getEndDate().equals(getEndDate())
                && otherDay.getDescription().equals(getDescription())
                && otherDay.getDestination().equals(getDestination())
                && otherDay.getEventList().equals(getEventList());
    }

    public boolean isClashingWith(Day other){
        return (this.getStartDate().compareTo(other.getStartDate()) >= 0
                && this.getStartDate().compareTo(other.getEndDate()) <= 0)
                || (this.getEndDate().compareTo(other.getStartDate()) >= 0
                && this.getEndDate().compareTo(other.getEndDate()) <= 0);
    }


    /**
     * Builder class to accommodate optional properties using builder pattern.
     * Can be used to construct {@link Day} without optional fields.
     */
    public static class Builder {
        private Name name;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private Description description;
        private Location destination;
        private Expenditure totalBudget;
        private EventList eventList;

        public static Builder newInstance (){
            return new Builder();
        }

        private Builder(){
            eventList = new EventList();
        }

        public static Builder of (Day day){
            requireAllNonNull(day.name, day.startDate, day.endDate, day.destination);
            return Builder.newInstance()
                    .setName(day.getName())
                    .setStartDate(day.startDate)
                    .setEndDate(day.endDate)
                    .setLocation(day.destination)
                    .setTotalBudget(day.totalBudget)
                    .setDescription(day.description)
                    .setEventList(day.eventList);
        }


        public Builder setName(Name name){
            this.name = name;
            return this;
        }

        public Builder setStartDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder setEndDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder setDescription (Description description){
            this.description = description;
            return this;
        }

        public Builder setLocation (Location location) {
            this.destination = location;
            return this;
        }

        public Builder setTotalBudget (Expenditure totalBudget){
            this.totalBudget = totalBudget;
            return this;
        }

        public Builder setEventList (EventList eventList) {
            this.eventList = eventList;
            return this;
        }

        public Day build(){
            return new Day(this);
        }

    }

}
