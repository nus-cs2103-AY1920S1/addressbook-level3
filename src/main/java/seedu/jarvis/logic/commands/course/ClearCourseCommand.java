package seedu.jarvis.logic.commands.course;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.course.Course;
import seedu.jarvis.model.viewstatus.ViewType;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.history.commands.exceptions.InvalidCommandToJsonException;

/**
 * Clears all courses in Course Planner.
 */
public class ClearCourseCommand extends Command {
    public static final String COMMAND_WORD = "clear-course";
    public static final boolean HAS_INVERSE = true;

    public static final String MESSAGE_SUCCESS = "Cleared course(s).";

    public static final String MESSAGE_INVERSE_SUCCESS = "Course Planner has been restored!";

    private List<Course> deleted;

    public ClearCourseCommand(List<Course> courses) {
        deleted = new ArrayList<>();
        deleted.addAll(courses);
    }

    public ClearCourseCommand() {
        this(new ArrayList<>());
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    public List<Course> getClearedCourses() {
        return deleted;
    }

    @Override
    public boolean hasInverseExecution() {
        return HAS_INVERSE;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // copy to deleted
        deleted = new ArrayList<>();
        model.getUnfilteredCourseList().forEach(course -> {
            deleted.add(new Course(
                course.getTitle(), course.getFaculty(), course.getDescription(),
                course.getCourseCode(), course.getCourseCredit(), course.getPrereqTree(),
                course.getPreclusion(), course.getFulfillRequirements()
            ));
        });

        model.getCoursePlanner().getUniqueCourseList().setCourses(new ArrayList<>());
        model.setViewStatus(ViewType.LIST_COURSE);
        return new CommandResult(MESSAGE_SUCCESS, true);
    }

    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        // deleted has courses that were originally in course planner
        requireNonNull(model);
        deleted.forEach(model::addCourse);
        model.setViewStatus(ViewType.LIST_COURSE);
        return new CommandResult(MESSAGE_INVERSE_SUCCESS, true);
    }

    @Override
    public JsonAdaptedCommand adaptToJsonAdaptedCommand() throws InvalidCommandToJsonException {
        // TODO check with marc to see if this is correct
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return o == this
                || (o instanceof ClearCourseCommand
                && deleted.equals(((ClearCourseCommand) o).deleted));
    }
}
