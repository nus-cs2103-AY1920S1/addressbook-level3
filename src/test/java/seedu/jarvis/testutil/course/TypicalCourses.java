package seedu.jarvis.testutil.course;

import seedu.jarvis.commons.util.CourseUtil;
import seedu.jarvis.model.course.Course;

public class TypicalCourses {
    // private constructor to prevent instantiation
    private TypicalCourses() {}

    public static final Course CS3230 = CourseUtil.getCourse("CS3230");
    public static final Course MA1521 = CourseUtil.getCourse("MA1521");
    public static final Course CS1010 = CourseUtil.getCourse("CS1010");
}
