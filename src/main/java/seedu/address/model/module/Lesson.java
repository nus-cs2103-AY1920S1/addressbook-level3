package seedu.address.model.module;

import org.json.simple.JSONObject;

/**
 * A lesson in a semester timetable.
 */
public class Lesson {
    private String lessonNo;
    private String startTime;
    private String endTime;
    private String weeks;
    private String lessonType;
    private String day;
    private Venue venue;

    public Lesson(JSONObject obj) {
        this.lessonNo = obj.get("classNo").toString();
        this.startTime = obj.get("startTime").toString();
        this.endTime = obj.get("endTime").toString();
        this.weeks = obj.get("weeks").toString();
        this.lessonType = obj.get("lessonType").toString();
        this.day = obj.get("day").toString();
        this.venue = new Venue(obj.get("venue").toString());
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

    public String getLessonNo() {
        return lessonNo;
    }
}
