package seedu.address.calendar.model.date;

import java.util.Arrays;

public class Year implements Comparable<Year> {
    private int year;

    public Year(int year) {
        this.year = year;
    }

    public int getNumericalValue() {
        return year;
    }

    Year copy() {
        return new Year(year);
    }

    @Override
    public int compareTo(Year otherYear) {
        return year - otherYear.year;
    }

    @Override
    public String toString() {
        return String.format("%04d", year);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof Year)) {
            return false;
        }

        Year otherYear = (Year) obj;
        boolean isSameYear = this.year == otherYear.year;
        return isSameYear;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new int[]{year});
    }
}
