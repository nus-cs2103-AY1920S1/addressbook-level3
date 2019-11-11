package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.KeyboardFlashCardsParser;
import seedu.address.model.Model;

//@@author keiteo
/**
 * Shows the answer to a flashcard during the test.
 */
public class ShowAnswerCommand extends Command {

    public static final String COMMAND_WORD = "ans";
    public static final String ERROR_MESSAGE = "Answer has already been displayed!\n"
            + "Next available command: rate, end";
    private static final String MESSAGE_SHOW_ANSWER_SUCCESS = "Answer displayed! "
            + "Please rate the FlashCard easy/good/hard.";

    private final KeyboardFlashCardsParser keyboardFlashCardsParser;

    public ShowAnswerCommand(KeyboardFlashCardsParser keyboardFlashCardsParser) {
        requireNonNull(keyboardFlashCardsParser);

        this.keyboardFlashCardsParser = keyboardFlashCardsParser;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.showAnswer();
        keyboardFlashCardsParser.setAwaitingAnswer(false);
        return new CommandResult(MESSAGE_SHOW_ANSWER_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowAnswerCommand) // instanceof handles nulls
                && keyboardFlashCardsParser.equals(((ShowAnswerCommand) other).keyboardFlashCardsParser);
    }
}
