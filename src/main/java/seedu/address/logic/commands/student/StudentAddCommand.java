package seedu.address.logic.commands.student;

import seedu.address.logic.commands.AddCommand;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class StudentAddCommand extends StudentCommand{
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new student\n"
            + "Parameters:\n"
            + "student/[STUDENT_NAME]\n"
            + "Example: student student/JusBevo \n\n";

    private final Student toAdd;

    /**
     * Creates a StudentAddCommand object.
     *
     * @param student to set.
     */
    public StudentAddCommand(Student student) {
        requireAllNonNull(student);
        toAdd = student;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasStudent(toAdd)) {
            throw new CommandException("MESSAGE_DUPLICATE_STUDENT(change later)");
        }
        model.addStudent(toAdd);
        return new CommandResult(String.format("success(MESSAGE_SUCCESS, change later)", toAdd));
    }

    /**
     * Generates a command execution success message.
     *
     * @param student that has been added.
     */
    private String generateSuccessMessage(Student student) {
        return "Added student: " + student;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StudentAddCommand // instanceof handles nulls
                && toAdd.equals(((StudentAddCommand) other).toAdd));
    }
}
