package seedu.address.logic.commands.student;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a list student command.
 */
public class StudentListCommand extends StudentCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " list: List of students";
    public static final String MESSAGE_SUCCESS = "Listed all students";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(generateSuccessMessage(model.getStudentSummary()));
    }

    /**
     * Generates a command execution success message.
     * @param message The relevant message from the model.
     * @return The String representation of a success message.
     */
    private String generateSuccessMessage(String message) {
        System.out.println(message);
        return "This is the list of students: " + "\n"
                + message;
    }
}
