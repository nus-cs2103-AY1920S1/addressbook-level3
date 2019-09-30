package seedu.address.model.itinerary.trip;

import seedu.address.model.itinerary.Date;
import seedu.address.model.itinerary.Expenditure;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.day.DayList;

import java.util.UUID;

public class Trip {
    private final Name name;
    private final Date from;
    private final Date to;
    private final TripId tripId;
    private final Location destintaion;
    private final Expenditure totalBudget;
    private final DayList dayList;

    public Trip(Name name, Date from, Date to, Location destintaion, Expenditure totalBudget, DayList dayList) {
        this.name = name;
        this.from = from;
        this.to = to;
        this.destintaion = destintaion;
        this.totalBudget = totalBudget;
        this.dayList = dayList;
        this.tripId = new TripId();
    }

    public Name getName() {
        return name;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }

    public TripId getTripId() {
        return tripId;
    }

    public Location getDestintaion() {
        return destintaion;
    }

    public Expenditure getTotalBudget() {
        return totalBudget;
    }

    public DayList getDayList() {
        return dayList;
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
                    && otherTrip.getFrom().equals(getFrom())
                    && otherTrip.getTo().equals(getTo())
                    && otherTrip.getDestintaion().equals(getDestintaion());
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
                && otherTrip.getFrom().equals(getFrom())
                && otherTrip.getTo().equals(getTo())
                && otherTrip.getDestintaion().equals(getDestintaion())
                && otherTrip.getDayList().equals(getDayList());
    }

    public boolean isClashingWith(Trip other){
        return (this.getFrom().compareTo(other.getTo()) == -1 && this.getTo().compareTo(other.getFrom()) == 1)
                || (this.getTo().compareTo(other.getFrom()) == -1 && this.getFrom().compareTo(other.getTo()) == 1);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Name: ")
                .append(name.toString())
                .append(" From: ")
                .append(from.toString())
                .append(" To: ")
                .append(to.toString())
                .append(" Destination: ")
                .append(destintaion.toString())
                .append(" Total Budget: ")
                .append(totalBudget.toString());

        return builder.toString();
    }
}
