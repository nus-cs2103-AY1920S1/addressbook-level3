package seedu.address.commons.util;

/**
 * {@Code MonthData} used for generating Statistics.
 */
public class MonthData implements Comparable<MonthData> {
    private int key;
    private int month;
    private int year;

    public MonthData(int key, int month, int year) {
        this.key = key;
        this.month = month;
        this.year = year;
    }

    public MonthData(int key, String date) {
        this.key = key;
        this.month = Integer.parseInt(date.substring(0, 2));
        this.year = Integer.parseInt(date.substring(3));
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return String.format("%02d-%04d", month, year);
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @Override
    public int compareTo(MonthData o) {
        if (this.isSameYear(o)) {
            return Integer.compare(getMonth(), o.getMonth());
        } else {
            return getYear() - o.getYear();
        }
    }

    private boolean isSameYear(MonthData o) {
        return getYear() == o.getYear();
    }

    @Override
    public String toString() {
        return getValue();
    }
}
