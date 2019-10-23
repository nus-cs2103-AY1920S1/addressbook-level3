package seedu.address.calendar.model;

public class Year {
    private int year;

    public Year(int year) {
        this.year = year;
    }

    int getNumericalValue() {
        return year;
    }

    @Override
    public String toString() {
        return String.format("%04d", year);
    }
}
