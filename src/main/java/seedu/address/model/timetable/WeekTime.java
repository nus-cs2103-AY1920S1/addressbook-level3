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

    public int getHourDifference(WeekTime weekTime) {
        return (this.getDay().getValue() - weekTime.getDay().getValue()) * 24 + this.getTime().getHour() - weekTime.getTime().getHour();
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
}
