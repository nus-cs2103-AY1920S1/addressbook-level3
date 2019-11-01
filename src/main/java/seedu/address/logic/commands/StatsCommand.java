package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

//@@author LeonardTay748
/**
 * Lists Statistics
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";
    public static final String MESSAGE_USAGE = "Displays the number of good, hard & easy FlashCards";

    /**
     * Should return a CommandResult with the statistics
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        return new CommandResult(MESSAGE_USAGE, false, true, false);
    }

}
