package seedu.address.model.waste;

import java.time.LocalDate;

public class WasteMonth implements Comparable<WasteMonth> {

    public static final String MESSAGE_CONSTRAINTS =
            "Month of year should follow the format MM/yyyy, MM-yyyy or MM.yyyy, and it should not be blank";

    /*
     * Regex that works on all valid date.
     */
    public static final String VALIDATION_REGEX = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)"
            + "(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)"
            + "0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])"
            + "|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])"
            + "(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";

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
