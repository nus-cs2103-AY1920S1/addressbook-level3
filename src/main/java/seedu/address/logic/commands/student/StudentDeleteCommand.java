package seedu.address.logic.commands.student;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class StudentDeleteCommand extends StudentCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + " [index]: Deletes a question\n"
            + "Note: index has to be greater than 0";

    private final Student student;

    /**
     * Creates a StudentDeleteCommand object.
     *
     * @param student student to delete from the list.
     */
    public StudentDeleteCommand(Student student) {
        this.student = student;
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
