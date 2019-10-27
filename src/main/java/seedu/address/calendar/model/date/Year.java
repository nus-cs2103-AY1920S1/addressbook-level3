package seedu.address.calendar.model.date;

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

    public int compareTo(Year otherYear) {
        return year - otherYear.year;
    }

    @Override
    public String toString() {
        return String.format("%04d", year);
    }
}
