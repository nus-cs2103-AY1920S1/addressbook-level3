package seedu.address.calendar.model.util;

import seedu.address.calendar.model.date.DayOfWeek;

import java.util.Optional;
import java.util.stream.Stream;

public class DayOfWeekUtil {

    static DayOfWeek of(int numericalVal) {
        Optional<DayOfWeek> desiredDay = Stream.of(DayOfWeek.values())
                .filter(d -> d.getNumericalVal() == numericalVal)
                .findAny();

        if (desiredDay.isEmpty()) {
            assert true : "All days should be represented by some value between 0 and 6 inclusive";
        }

        return desiredDay.get();
    }
}
