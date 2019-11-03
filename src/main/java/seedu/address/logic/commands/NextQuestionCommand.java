package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.KeyboardFlashCardsParser;
import seedu.address.model.Model;

/**
 * Represents a NextQuestionCommand to skip questions.
 */
abstract class NextQuestionCommand extends Command {

    private static final String MESSAGE_SUCCESS_END_OF_TEST = "End of test!";

    private final KeyboardFlashCardsParser keyboardFlashCardsParser;
    private final String messageSuccess;

    NextQuestionCommand(KeyboardFlashCardsParser keyboardFlashCardsParser, String messageSuccess) {
        this.keyboardFlashCardsParser = keyboardFlashCardsParser;
        this.messageSuccess = messageSuccess;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (!model.hasTestFlashCard()) {
            keyboardFlashCardsParser.endTestMode();
            model.endFlashCardTest();
            return new CommandResult(MESSAGE_SUCCESS_END_OF_TEST);
        }

        String nextQuestion = model.getTestQuestion();
        keyboardFlashCardsParser.setAwaitingAnswer(true);
        return new CommandResult(String.format(messageSuccess, nextQuestion));
    }
}
