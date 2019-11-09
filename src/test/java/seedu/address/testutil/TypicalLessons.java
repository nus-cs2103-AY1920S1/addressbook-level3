package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Notebook;
import seedu.address.model.lesson.Lesson;
/**
 * A utility class containing a list of {@code Lesson} objects to be used in tests.
 */
public class TypicalLessons {

    public static final Lesson MON = new LessonBuilder().withClassName("Monday Class").withStartTime("06/01/2020 1200")
            .withEndTime("06/01/2020 1300").build();

    public static final Lesson ANOTHER_MON = new LessonBuilder().withClassName("Second Mon Class")
            .withStartTime("06/01/2020 1400").withEndTime("06/01/2020 1500").build();

    public static final Lesson TUE = new LessonBuilder().withClassName("Tuesday Class").withStartTime("07/01/2020 1200")
            .withEndTime("07/01/2020 1300").build();

    public static final Lesson WED = new LessonBuilder().withClassName("Wednesday Class")
            .withStartTime("08/01/2020 1200").withEndTime("08/01/2020 1300").build();

    public static final Lesson THUR = new LessonBuilder().withClassName("Thursday Class")
            .withStartTime("09/01/2020 1200").withEndTime("09/01/2020 1300").build();

    public static final Lesson FRI = new LessonBuilder().withClassName("Friday Class").withStartTime("10/01/2020 1200")
            .withEndTime("10/01/2020 1300").build();

    private TypicalLessons() {}

    public static List<Lesson> getTypicalLessons() {
        return new ArrayList<>(Arrays.asList(MON, TUE, WED, THUR, FRI));
    }

    /**
     * Returns an {@code Notebook} with all the typical classrooms.
     */
    public static Notebook getTypicalNotebook() {
        Notebook notebook = new Notebook();
        for (Lesson lesson : getTypicalLessons()) {
            notebook.addLesson(lesson);
        }
        return notebook;
    }
}
