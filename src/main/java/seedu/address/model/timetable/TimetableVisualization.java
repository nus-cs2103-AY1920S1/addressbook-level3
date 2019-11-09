package seedu.address.model.timetable;

import seedu.address.commons.exceptions.IllegalValueException;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.StringJoiner;

public class TimetableVisualization {
    private Timetable timetable;
    private static final int SPACES_BETWEEN_DAY_AND_TIMETABLE = 5;

    public TimetableVisualization(Timetable timetable) {
        this.timetable = timetable;
    }

    public String visualize() {
        StringJoiner result = new StringJoiner("\n");
        result.add(getHeader());
        result.add(getBody());
        return result.toString();
    }

    // 0 -- 23
    // 4 dash per hour
    // Should look like this
    // 0000 0100 0200 0300
    // -------------------
    private String getHeader() {
        StringJoiner sj = new StringJoiner("  ");
        StringBuilder result = new StringBuilder();
        result.append(getIndentation());

        for (int i = 0; i < 24; i++) {
            sj.add(String.format("%02d", i));
        }
        result.append(sj.toString());
        result.append("\n");

        result.append(getIndentation());
        for (int i = 0; i < 24 * 4; i++) {
            result.append("_");
        }
        return result.toString();
    }

    private String getBody() {
        StringJoiner result = new StringJoiner("\n");
        for (int i = 1; i <= 7; i++) {
            result.add(getDayString(i));
        }
        return result.toString();
    }

    private String getDayString(int dayOfWeek) {
        // Right pad with space
        DayOfWeek day = DayOfWeek.of(dayOfWeek);
        String dayString = String.format("%-" + SPACES_BETWEEN_DAY_AND_TIMETABLE + "s", day.toString().substring(0, 3));
        StringBuilder result = new StringBuilder();
        result.append(dayString);

        TimeRange dummyTimerange;

        for (int i = 0; i < 24; i++) {
            for (int j = 0; j <= 3; j++) {
                dummyTimerange = null;
                if (j != 3) {
                    try {
                        dummyTimerange = new TimeRange(day, LocalTime.parse(String.format("%02d:%02d", i, j * 15)),
                                day, LocalTime.parse(String.format("%02d:%02d", i, (j + 1) * 15)));
                    } catch (IllegalValueException e) {
                        e.printStackTrace();
                    }
                } else if (i != 23) {
                    try {
                        dummyTimerange = new TimeRange(day, LocalTime.parse(String.format("%02d:45", i)),
                                day, LocalTime.parse(String.format("%02d:00", i + 1)));
                    } catch (IllegalValueException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        dummyTimerange = new TimeRange(day, LocalTime.parse("23:45"),
                                day, LocalTime.parse("23:59"));
                    } catch (IllegalValueException e) {
                        e.printStackTrace();
                    }
                }
                result.append((dummyTimerange != null) && this.timetable.overlaps(dummyTimerange) ? "-" : " ");
            }
        }
        return result.toString();
    }

    private String getIndentation() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < SPACES_BETWEEN_DAY_AND_TIMETABLE; i++) {
            result.append(" ");
        }
        return result.toString();
    }
}
