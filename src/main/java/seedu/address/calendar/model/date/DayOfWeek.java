package seedu.address.calendar.model.date;

public enum DayOfWeek {
    SUN(0),
    MON(1),
    TUE(2),
    WED(3),
    THU(4),
    FRI(5),
    SAT(6);

    private int numericalVal;

    DayOfWeek(int numericalVal) {
        this.numericalVal = numericalVal;
    }

    public int getNumericalVal() {
        return numericalVal;
    }

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
