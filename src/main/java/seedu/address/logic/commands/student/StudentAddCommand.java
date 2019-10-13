package seedu.address.logic.commands.student;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;

/**
 * Represents a command for adding students.
 */
public class StudentAddCommand extends StudentCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new student\n"
            + "Parameters:\n"
            + "name/ [NAME]\n"
            + "Example: name/ Jeong Sock Hwee\n\n";

    private final String name;

    /**
     * Creates a StudentAddCommand object.
     *
     * @param name to set.
     */
    public StudentAddCommand(String name) {
        requireAllNonNull(name);
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Student student;
        student = new Student(new Name(this.name));
        model.addStudent(student);
        return new CommandResult(generateSuccessMessage(student));
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
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentAddCommand)) {
            return false;
        }

        // state check
        StudentAddCommand e = (StudentAddCommand) other;
        return name.equals(e.name);
    }
}
