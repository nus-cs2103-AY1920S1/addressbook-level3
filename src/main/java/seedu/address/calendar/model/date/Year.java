package seedu.address.calendar.model.date;

public class Year {
    private int year;

    public Year(int year) {
        this.year = year;
    }

    public int getNumericalValue() {
        return year;
    }

    @Override
    public String toString() {
        return String.format("%04d", year);
    }
}
