package seedu.address.calendar.model;

import java.util.Optional;
import java.util.stream.Stream;

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

    static DayOfWeek of(int numericalVal) {
        Optional<DayOfWeek> desiredDay = Stream.of(DayOfWeek.values())
                .filter(d -> d.numericalVal == numericalVal)
                .findAny();

        if (desiredDay.isEmpty()) {
            assert true : "All days should be represented by some value between 0 and 6 inclusive";
        }

        return desiredDay.get();
    }
}