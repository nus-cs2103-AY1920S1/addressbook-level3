package seedu.address.model.date;

/**
 * Represents date used in attendance and performance recording.
 */
public class AthletickDate {

    public static final String MESSAGE_CONSTRAINTS = "Please specify date in either MMYYYY or "
            + "DDMMYYYY format.";
    public static final String WRONG_DATE_FORMAT = "Invalid date specified.";

    private int day;
    private int month;
    private int year;
    private int type;
    private String mth;

    public AthletickDate(int day, int month, int year, int type, String mth) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.type = type;
        this.mth = mth;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getType() {
        return type;
    }

    public String getMth() {
        return mth;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AthletickDate
                && day == ((AthletickDate) other).day
                && month == ((AthletickDate) other).month
                && year == ((AthletickDate) other).year);
    }
}
