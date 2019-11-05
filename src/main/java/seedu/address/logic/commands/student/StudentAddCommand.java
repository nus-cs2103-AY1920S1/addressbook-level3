package seedu.address.logic.commands.student;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Student;

/**
 * Adds a student to the student record.
 */
public class StudentAddCommand extends StudentCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new student\n"
            + "Parameters:\n"
            + "name/[STUDENT_NAME]\n"
            + "tag/[SUBJECT_THAT_STUDENT_IS_WEAK_IN]"
            + "Full Example: student name/njoy tag/Chemistry -->"
            + "creates new student called njoy, with weak subject chemistry\n\n";

    public static final String MESSAGE_SUCCESS = "New student added: %1$s";

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
            throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
        }
        model.addStudent(toAdd);
        return new CommandResult(generateSuccessMessage(toAdd), CommandResultType.SHOW_STUDENT);
    }

    /**
     * Generates a command execution success message.
     *
     * @param student that has been added.
     */
    private String generateSuccessMessage(Student student) {
        return String.format(MESSAGE_SUCCESS, student);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StudentAddCommand // instanceof handles nulls
                && toAdd.equals(((StudentAddCommand) other).toAdd));
    }

}
