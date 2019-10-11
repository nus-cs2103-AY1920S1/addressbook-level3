package seedu.address.logic.commands.student;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Student;

/**
 * Represents a delete student command.
 */
public class StudentDeleteCommand extends StudentCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + " [index]: Deletes a student\n"
            + "Note: index has to be greater than 0";

    private final Index index;

    /**
     * Creates a StudentDeleteCommand object.
     *
     * @param index index of student to delete from the list.
     */
    public StudentDeleteCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(generateSuccessMessage(model.deleteStudent(index)));
    }

    /**
     * Generates a command execution success message.
     *
     * @param student that has been added.
     */
    private String generateSuccessMessage(Student student) {
        return "Deleted student: " + student;
    }
}
