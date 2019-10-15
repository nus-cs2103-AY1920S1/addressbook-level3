package seedu.address.model.waste;

import java.time.LocalDate;

public class WasteMonth implements Comparable<WasteMonth> {

    public static final String MESSAGE_CONSTRAINTS =
            "Month of year can be in any format permissible by the Natty library.";

    private int month;
    private int year;

    public WasteMonth(int month, int year) {
        this.month = month;
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public boolean isAfter(WasteMonth otherWasteMonth) {
        return this.compareTo(otherWasteMonth) > 0;
    }

    public boolean isBefore(WasteMonth otherWasteMonth) {
        return this.compareTo(otherWasteMonth) < 0;
    }

    @Override
    public int compareTo(WasteMonth otherMonth) {
        LocalDate otherDate = LocalDate.of(otherMonth.getYear(), otherMonth.getMonth(), 1);
        LocalDate thisDate = LocalDate.of(this.getYear(), this.getMonth(), 1);
        return thisDate.compareTo(otherDate);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof WasteMonth)) {
            return false;
        }
        boolean isSameMonth = ((WasteMonth) other).getMonth() == this.getMonth();
        boolean isSameYear = ((WasteMonth) other).getYear() == this.getYear();
        return isSameMonth && isSameYear;
    }
}
