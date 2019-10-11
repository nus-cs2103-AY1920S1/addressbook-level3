package seedu.address.logic.commands.student;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class StudentListCommand extends StudentCommand{
    public static final String MESSAGE_USAGE = COMMAND_WORD + " list: List of students";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;//new CommandResult(model.getStudentList());
    }
}
