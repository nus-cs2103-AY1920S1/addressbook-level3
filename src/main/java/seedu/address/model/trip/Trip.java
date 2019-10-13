package seedu.address.model.trip;

import java.time.LocalDateTime;

import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.model.diary.Diary;
import seedu.address.model.itinerary.Expenditure;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.day.DayList;

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
    private final Diary diary;

    /**
     * Constructs a trip.
     */
    public Trip(Name name, LocalDateTime startDate, LocalDateTime endDate,
                Location destination, Expenditure totalBudget, DayList dayList, Diary diary) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.destination = destination;
        this.totalBudget = totalBudget;
        this.dayList = dayList;
        this.tripId = new TripId();
        this.diary = diary;
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

    public Diary getDiary() {
        return diary;
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

    /**
     * Checks whether this trip is clashing with {@code other}.
     *
     * @param other The other trip instance to check.
     * @return Boolean of whether the trip clashes.
     */
    public boolean isClashingWith(Trip other) {
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
