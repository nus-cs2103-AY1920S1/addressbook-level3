package seedu.guilttrip.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.model.GuiltTrip;
import seedu.guilttrip.model.Model;

/**
 * Clears GuiltTrip.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String ONE_LINER_DESC = COMMAND_WORD + ": Clears the guiltTrip.";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC;
    public static final String MESSAGE_SUCCESS = "Guilt Trip has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setGuiltTrip(new GuiltTrip(true));
        model.commitGuiltTrip();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
