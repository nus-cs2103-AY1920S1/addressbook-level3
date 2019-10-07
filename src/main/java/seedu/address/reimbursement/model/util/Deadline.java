package seedu.address.reimbursement.model.util;

public class Deadline {
    private static String DASH = "-";
    private int year;
    private int month;
    private int day;

    public Deadline(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String toString() {
        return Integer.toString(year) + DASH + Integer.toString(month) + DASH + Integer.toString(day) + DASH;
    }
}
