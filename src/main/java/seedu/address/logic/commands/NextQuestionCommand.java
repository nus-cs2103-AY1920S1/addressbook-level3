package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.logic.parser.KeyboardFlashCardsParser;
import seedu.address.model.Model;

//@@author keiteo
/**
 * This class provides a skeletal implementation for commands that fetch the next FlashCard from the test model.
 */
abstract class NextQuestionCommand extends Command {

    public static final String MESSAGE_SUCCESS_END_OF_TEST = "End of test!";

    private static Logger logger = Logger.getLogger("Foo");
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
            logger.log(Level.INFO, "No more flashcards left to test!");
            keyboardFlashCardsParser.endTestMode();
            logger.log(Level.INFO, "Enabling KeyboardFlashCardsParser to accept normal commands");
            model.updatePerformance(model);
            logger.log(Level.INFO, "Updating performance");
            CommandResult result = new CommandResult(MESSAGE_SUCCESS_END_OF_TEST);
            result.setTestMode(false, true);
            return result;
        }

        model.setTestFlashCard();
        keyboardFlashCardsParser.setAwaitingAnswer(true);
        logger.log(Level.INFO, "Getting the next flashcard");
        return new CommandResult(
                messageSuccess,
                model.getTestFlashCardPanel());
    }
}
