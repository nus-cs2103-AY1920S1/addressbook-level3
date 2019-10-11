package seedu.address.model.trip;

import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.model.itinerary.Expenditure;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.day.DayList;
import seedu.address.model.trip.exceptions.CompulsoryFieldEmptyException;

import java.time.LocalDateTime;

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
            return otherTrip != null
                    && otherTrip.getName().equals(getName())
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

}
