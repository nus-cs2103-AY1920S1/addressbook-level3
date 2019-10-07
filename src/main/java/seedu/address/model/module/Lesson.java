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
        if (obj.containsKey("classNo")) {
            this.lessonNo = new LessonNo(obj.get("classNo").toString());
        } else {
            this.lessonNo = new LessonNo("");
        }

        if (obj.containsKey("startTime")) {
            this.startTime = new StartTime(obj.get("startTime").toString());
        } else {
            this.startTime = new StartTime("");
        }

        if (obj.containsKey("endTime")) {
            this.endTime = new EndTime(obj.get("endTime").toString());
        } else {
            this.endTime = new EndTime("");
        }

        if (obj.containsKey("weeks")) {
            this.weeks = new Weeks(obj.get("weeks"));
        } else {
            this.weeks = new Weeks("");
        }

        if (obj.containsKey("lessonType")) {
            this.lessonType = new LessonType(obj.get("lessonType").toString());
        } else {
            this.lessonType = new LessonType("");
        }

        if (obj.containsKey("day")) {
            this.day = new Day(obj.get("day").toString());
        } else {
            this.day = new Day("");
        }

        if (obj.containsKey("venue")) {
            this.venue = new Venue(obj.get("venue").toString());
        } else {
            this.venue = new Venue("");
        }
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
