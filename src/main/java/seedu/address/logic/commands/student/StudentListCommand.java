package seedu.address.logic.commands.student;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a list student command.
 */
public class StudentListCommand extends StudentCommand {

    public static final String MESSAGE_SUCCESS = "This is the list of students: \n";

    /**
     * Executes the student list command.
     *
     * @param model {@code Model} which the command should operate on.
     * @return Command result if the command is executed successfully.
     * @throws CommandException -
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(generateSuccessMessage(model.getStudentSummary()), CommandResultType.SHOW_STUDENT);
    }

    /**
     * Generates a command execution success message.
     *
     * @param message The relevant message from the model.
     * @return The String representation of a success message.
     */
    private String generateSuccessMessage(String message) {
        return MESSAGE_SUCCESS + message;
    }


}
