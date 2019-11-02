package seedu.jarvis.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.jarvis.commons.core.tag.Tag;
import seedu.jarvis.commons.util.CourseUtil;
import seedu.jarvis.logic.commands.course.ShowCourseHelpCommand;
import seedu.jarvis.model.course.Course;
import seedu.jarvis.model.course.CoursePlanner;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Course[] getSampleCourses() {
        return new Course[] {
            CourseUtil.getCourse("CG1111").get(),
            CourseUtil.getCourse("CS1010").get(),
            CourseUtil.getCourse("CS1231").get(),
            CourseUtil.getCourse("MA1511").get(),
            CourseUtil.getCourse("MA1512").get(),
            CourseUtil.getCourse("GER1000").get()
        };
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    public static CoursePlanner getSampleCoursePlanner() {
        CoursePlanner cp = new CoursePlanner();
        Arrays.stream(getSampleCourses()).forEach(cp::addCourse);
        cp.setCourseDisplayText(ShowCourseHelpCommand.MESSAGE_HELP);
        return cp;
    }
}
