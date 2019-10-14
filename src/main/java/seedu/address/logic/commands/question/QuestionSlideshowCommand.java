package seedu.address.logic.commands.question;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Starts a slideshow based on the questions selected.
 */
public class QuestionSlideshowCommand extends QuestionCommand {

    public static final String MESSAGE_USAGE =
        COMMAND_WORD + " slideshow: Start a questions slideshow";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult("Starting slideshow.", false, true, false);
    }
}
