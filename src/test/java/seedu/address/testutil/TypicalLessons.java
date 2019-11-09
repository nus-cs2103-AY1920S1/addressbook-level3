package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.UniqueLessonList;

/**
 * A utility class containing a list of {@code Lesson} objects to be used in tests.
 */
public class TypicalLessons {

    public static final Lesson LESSON_ONE = new LessonBuilder()
            .withClassName("4C3 Math")
            .withStartTime("16/12/2019 1800").withEndTime("16/12/2019 1900").build();

    public static final Lesson LESSON_TWO = new LessonBuilder()
            .withClassName("2C5 English")
            .withStartTime("17/12/2019 1200").withEndTime("20/12/2019 1400").build();

    public static final Lesson LESSON_THREE = new LessonBuilder()
            .withClassName("3E Science")
            .withStartTime("18/12/2019 0800").withEndTime("18/12/2019 1000").build();


    private TypicalLessons() {
    }

    /**
     * Makes the lesson list from the list of UniqueLessonList.
     * @param lessonList a list of UniqueLessonList.
     */
    private static void makeLessonList(List<UniqueLessonList> lessonList) {
        for (int i = 0; i < 7; i++) {
            lessonList.add(new UniqueLessonList());
        }
        lessonList.get(0).add(LESSON_ONE);
        lessonList.get(1).add(LESSON_TWO);
        lessonList.get(2).add(LESSON_THREE);
    }


    public static List<UniqueLessonList> getTypicalLessons() {
        List<UniqueLessonList> lessonList = new ArrayList<>();
        makeLessonList(lessonList);
        return new ArrayList<>(Arrays.asList(lessonList.get(0), lessonList.get(1), lessonList.get(2),
                lessonList.get(3), lessonList.get(4), lessonList.get(5), lessonList.get(6)));
    }

}
