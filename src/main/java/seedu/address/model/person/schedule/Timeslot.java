package seedu.address.model.person.schedule;

/**
 * Timeslot of an Event.
 */
public class Timeslot {
    private String startTime;
    private String endTime;
    private String venue;

    public Timeslot(String startTime, String endTime, String venue) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
    }

    /**
     * Converts to String.
     * @return String
     */
    public String toString() {
        String output = "";
        output += startTime + " - " + endTime + " at " + venue + " ";
        return output;
    }
}
