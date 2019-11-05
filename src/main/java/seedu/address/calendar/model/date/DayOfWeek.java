package seedu.address.calendar.model.date;

/**
 * Represents the day of the month, i.e. Sunday, Monday, ... For easy mathematical manipulations,
 * each day of month has an associated {@code int} value.
 */
public enum DayOfWeek {
    /**
     * Represents Sunday, with associated {@code int} value 0.
     */
    SUN(0),
    /**
     * Represents Monday, with associated {@code int} value 1.
     */
    MON(1),
    /**
     * Represents Tuesday, with associated {@code int} value 2.
     */
    TUE(2),
    /**
     * Represents Wednesday, with associated {@code int} value 3.
     */
    WED(3),
    /**
     * Represents Thursday, with associated {@code int} value 4.
     */
    THU(4),
    /**
     * Represents Friday, with associated {@code int} value 5.
     */
    FRI(5),
    /**
     * Represents Saturday, with associated {@code int} value 6.
     */
    SAT(6);

    private int numericalVal;

    /**
     * Returns an instance of {@code DayOfWeek}.
     *
     * @param numericalVal the associated {@code int} value of {@code this}.
     */
    DayOfWeek(int numericalVal) {
        this.numericalVal = numericalVal;
    }

    /**
     * Gets the {@code int} value associated with {@code this}.
     *
     * @return {@code int} value associated with {@code this}
     */
    public int getNumericalVal() {
        return numericalVal;
    }

    /**
     * Returns a capitalised string representation of {@code this} such that it is represented with the first three
     * letter of the day of month.
     *
     * @return capitalised string representation of {@code this} such that it is represented with the first three
     *         letter of the day of month.
     */
    @Override
    public String toString() {
        switch(this) {
        case SUN:
            return "Sun";
        case MON:
            return "Mon";
        case TUE:
            return "Tue";
        case WED:
            return "Wed";
        case THU:
            return "Thu";
        case FRI:
            return "Fri";
        case SAT:
            return "Sat";
        default:
            assert false : "Only possible values are from Sun to Mon";
            return "";
        }
    }
}
