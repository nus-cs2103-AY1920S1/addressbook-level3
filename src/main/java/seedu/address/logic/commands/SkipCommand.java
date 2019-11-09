package seedu.address.logic.commands;

import seedu.address.logic.parser.KeyboardFlashCardsParser;
import seedu.address.model.Model;

/**
 * This creates a SkipCommand object that allows users to skip the current question.
 */
public class SkipCommand extends NextQuestionCommand {

    public static final String COMMAND_WORD = "skip";
    public static final String MESSAGE_SUCCESS = "Successfully skipped question! Here's the next question";
    public static final String ERROR_MESSAGE = "You can only skip after seeing the question!\n"
            + "Next available command: rate, end";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Skips a flashcard and gets the next question.";

    public SkipCommand(KeyboardFlashCardsParser keyboardFlashCardsParser) {
        super(keyboardFlashCardsParser, MESSAGE_SUCCESS);
    }

    @Override
    public CommandResult execute(Model model) {
        return super.execute(model);
    }
}
