package seedu.address.calendar.model.date;

/**
 * Represents the day of the month, i.e. Sunday, Monday, ... For easy mathematical manipulations,
 * each day of month has an associated numerical value.
 */
public enum DayOfWeek {
    /**
     * Represents Sunday, with associated numerical value 0.
     */
    SUN(0),
    /**
     * Represents Monday, with associated numerical value 1.
     */
    MON(1),
    /**
     * Represents Tuesday, with associated numerical value 2.
     */
    TUE(2),
    /**
     * Represents Wednesday, with associated numerical value 3.
     */
    WED(3),
    /**
     * Represents Thursday, with associated numerical value 4.
     */
    THU(4),
    /**
     * Represents Friday, with associated numerical value 5.
     */
    FRI(5),
    /**
     * Represents Saturday, with associated numerical value 6.
     */
    SAT(6);

    private int numericalVal;

    /**
     * Returns an instance of {@code DayOfWeek}.
     *
     * @param numericalVal The associated numerical value of {@code this}.
     */
    DayOfWeek(int numericalVal) {
        this.numericalVal = numericalVal;
    }

    /**
     * Gets the numerical value associated with {@code this}.
     *
     * @return numerical value associated with {@code this}
     */
    public int getNumericalVal() {
        return numericalVal;
    }

    /**
     * Returns a capitalised string representation of {@code this} such that it is represented with the first three
     * letter of the day of month.
     *
     * @return Capitalised string representation of {@code this} such that it is represented with the first three
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
