package seedu.address.model.person.schedule;

import java.time.LocalDateTime;

public class Timeslot {
    private String startTime;
    private String endTime;
    private String venue;

    public Timeslot(String startTime, String endTime, String venue){
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
    }

    public String toString(){
        String output = "";

        output += startTime + " - " + endTime + " at " + venue + " === ";

        return output;
    }
}
