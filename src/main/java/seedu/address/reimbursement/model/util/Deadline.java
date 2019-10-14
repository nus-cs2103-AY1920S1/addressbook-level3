package seedu.address.reimbursement.model.util;

/**
 * Stores the deadline of a Reimbursement.
 */
public class Deadline {
    private static String dash = "-";
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

    /**
     * @return a string representing the deadline.
     */
    public String toString() {
        if (year != -1) {
            return Integer.toString(year) + dash + Integer.toString(month) + dash + Integer.toString(day);
        } else {
            return "";
        }

    }

    /**
     * Compares two deadlines.
     * @param anotherDeadline the deadline to compare to.
     * @return -1 if the current deadline is smaller, 0 if they are the same, 1 if otherwise.
     */
    public int compareTo(Deadline anotherDeadline) {
        if (this.year < anotherDeadline.year) {
            return 1;
        } else if (this.year > anotherDeadline.year) {
            return -1;
        } else {
            if (this.month < anotherDeadline.month) {
                return 1;
            } else if (this.month > anotherDeadline.month) {
                return -1;
            } else {
                if (this.day < anotherDeadline.day) {
                    return 1;
                } else if (this.day > anotherDeadline.day) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
    }
}
