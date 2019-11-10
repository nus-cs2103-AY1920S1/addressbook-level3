package seedu.planner.logic.commands;

import seedu.planner.logic.autocomplete.CommandInformation;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.commands.util.HelpExplanation;
import seedu.planner.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";
    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Plan2Travel as requested ...";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD,
            "Exits from the program.",
            COMMAND_WORD,
            COMMAND_WORD
    );

    public static final CommandInformation COMMAND_INFORMATION = new CommandInformation(COMMAND_WORD);

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
