package seedu.address.model.Lesson.Exceptions;

public class DuplicateLessonException extends RuntimeException {
    public DuplicateLessonException() {
        super("Operation would result in duplicate lesson");
    }
}
