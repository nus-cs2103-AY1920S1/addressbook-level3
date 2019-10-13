package seedu.address.calendar.model;

enum DayOfWeek {
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

    int getNumericalVal() {
        return numericalVal;
    }
}