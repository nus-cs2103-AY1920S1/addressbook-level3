package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.tarence.model.Model;
import seedu.tarence.model.StudentBook;

/**
 * Clears T.A.rence.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "T.A.rence has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setStudentBook(new StudentBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
