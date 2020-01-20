package seedu.address.model.performanceoverview;

public class RateOfAttendance {
    private final int numOfMeetings;
    private final int numOfMeetingsAttended;

    public RateOfAttendance(int numOfMeetings, int numOfMeetingsAttended) {
        this.numOfMeetings = numOfMeetings;
        this.numOfMeetingsAttended = numOfMeetingsAttended;

    }

    public double getRate() {
        if (numOfMeetings == 0) {
            return 0;
        }
        double rate = ((double) numOfMeetingsAttended / numOfMeetings) * 100;
        return rate;
    }

    public String getRateAsString() {
        return String.format("%.1f", getRate());
    }
}
