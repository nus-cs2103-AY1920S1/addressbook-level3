package seedu.address.diaryfeature.logic.commands;

import seedu.address.diaryfeature.model.DiaryModel;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Adds a person to the address book.
 */
public class ErrorCommand extends Command<DiaryModel> {


    private final Exception exception;

    public ErrorCommand(Exception error) {
        exception = error;
    }

    @Override
    public CommandResult execute(DiaryModel model) throws CommandException {
        return new CommandResult(exception.toString());
    }

}
