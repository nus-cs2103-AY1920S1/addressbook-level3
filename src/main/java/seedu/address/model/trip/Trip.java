package seedu.address.model.trip;

import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.model.itinerary.Expenditure;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.day.DayList;
import seedu.address.model.trip.exceptions.CompulsoryFieldEmptyException;

import java.time.LocalDateTime;
import java.util.Optional;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Trip in TravelPal.
 * Compulsory fields: name, startDate, endDate, destination, dayList, totalBudget
 */
public class Trip {
    // Compulsory Fields
    private final Name name;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final TripId tripId;
    private final Location destination;
    private final DayList dayList;
    private final Expenditure totalBudget;

    /**
     * Constructs a trip.
     */
    public Trip (Name name, LocalDateTime startDate, LocalDateTime endDate,
                      Location destination, Expenditure totalBudget, DayList dayList) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.destination = destination;
        this.totalBudget = totalBudget;
        this.dayList = dayList;
        this.tripId = new TripId();
    }

    /**
     * Constructor for {@link Builder}.
     */
    private Trip (Builder builder) {
        try {
            requireAllNonNull(builder.name, builder.startDate, builder.endDate);
        } catch (NullPointerException e) {
            throw new CompulsoryFieldEmptyException();
        }
        this.name = builder.name;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.destination = builder.destination;
        this.totalBudget = builder.totalBudget;
        this.dayList = builder.dayList;
        this.tripId = new TripId();

    }


    //Compulsory field getters
    public Name getName() {
        return name;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public TripId getTripId() {
        return tripId;
    }

    public Location getDestination() {
        return destination;
    }

    public DayList getDayList() {
        return dayList;
    }

    public Expenditure getBudget() {
        return totalBudget;
    }

    /**
     * Returns true if both {@link Trip} contain the same booking and their to and from time are the same.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameTrip(Trip otherTrip) {
        if (otherTrip == this) {
            return true;
        } else {
            return otherTrip.getName().equals(getName())
                    && otherTrip.getStartDate().equals(getStartDate())
                    && otherTrip.getEndDate().equals(getEndDate())
                    && otherTrip.getDestination().equals(getDestination());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Trip)) {
            return false;
        }

        Trip otherTrip = (Trip) other;
        return otherTrip.getName().equals(getName())
                && otherTrip.getStartDate().equals(getStartDate())
                && otherTrip.getEndDate().equals(getEndDate())
                && otherTrip.getDestination().equals(getDestination())
                && otherTrip.getDayList().equals(getDayList());
    }

    public boolean isClashingWith(Trip other){
        return (this.getStartDate().compareTo(other.getStartDate()) >= 0
                && this.getStartDate().compareTo(other.getEndDate()) <= 0)
                || (this.getEndDate().compareTo(other.getStartDate()) >= 0
                        && this.getEndDate().compareTo(other.getEndDate()) <= 0);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ")
                .append(name.toString())
                .append(" From: ")
                .append(ParserDateUtil.getDisplayTime(startDate))
                .append(" To: ")
                .append(ParserDateUtil.getDisplayTime(endDate))
                .append(" Destination: ")
                .append(destination.toString())
                .append(" Total Budget: ")
                .append(totalBudget.toString());

        return builder.toString();
    }

    /**
     * Builder class to accommodate optional properties using builder pattern.
     * Can be used to construct {@link Trip} without optional fields.
     */
    public static class Builder {
        private Name name;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private Location destination;
        private Expenditure totalBudget;
        private DayList dayList;

        public static Builder newInstance (){
            return new Builder();
        }

        public static Builder of (Trip trip){
            requireAllNonNull(trip.name, trip.startDate, trip.endDate, trip.destination);
            return Builder.newInstance()
                    .setName(trip.getName())
                    .setStartDate(trip.startDate)
                    .setEndDate(trip.endDate)
                    .setLocation(trip.destination)
                    .setTotalBudget(trip.totalBudget)
                    .setDayList(trip.dayList);
        }

        private Builder(){
            dayList = new DayList();
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

        public Builder setLocation (Location location) {
            this.destination = location;
            return this;
        }

        public Builder setTotalBudget (Expenditure totalBudget){
            this.totalBudget = totalBudget;
            return this;
        }

        public Builder setDayList (DayList dayList) {
            this.dayList = dayList;
            return this;
        }

        /**
         * Terminal method to construct new {@link Trip}.
         */
        public Trip build(){
            return new Trip(this);
        }

    }
}
