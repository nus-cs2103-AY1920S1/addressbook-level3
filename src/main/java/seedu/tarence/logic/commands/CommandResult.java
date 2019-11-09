package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.Assignment;
import seedu.tarence.model.tutorial.Tutorial;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** The application should changeTabs */
    private boolean changeTabs;

    /** The application should display attendance */
    private boolean hasAttendanceDisplay;

    /** The application should display assignment */
    private boolean hasAssignmentDisplay;

    /** The application should display an assignment list */
    private boolean hasAssignmentsToDisplay;

    /** The attendance to be displayed by the application */
    private Tutorial tutorialAttendanceToDisplay;

    /** The type of tab to display **/
    private TabNames tabToDisplay;

    /** The type of assignment format to display **/
    private DisplayFormat assignmentDisplayFormat;

    /** The type of assignment to display **/
    private Assignment assignmentToDisplay;

    /** The type of student's scores to display **/
    private Map<Student, Integer> studentScores;

    /** The assignment list to display **/
    private List<Assignment> assignments;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.hasAttendanceDisplay = false;
        this.changeTabs = false;
        this.hasAssignmentDisplay = false;
        this.hasAssignmentsToDisplay = false;
    }


    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser} and
     * a specified {@code Tutorial} and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, Tutorial tutorialAttendanceToDisplay) {
        this(feedbackToUser);
        requireAllNonNull(feedbackToUser, tutorialAttendanceToDisplay);
        this.tutorialAttendanceToDisplay = tutorialAttendanceToDisplay;
        this.hasAttendanceDisplay = true;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser} and
     * a specified {@code tabToDisplay} and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, TabNames tabToDisplay) {
        this(feedbackToUser);
        requireAllNonNull(feedbackToUser, tabToDisplay);
        this.tabToDisplay = tabToDisplay;
        this.changeTabs = true;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser} and
     * a specified {@code assignment}, {@code studentScores}, {@code displayFormat} and
     * other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, Assignment assignment, Map<Student, Integer> studentScores,
                         DisplayFormat displayFormat) {
        this(feedbackToUser);
        requireAllNonNull(feedbackToUser, assignment, studentScores, displayFormat);
        this.hasAssignmentDisplay = true;
        this.assignmentToDisplay = assignment;
        this.assignmentDisplayFormat = displayFormat;
        this.studentScores = studentScores;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code Assignmnet} list and
     * other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, List<Assignment> assignments) {
        this(feedbackToUser);
        this.hasAssignmentsToDisplay = true;
        this.assignments = assignments;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isShowAttendance() {
        return hasAttendanceDisplay;
    }

    public boolean isChangeTabs() {
        return changeTabs;
    }

    public boolean isAssignmentDisplay() {
        return hasAssignmentDisplay;
    }

    public boolean isAssignmentsDisplay() {
        return this.hasAssignmentsToDisplay;
    }

    public TabNames getTabToDisplay() {
        return this.tabToDisplay;
    }

    public Tutorial getTutorialAttendance() {
        return this.tutorialAttendanceToDisplay;
    }

    public DisplayFormat getAssignmentDisplayFormat() {
        return this.assignmentDisplayFormat;
    }

    public Assignment getAssignmentToDisplay() {
        return this.assignmentToDisplay;
    }

    public Map<Student, Integer> getStudentScores() {
        return this.studentScores;
    }

    public List<Assignment> getAssignmentsToDisplay() {
        return this.assignments;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && hasAttendanceDisplay == otherCommandResult.hasAttendanceDisplay
                && changeTabs == otherCommandResult.changeTabs
                && hasAssignmentDisplay == otherCommandResult.hasAssignmentDisplay
                && hasAssignmentsToDisplay == otherCommandResult.hasAssignmentsToDisplay;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, hasAttendanceDisplay, changeTabs,
                hasAssignmentDisplay, hasAssignmentsToDisplay);
    }

    @Override
    public String toString() {
        return feedbackToUser;
    }

}
