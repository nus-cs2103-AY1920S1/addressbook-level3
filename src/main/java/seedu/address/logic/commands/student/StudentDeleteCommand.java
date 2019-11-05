package seedu.address.logic.commands.student;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Student;

/**
 * Represents a delete student command.
 */
public class StudentDeleteCommand extends StudentCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + " [index]: Deletes a student\n"
            + "Note: index has to be greater than 0\n"
            + "Full Example: student delete 1 --> removes student with index number 1\n\n";

    public static final String MESSAGE_SUCCESS = "Deleted Student: %1$s";

    private final Index targetIndex;

    /**
     * Creates a StudentDeleteCommand object.
     *
     * @param targetIndex index of student to delete from the list.
     */
    public StudentDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Executes the delete student command.
     *
     * @param model {@code Model} which the command should operate on.
     * @return a command result if the command is executed successfully.
     * @throws CommandException if the command is not in the proper format/the index specified is
     *                          outside the range of the student list.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteStudent(studentToDelete);
        return new CommandResult(generateSuccessMessage(studentToDelete), CommandResultType.SHOW_STUDENT);
    }

    /**
     * Generates a command execution success message.
     *
     * @param student that has been added.
     */
    private String generateSuccessMessage(Student student) {
        return String.format(MESSAGE_SUCCESS, student);
    }

}
