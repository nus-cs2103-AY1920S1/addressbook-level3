package seedu.address.testutil.moduleutil;

import java.time.DayOfWeek;
import java.time.LocalTime;

import seedu.address.model.module.Lesson;
import seedu.address.model.module.LessonNo;
import seedu.address.model.module.LessonType;
import seedu.address.model.module.Venue;
import seedu.address.model.module.Weeks;

/**
 * Typical Lessons.
 */
public class TypicalLessons {
    private static final LessonNo LESSON_NO_1 = new LessonNo("D01");
    private static final Venue VENUE_1 = new Venue("COM2");
    private static final LocalTime START_TIME_1 = LocalTime.parse("1000");
    private static final LocalTime END_TIME_1 = LocalTime.parse("1200");
    private static final Weeks WEEKS_1 = TypicalWeeks.generateWeeks_weekNumbers_allWeeks();

    /**
     * Generate a Typical Lesson.
     *
     * @return Lesson 1
     */
    public static Lesson generateValidLesson() {
        return new Lesson(LESSON_NO_1, START_TIME_1, END_TIME_1, WEEKS_1,
                LessonType.TUTORIAL, DayOfWeek.TUESDAY, VENUE_1);
    }
}
