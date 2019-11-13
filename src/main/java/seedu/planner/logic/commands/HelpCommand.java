package seedu.planner.logic.commands;

import seedu.planner.logic.autocomplete.CommandInformation;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.commands.result.UiFocus;
import seedu.planner.logic.commands.util.HelpExplanation;
import seedu.planner.model.Model;

//@@author 1nefootstep
/**
 * Displays a command summary to the user.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD,
            "Shows program usage instructions.",
            COMMAND_WORD,
            COMMAND_WORD
    );

    public static final CommandInformation COMMAND_INFORMATION = new CommandInformation(COMMAND_WORD);

    public static final String SHOWING_HELP_MESSAGE = "Opened the help tab.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false, new UiFocus[]{UiFocus.HELP});
    }
}
