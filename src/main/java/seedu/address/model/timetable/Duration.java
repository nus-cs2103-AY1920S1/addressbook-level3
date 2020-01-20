package seedu.address.model.timetable;

/**
 * A class to store time duration in number of days and hours and minutes
 */
public class Duration implements Comparable<Duration> {
    private final int days;
    private final int hours;
    private final int minutes;

    public Duration(int days, int hours, int minutes) {
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
    }

    @Override
    public int compareTo(Duration duration) {
        if (this.days != duration.days) {
            return this.days - duration.days;
        } else if (this.hours != duration.hours) {
            return this.hours - duration.hours;
        } else {
            return this.minutes - duration.minutes;
        }
    }

    public int getDays() {
        return days;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Duration duration = (Duration) o;
        return days == duration.days
                && hours == duration.hours
                && minutes == duration.minutes;
    }

    @Override
    public String toString() {
        return String.format("Days: %d Hours: %d Minutes: %d", days, hours, minutes);
    }
}
