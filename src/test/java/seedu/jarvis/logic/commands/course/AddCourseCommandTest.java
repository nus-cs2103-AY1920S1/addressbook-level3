package seedu.jarvis.logic.commands.course;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.testutil.Assert.assertThrows;
import static seedu.jarvis.testutil.course.TypicalCourses.CS1010;
import static seedu.jarvis.testutil.course.TypicalCourses.CS3230;
import static seedu.jarvis.testutil.course.TypicalCourses.MA1521;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.course.Course;
import seedu.jarvis.testutil.ModelStub;
import seedu.jarvis.testutil.course.TypicalCourses;

public class AddCourseCommandTest {

    @BeforeEach
    public void hasInverseExecution() {
        AddCourseCommand addCourseCommand =
            new AddCourseCommand(TypicalCourses.getTypicalCourses());
        assertTrue(addCourseCommand.hasInverseExecution());
    }

    @Test
    public void constructor_nullCourse_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCourseCommand(null));
    }

    @Test
    public void execute_courseAcceptedByModel_addSuccess() throws Exception {
        ModelStubAcceptingCourse modelStubAcceptingCourse = new ModelStubAcceptingCourse();
        List<Course> courses = TypicalCourses.getTypicalCourses();

        CommandResult commandResult = new AddCourseCommand(courses)
            .execute(modelStubAcceptingCourse);

        assertEquals(String.format(AddCourseCommand.MESSAGE_SUCCESS, courses),
            commandResult.getFeedbackToUser());
        assertEquals(courses, modelStubAcceptingCourse.coursesAdded);
    }

    @Test
    public void execute_someDuplicateCourses_addsNonDuplicates() throws Exception {
        List<Course> courses = List.of(MA1521, CS3230);
        List<Course> modelCourses = new ArrayList<>(List.of(MA1521, CS1010));
        AddCourseCommand addCourseCommand = new AddCourseCommand(courses);
        ModelStubWithCourses modelStub = new ModelStubWithCourses(modelCourses);

        // should add CS3230
        CommandResult commandResult = addCourseCommand.execute(modelStub);

        assertEquals(String.format(AddCourseCommand.MESSAGE_SUCCESS, List.of(CS3230)),
            commandResult.getFeedbackToUser());
    }


    @Test
    public void execute_duplicateCourse_throwsCommandException() {
        List<Course> courses = TypicalCourses.getTypicalCourses();
        AddCourseCommand addCourseCommand = new AddCourseCommand(courses);
        ModelStubWithCourses modelStub = new ModelStubWithCourses(courses);

        assertThrows(CommandException.class,
            AddCourseCommand.MESSAGE_DUPLICATE_COURSES, () -> addCourseCommand.execute(modelStub));
    }

    @Test
    public void executeInverse_addCourseAllSuccess_removesAll() throws Exception {
        List<Course> courses = new ArrayList<>(List.of(MA1521, CS1010));
        AddCourseCommand addCourseCommand = new AddCourseCommand(courses);
        ModelStubWithCourses modelStub = new ModelStubWithCourses(new ArrayList<>());

        CommandResult commandResult = addCourseCommand.execute(modelStub);
        assertTrue(modelStub.hasCourse(MA1521) && modelStub.hasCourse(CS1010));
        addCourseCommand.executeInverse(modelStub);
        assertTrue(!modelStub.hasCourse(MA1521) && !modelStub.hasCourse(CS1010));
    }

    @Test
    public void executeInverse_addCoursePartialSuccess_removesPartial() throws Exception {
        List<Course> courses = new ArrayList<>(List.of(MA1521, CS1010));
        AddCourseCommand addCourseCommand = new AddCourseCommand(courses);
        ModelStubWithCourses modelStub = new ModelStubWithCourses(new ArrayList<>(List.of(MA1521)));

        // only add CS1010, MA1521 already in Model
        CommandResult commandResult = addCourseCommand.execute(modelStub);
        assertTrue(modelStub.hasCourse(MA1521) && modelStub.hasCourse(CS1010));

        // should only delete CS1010, as that was the only course that was added
        addCourseCommand.executeInverse(modelStub);
        assertTrue(modelStub.hasCourse(MA1521) && !modelStub.hasCourse(CS1010));
    }

    /**
     * A Model stub that contains a list of courses.
     */
    private static class ModelStubWithCourses extends ModelStub {
        private final List<Course> courses;

        ModelStubWithCourses(List<Course> courses) {
            requireNonNull(courses);
            this.courses = courses;
        }

        @Override
        public boolean hasCourse(Course course) {
            requireNonNull(course);
            return this.courses.contains(course);
        }

        @Override
        public void addCourse(Course course) {
            requireNonNull(course);
            if (!hasCourse(course)) {
                courses.add(course);
            }
        }

        @Override
        public void deleteCourse(Course course) {
            requireNonNull(course);
            if (hasCourse(course)) {
                courses.remove(course);
            }
        }
    }

    /**
     * A Model stub that always accepts the course being added.
     */
    private static class ModelStubAcceptingCourse extends ModelStub {
        final ArrayList<Course> coursesAdded = new ArrayList<>();

        @Override
        public boolean hasCourse(Course course) {
            requireNonNull(course);
            return coursesAdded.stream().anyMatch(c -> c.equals(course));
        }

        @Override
        public void addCourse(Course course) {
            requireNonNull(course);
            coursesAdded.add(course);
        }
    }
}
