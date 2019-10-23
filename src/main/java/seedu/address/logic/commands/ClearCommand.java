package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Classroom;
import seedu.address.model.Model;

/**
 * Clears the classroom.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "classroom has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setClassroom(new Classroom());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
