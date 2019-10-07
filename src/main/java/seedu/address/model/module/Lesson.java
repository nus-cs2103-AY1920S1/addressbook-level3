package seedu.address.model.module;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.simple.JSONObject;

import seedu.address.model.person.schedule.Timeslot;

/**
 * A lesson in a semester timetable.
 */
public class Lesson {
    private LessonNo lessonNo;
    private StartTime startTime;
    private EndTime endTime;
    private Weeks weeks;
    private LessonType lessonType;
    private Day day;
    private Venue venue;

    public Lesson(JSONObject obj) {
        this.lessonNo = new LessonNo(obj.get("classNo").toString());
        this.startTime = new StartTime(obj.get("startTime").toString());
        this.endTime = new EndTime(obj.get("endTime").toString());
        this.weeks = new Weeks(obj.get("weeks").toString());
        this.lessonType = new LessonType(obj.get("lessonType").toString());
        this.day = new Day(obj.get("day").toString());
        this.venue = new Venue(obj.get("venue").toString());
    }

    public Lesson(LessonNo lessonNo, StartTime startTime, EndTime endTime, Weeks weeks,
                  LessonType lessonType, Day day, Venue venue) {
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
                + "Day: " + day + " "
                + "Start: " + startTime + " "
                + "End: " + endTime + " "
                + "Venue: " + venue.toString();

        return result;
    }

    public LessonNo getLessonNo() {
        return lessonNo;
    }

    public StartTime getStartTime() {
        return startTime;
    }

    public EndTime getEndTime() {
        return endTime;
    }

    public Weeks getWeeks() {
        return weeks;
    }

    public LessonType getLessonType() {
        return lessonType;
    }

    public Day getDay() {
        return day;
    }

    public Venue getVenue() {
        return venue;
    }

    /**
     * Generate all timeslots for the lesson, taking into account of holidays.
     * @return
     */
    public List<Timeslot> generateTimeslots() {
        List<Timeslot> timeslots = new ArrayList<>();

        seedu.address.model.person.schedule.Venue venue = new
                seedu.address.model.person.schedule.Venue(this.venue.toString());

        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

        // need acad year and semester no

        LocalDateTime startDateTime = LocalDateTime.parse(this.startTime.toString(), DATE_FORMATTER);
        LocalDateTime endDateTime = LocalDateTime.parse(this.endTime.toString(), DATE_FORMATTER);

        Timeslot ts = new Timeslot(startDateTime, endDateTime, venue);
        timeslots.add(ts);

        return timeslots;
    }
}
