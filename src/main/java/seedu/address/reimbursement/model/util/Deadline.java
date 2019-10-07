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

    public Deadline() {
        this(-1, -1, -1);
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String toString() {
        if (year != -1) {
            return Integer.toString(year) + DASH + Integer.toString(month) + DASH + Integer.toString(day) + DASH;
        } else {
            return "";
        }

    }

    public int compareTo(Deadline anotherDeadline) {
        if (this.year > anotherDeadline.year) {
            return 1;
        } else if (this.year < anotherDeadline.year) {
            return -1;
        } else {
            if (this.month > anotherDeadline.month) {
                return 1;
            } else if (this.month < anotherDeadline.month) {
                return -1;
            } else {
                if (this.day > anotherDeadline.day) {
                    return 1;
                } else if (this.day < anotherDeadline.day) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
    }
}
