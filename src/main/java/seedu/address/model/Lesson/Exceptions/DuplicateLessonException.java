package seedu.address.model.Lesson.Exceptions;

/**
 * Signals that the operation will result in duplicate Lessons (Lessons are considered duplicates if they have the
 * same identity).
 */
public class DuplicateLessonException extends RuntimeException {
    public DuplicateLessonException() {
        super("Operation would result in duplicate lesson");
    }
}
