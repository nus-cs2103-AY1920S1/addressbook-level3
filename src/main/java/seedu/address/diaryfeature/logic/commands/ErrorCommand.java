package seedu.address.diaryfeature.logic.commands;

import seedu.address.diaryfeature.model.DiaryModel;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

/**
 * Processes all errors and exceptions to a readable format
 */
public class ErrorCommand extends Command<DiaryModel> {
    private final Exception exception;

    /**
     * Creates and error command with the specified {@code error}
     *
     * @param error
     */
    public ErrorCommand(Exception error) {
        exception = error;
    }

    /**
     * Executes the error comand by returning a readable error
     *
     * @param model {@code model} which the command should operate on.
     * @return {@code CommandResult}
     */
    @Override
    public CommandResult execute(DiaryModel model) {
        return new CommandResult(exception.toString());
    }

}
