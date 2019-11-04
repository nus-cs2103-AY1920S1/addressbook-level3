package seedu.address.testutil.moduleutil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.module.Weeks;
import seedu.address.model.module.WeeksType;

/**
 * Typical Weeks for a Lesson.
 */
public class TypicalWeeks {
    public static final LocalDate START_DATE_DEFAULT = LocalDate.MIN;
    public static final LocalDate END_DATE_DEFAULT = LocalDate.MAX;
    public static final LocalDate START_DATE_1 = LocalDate.parse("2019-08-12");
    public static final LocalDate END_DATE_1 = LocalDate.parse("2019-10-28");

    public static final List<Integer> WEEK_NUMBERS_ALL = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
    public static final List<Integer> WEEK_NUMBERS_SOME = Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10);
    public static final List<Integer> WEEK_NUMBERS_EVEN = Arrays.asList(4, 6, 8, 10, 12);
    public static final List<Integer> WEEK_NUMBERS_ODD = Arrays.asList(3, 5, 7, 9, 11, 13);
    public static final List<Integer> WEEK_NUMBERS_EMPTY = new ArrayList<>();

    /**
     * Generate a Typical Lesson of WeeksType.WEEK_NUMBERS with all weeks.
     */
    public static Weeks generateWeeks_weekNumbers_allWeeks() {
        return new Weeks(WEEK_NUMBERS_ALL, START_DATE_DEFAULT, END_DATE_DEFAULT, -1, WeeksType.WEEK_NUMBERS);
    }

    /**
     * Generate a Typical Lesson of WeeksType.WEEK_NUMBERS with weeks 3 to 10 only.
     */
    public static Weeks generateWeeks_weekNumbers_someWeeks() {
        return new Weeks(WEEK_NUMBERS_SOME, START_DATE_DEFAULT, END_DATE_DEFAULT, -1, WeeksType.WEEK_NUMBERS);
    }

    /**
     * Generate a Typical Lesson of WeeksType.WEEK_NUMBERS with even weeks.
     */
    public static Weeks generateWeeks_weekNumbers_evenWeeks() {
        return new Weeks(WEEK_NUMBERS_EVEN, START_DATE_DEFAULT, END_DATE_DEFAULT, -1, WeeksType.WEEK_NUMBERS);
    }

    /**
     * Generate a Typical Lesson of WeeksType.WEEK_NUMBERS with odd weeks.
     */
    public static Weeks generateWeeks_weekNumbers_oddWeeks() {
        return new Weeks(WEEK_NUMBERS_ODD, START_DATE_1, END_DATE_1, -1, WeeksType.WEEK_NUMBERS);
    }

    /**
     * Generate a Typical Lesson of WeeksType.START_END_WEEK_NUMBERS with all weeks,
     * start date 2019-08-12 and end date 2019-10-28.
     */
    public static Weeks generateWeeks_startEndWeekNumbers_allWeeks() {
        return new Weeks(WEEK_NUMBERS_ALL, START_DATE_1, END_DATE_1, -1, WeeksType.START_END_WEEK_NUMBERS);
    }

    /**
     * Generate a Typical Lesson of WeeksType.START_END_WEEK_NUMBERS with all weeks
     * but with start date later than end date.
     */
    public static Weeks generateWeeks_startEndWeekNumbers_invalidStartEnd() {
        return new Weeks(WEEK_NUMBERS_ALL, END_DATE_1, START_DATE_1, -1, WeeksType.START_END_WEEK_NUMBERS);
    }

    /**
     * Generate a Typical Lesson of WeeksType.START_END_WEEK_NUMBERS with odd weeks,
     * start date 2019-08-12 and end date 2019-10-28.
     */
    public static Weeks generateWeeks_startEndWeekNumbers_oddWeeks() {
        return new Weeks(WEEK_NUMBERS_ODD, START_DATE_1, END_DATE_1, -1, WeeksType.START_END_WEEK_NUMBERS);
    }

    /**
     * Generate a Typical Lesson of WeeksType.START_END_WEEK_INTERVAL with specified week interval,
     * start date 2019-08-12 and end date 2019-10-28.
     * @param interval week interval.
     */
    public static Weeks generateWeeks_startEndWeekInterval(int interval) {
        return new Weeks(WEEK_NUMBERS_EMPTY, START_DATE_1, END_DATE_1, interval, WeeksType.START_END_WEEK_INTERVAL);
    }

    /**
     * Generate a Typical Lesson of WeeksType.START_END_WEEK_INTERVAL with week interval of 2,
     * start date 2019-08-12 and end date 2019-10-28.
     */
    public static Weeks generateWeeks_startEndWeekInterval_invalidStartEndDate() {
        return new Weeks(WEEK_NUMBERS_EMPTY, END_DATE_1, START_DATE_1, 1, WeeksType.START_END_WEEK_INTERVAL);
    }
}
