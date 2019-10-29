package seedu.address.logic.commands.mark;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Student;

/**
 * Represents an Add Mark Command.
 */
public class AddMarkCommand extends MarkCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks a student that already exists in the student list\n"
            + "Parameters:\n"
            + "index/[STUDENT_INDEX]\n"
            + "Full Example: mark index/1 --> marks student at index 1 to be struggling with academics.\n\n";

    public static final String MESSAGE_SUCCESS = "Student at index %1$d has been marked";
    public static final String MESSAGE_STUDENT_ALREADY_MARKED = "Student at index %1$d has already been marked";

    private final Index toMark;

    /**
     * Creates a AddMarkCommand object.
     *
     * @param toMark index of student to be marked.
     */
    public AddMarkCommand(Index toMark) {
        requireAllNonNull(toMark);
        this.toMark = toMark;
    }

    /**
     * Executes the add mark command.
     *
     * @param model {@code Model} which the command should operate on.
     * @return a command result if the command is executed successfully.
     * @throws CommandException if the student does not exist in the student list/has already been marked.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (toMark.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }
        Student studentToMark = lastShownList.get(toMark.getZeroBased());
        if (studentToMark.getIsMarked()) {
            throw new CommandException(String.format(MESSAGE_STUDENT_ALREADY_MARKED, toMark.getOneBased()));
        }

        Student newStudent = studentToMark.setMarked();
        model.setStudent(studentToMark, newStudent);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);

        return new CommandResult(generateSuccessMessage(MESSAGE_SUCCESS, toMark.getOneBased()));
    }

    /**
     * Generates a command execution success message.
     *
     * @param successMessage Success message
     * @param index          of student to mark
     */
    private String generateSuccessMessage(String successMessage, int index) {
        return String.format(successMessage, index);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddMarkCommand // instanceof handles nulls
                && toMark.equals(((AddMarkCommand) other).toMark));
    }
}
