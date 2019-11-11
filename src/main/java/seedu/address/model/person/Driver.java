package seedu.address.model.person;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.EventTime;
import seedu.address.model.person.exceptions.RatingException;
import seedu.address.model.person.exceptions.SchedulingException;
import seedu.address.model.tag.Tag;

/**
 * Represents a Driver available for work.
 * Gurantees: details are present and not null, field values are validated, id is immutable.
 */
public class Driver extends Person {

    public static final String MESSAGE_INVALID_ID = "Invalid driver ID.";
    public static final String MESSAGE_NOT_AVAILABLE = "Driver(ID: %1$s) is not available";

    //Identity fields
    private final int id;

    //data fields
    private Schedule schedule;
    private DriverRating driverRating;

    /**
     * Every field must be present and not null.
     */
    public Driver(int id, Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(name, phone, email, address, tags);
        schedule = new Schedule();
        this.id = id;
        this.driverRating = new DriverRating();
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public int getId() {
        return id;
    }

    public int getRating() {
        return this.driverRating.getRating();
    }

    public int getTotalNoOfReviews() {
        return this.driverRating.getTotalNoOfReviews();
    }

    /**
     * {@see DriverRating#addRating}
     */
    public void addRating(int rating) {
        if (!driverRating.isValid(rating)) {
            throw new RatingException(driverRating.RATING_OUT_OF_RANGE);
        }
        driverRating.addRating(rating);
    }

    public void setRating(int rating, int totalNoOfReviews) {
        driverRating.setRating(rating, totalNoOfReviews);
    }

    /**
     * {@see Schedule#remove}
     */
    public boolean deleteFromSchedule(EventTime durationToRemove) {
        return schedule.remove(durationToRemove);
    }

    public void addToSchedule(EventTime durationToAdd) throws SchedulingException {
        schedule.add(durationToAdd);
    }

    public boolean isScheduleAvailable(EventTime durationToAdd) {
        return schedule.isAvailable(durationToAdd);
    }

    public SchedulingSuggestion suggestTime(EventTime eventTime, LocalTime timeNow) {
        return this.schedule.getSchedulingSuggestion(eventTime, timeNow);
    }

    public Optional<EventTime> suggestTime(Duration duration, LocalTime timeNow) {
        return this.schedule.findFirstAvailableSlot(timeNow, duration);
    }

    public void assign(EventTime eventTime) throws SchedulingException {
        this.schedule.add(eventTime);
    }

    /**
     * Returns true if both drivers of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two drivers.
     */
    public boolean isSameDriver(Driver otherDriver) {
        if (otherDriver == this) {
            return true;
        }

        return otherDriver != null
                && otherDriver.getName().equals(getName())
                && (otherDriver.getPhone().equals(getPhone()) || otherDriver.getEmail().equals(getEmail()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Driver)) {
            return false;
        }

        Driver otherDriver = (Driver) other;
        return otherDriver.getId() == getId()
                && otherDriver.getName().equals(getName())
                && otherDriver.getPhone().equals(getPhone())
                && otherDriver.getEmail().equals(getEmail())
                && otherDriver.getAddress().equals(getAddress())
                && otherDriver.getSchedule().equals(getSchedule())
                && otherDriver.getTags().equals(getTags());
    }

    /**
     * Returns a string representation of the driver, with identity fields visible to the user.
     *
     * @return string representation of driver
     */
    @Override
    public String toString() {
        StringBuilder driverBuilder = new StringBuilder();
        driverBuilder.append(" Driver stats: \n")
                .append(" id: ")
                .append(getId())
                .append(" rating: ")
                .append(getRating())
                .append(" ")
                .append(super.toString());
        return driverBuilder.toString();
    }

}
