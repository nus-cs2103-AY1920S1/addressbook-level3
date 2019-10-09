package seedu.address.model.module;

/**
 * Represents a Lesson in an academic semester of a module.
 */
public class Lesson {
    private LessonNo lessonNo;
    private StartTime startTime;
    private EndTime endTime;
    private Weeks weeks;
    private LessonType lessonType;
    private Day day;
    private Venue venue;

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
