package seedu.address.diaryfeature.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.diaryfeature.model.DiaryModel;
import seedu.address.diaryfeature.model.details.Details;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Sets the user details for this diary
 */
public class SetDetailsCommand extends Command<DiaryModel> {
    public static final String COMMAND_WORD = "setDetails";
    private static final String MESSAGE_SUCCESS = "Details have been set! You are now Password Protected. Dont forget your details!";
    private static final String MESSAGE_FAILURE = "Wait a second, there is already a password...";
    private final Details checker;

    /**
     * Creates an SetDetailsCommand to add password protection
     */
    public SetDetailsCommand(Details input) {
        requireNonNull(input);
        checker = input;
    }

    /**
     * Executes the command by checking if a new set of details can be implemented
     *
     * @param model on which the command is executes
     * @return {@code CommandResult} a readable message to show whether the set was a success or not
     */

    @Override
    public CommandResult execute(DiaryModel model) throws CommandException {
        requireNonNull(model);
        if (model.hasDetails()) {
            return new CommandResult(MESSAGE_FAILURE + "\n");
        } else {
            model.setDetails(checker);
            return new CommandResult(MESSAGE_SUCCESS + "\n");
        }
    }
}
