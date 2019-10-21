package seedu.achievements.logic.commands;

import seedu.achievements.model.StatisticsModel;
import seedu.address.logic.commands.CommandResult;
import seedu.main.logic.commands.Command;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command<StatisticsModel> {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute(StatisticsModel statisticsModel) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
