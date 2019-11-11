package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSROOM;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ASSIGNMENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.classroom.Classroom;


/**
 * Sets a classroom in the notebook to be the current classroom.
 */
public class SetClassroomCommand extends Command {

    public static final String COMMAND_WORD = "setclass";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets a classroom as current classroom"
                                                       + "Parameters: "
                                                       + PREFIX_CLASSROOM + "CLASSROOMNAME ";

    public static final String MESSAGE_SUCCESS = "Classroom set: %1$s";
    public static final String MESSAGE_CLASSROOM_NOT_FOUND = "This classroom does not exists in the notebook";


    private final Classroom toSet;

    public SetClassroomCommand(String classroomName) {
        requireNonNull(classroomName);
        toSet = new Classroom(classroomName);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasClassroom(toSet)) {
            Classroom newCurrentClassroom = model.getClassroom(toSet);
            model.setCurrentClassroom(newCurrentClassroom);
        } else {
            throw new CommandException(MESSAGE_CLASSROOM_NOT_FOUND);
        }
        model.updateFilteredAssignmentList(PREDICATE_SHOW_ALL_ASSIGNMENTS);
        model.displayAssignments();
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        model.displayStudents();
        model.saveState();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toSet));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                       || (other instanceof AddLessonCommand // instanceof handles nulls
                                   && toSet.equals(((SetClassroomCommand) other).toSet));
    }
}
