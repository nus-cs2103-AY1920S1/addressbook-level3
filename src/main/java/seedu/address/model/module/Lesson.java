package seedu.address.model.module;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Lesson in an academic semester of a module.
 */
public class Lesson {
    private LessonNo lessonNo;
    private LocalTime startTime;
    private LocalTime endTime;
    private Weeks weeks;
    private LessonType lessonType;
    private DayOfWeek day;
    private Venue venue;

    public Lesson(LessonNo lessonNo, LocalTime startTime, LocalTime endTime, Weeks weeks,
                  LessonType lessonType, DayOfWeek day, Venue venue) throws IllegalValueException {
        if (startTime.isAfter(endTime)) { //DEFENSIVE CODE - assumption: there is no lesson that goes past midnight.
            throw new IllegalValueException("Start time cannot be after end time.");
        }
        this.lessonNo = lessonNo;
        this.startTime = startTime;
        this.endTime = endTime;
        this.weeks = weeks;
        this.lessonType = lessonType;
        this.day = day;
        this.venue = venue;
    }

    @Override
    public String toString() {
        String result = "ClassNo: " + lessonNo + " "
                + "Weeks: " + weeks + " "
                + "LessonType: " + lessonType + " "
                + "Day: " + day.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " "
                + "Start: " + startTime + " "
                + "End: " + endTime + " "
                + "Venue: " + venue.toString();

        return result;
    }

    public LessonNo getLessonNo() {
        return lessonNo;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Weeks getWeeks() {
        return weeks;
    }

    public LessonType getLessonType() {
        return lessonType;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public Venue getVenue() {
        return venue;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Lesson)) {
            return false;
        }
        Lesson l = (Lesson) other;
        if (l == this) {
            return true;
        } else if (l.lessonNo.equals(this.lessonNo)
                && l.startTime.equals(this.startTime)
                && l.endTime.equals(this.endTime)
                && l.weeks.equals(this.weeks)
                && l.lessonType.equals(this.lessonType)
                && l.day.equals(this.day)
                && l.venue.equals(this.venue)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonNo, startTime, endTime, weeks, lessonType, day, venue);
    }
}
