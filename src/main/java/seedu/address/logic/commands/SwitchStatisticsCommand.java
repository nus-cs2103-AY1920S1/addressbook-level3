package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Switches in between the Statistics Graphics and the Statistics Table in guiltTrip.
 */
public class SwitchStatisticsCommand extends Command {

    public static final String COMMAND_WORD = "switchStats";
    public static final String MESSAGE_SUCCESS = "Switched Stats.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, new ArrayList<Boolean>(List.of(true, true)));
    }
}
