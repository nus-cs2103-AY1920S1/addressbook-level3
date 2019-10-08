package seedu.address.model.module;

import java.util.ArrayList;
import java.util.List;

/**
 * Weeks of the Lesson.
 */
public class Weeks {
    private List<Integer> weekNumbers = new ArrayList<>();
    private String startDateString;
    private String endDateString;
    private int weekInterval;
    private int type; //TODO: convert to enum

    public Weeks(List<Integer> weekNumbers, String startDateString,
                 String endDateString, int weekInterval, int type) {
        this.weekNumbers = weekNumbers;
        this.startDateString = startDateString;
        this.endDateString = endDateString;
        this.weekInterval = weekInterval;
        this.type = type;
    }

    public Weeks() {
        this.startDateString = "";
        this.endDateString = "";
        this.weekInterval = -1;
        this.type = -1;
    }

    /**
     * Static method to get an empty remark.
     */
    public static Weeks emptyWeeks() {
        return new Weeks();
    }

    public List<Integer> getWeekNumbers() {
        return weekNumbers;
    }

    public void setWeekNumbers(List<Integer> weekNumbers) {
        this.weekNumbers = weekNumbers;
    }

    public String getStartDateString() {
        return startDateString;
    }

    public void setStartDateString(String startDateString) {
        this.startDateString = startDateString;
    }

    public String getEndDateString() {
        return endDateString;
    }

    public void setEndDateString(String endDateString) {
        this.endDateString = endDateString;
    }

    public int getWeekInterval() {
        return weekInterval;
    }

    public void setWeekInterval(int weekInterval) {
        this.weekInterval = weekInterval;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        String output = "";
        switch(type) {
        case(1): output = "Week Numbers: " + weekNumbers.toString();
                break;
        case(2): output = "Start Date: " + startDateString + " End Date: "
                + endDateString + " Week Numbers: " + weekNumbers.toString();
                break;
        case(3): output = "Start Date: " + startDateString + " End Date: "
                + endDateString + " Week Interval: " + weekInterval;
                break;
        default: assert false;
        }
        return output;
    }
}
