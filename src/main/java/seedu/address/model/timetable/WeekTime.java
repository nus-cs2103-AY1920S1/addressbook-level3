package seedu.address.model.timetable;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

public class WeekTime implements Comparable<WeekTime> {
    private final DayOfWeek day;
    private final LocalTime time;

    public WeekTime(DayOfWeek day, LocalTime time) {
        this.day = day;
        this.time = time;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WeekTime weekTime = (WeekTime) o;
        return day == weekTime.day
                && time.equals(weekTime.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, time);
    }

    @Override
    public int compareTo(WeekTime weekTime) {
        if (this.day != weekTime.day) {
            return this.day.getValue() - weekTime.day.getValue();
        }
        return this.time.compareTo(weekTime.time);
    }

    public boolean isAfter(WeekTime weekTime) {
        return this.compareTo(weekTime) > 0;
    }

    public boolean isBefore(WeekTime weekTime) {
        return this.compareTo(weekTime) < 0;
    }

    @Override
    public String toString() {
        return this.day.toString() + " " + this.time.toString();
    }

    public Duration minus(WeekTime other) {
        int diffInMinutes = this.getDay().getValue() * (24 * 60) + this.getTime().getHour() * 60 + this.getTime().getMinute()
                - other.getDay().getValue() * (24 * 60) - other.getTime().getHour() * 60 - other.getTime().getMinute();
        int days = diffInMinutes / (24 * 60);
        diffInMinutes = diffInMinutes % (24 * 60);
        int hours = diffInMinutes / 60;
        int minutes = diffInMinutes % 60;

        return new Duration(days, hours, minutes);
    }
}
