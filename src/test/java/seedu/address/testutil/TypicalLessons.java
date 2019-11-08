package seedu.address.testutil;

import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.UniqueLessonList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TypicalLessons {

    public static final Lesson lessonOne = new LessonBuilder()
            .withClassName("4C3 Math")
            .withStartTime("16/12/2019 1800").withEndTime("16/12/2019 1900").build();

    public static final Lesson lessonTwo = new LessonBuilder()
            .withClassName("2C5 English")
            .withStartTime("17/12/2019 1200").withEndTime("20/12/2019 1400").build();

    public static final Lesson lessonThree = new LessonBuilder()
            .withClassName("3E Science")
            .withStartTime("18/12/2019 0800").withEndTime("18/12/2019 1000").build();


    private static void makeLessonList(List<UniqueLessonList> lessonList) {
        for (int i = 0; i < 7; i++) {
            lessonList.add(new UniqueLessonList());
        }
        lessonList.get(0).add(lessonOne);
        lessonList.get(1).add(lessonTwo);
        lessonList.get(2).add(lessonThree);
    }

    private TypicalLessons() {
    }

    public static List<UniqueLessonList> getTypicalLessons() {
        List<UniqueLessonList> lessonList = new ArrayList<>();
        makeLessonList(lessonList);
        return new ArrayList<>(Arrays.asList(lessonList.get(0), lessonList.get(1), lessonList.get(2),
                lessonList.get(3), lessonList.get(4), lessonList.get(5), lessonList.get(6)));
    }

}
