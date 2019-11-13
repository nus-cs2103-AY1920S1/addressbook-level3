package seedu.address.model.lesson;

import java.util.Comparator;

//@@author weikiat97
/**
 * Sorts the lesson list by chronological start time.
 */
public class LessonComparator implements Comparator<Lesson> {

    @Override
    public int compare(Lesson lesson1, Lesson lesson2) {
        if (lesson1.getStartTime().getTime()
                .before(lesson2.getStartTime().getTime())) {
            return -1;
        } else {
            return 1;
        }
    }

}
