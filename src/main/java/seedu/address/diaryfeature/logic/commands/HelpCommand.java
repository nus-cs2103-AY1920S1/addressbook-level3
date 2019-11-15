package seedu.address.diaryfeature.logic.commands;

import seedu.address.diaryfeature.model.DiaryModel;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command<DiaryModel> {
    public static final String COMMAND_WORD = "help";
    private static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    /**
     * Executes the command by showing the help window
     *
     * @param diaryModel on which the command is executes
     * @return {@code CommandResult} a readable form of the matched entries
     */
    @Override
    public CommandResult execute(DiaryModel diaryModel) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
