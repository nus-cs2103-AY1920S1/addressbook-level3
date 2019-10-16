package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Displays statistics of the user's spending.
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("ST2334 is the best :)");
    }
}
