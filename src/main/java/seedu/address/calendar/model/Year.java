package seedu.address.calendar.model;

public class Year {
    private int year;

    Year(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return String.format("%04d", year);
    }
}
