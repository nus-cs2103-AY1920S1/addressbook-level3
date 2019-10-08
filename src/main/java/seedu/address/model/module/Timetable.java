package seedu.address.model.module;

import org.json.simple.JSONObject;

/**
 * The timetable of the module
 */
public class Timetable {
    private String classNo;
    private String startTime;
    private String endTime;
    private Venue venue;

    public Timetable(JSONObject obj) {
        this.classNo = obj.get("classNo").toString();
        this.startTime = obj.get("startTime").toString();
        this.endTime = obj.get("endTime").toString();
        this.venue = new Venue(obj.get("venue").toString());
    }

    @Override
    public String toString() {
        String result = "ClassNo: " + classNo + " "
                + "Start: " + startTime + " "
                + "End: " + endTime + " "
                + "Venue: " + venue.toString();

        return result;
    }

    public String getClassNo() {
        return classNo;
    }
}
