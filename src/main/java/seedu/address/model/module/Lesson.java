package seedu.address.model.module;

import org.json.simple.JSONObject;

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
}
