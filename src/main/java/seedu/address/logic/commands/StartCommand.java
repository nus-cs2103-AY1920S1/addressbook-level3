package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.logic.parser.KeyboardFlashCardsParser;
import seedu.address.model.Model;
import seedu.address.model.category.CategoryContainsAnyKeywordsPredicate;
import seedu.address.model.flashcard.FlashCard;

//@@author keiteo
/**
 * Instantiates a StartCommand to allow users to start the flashcard test
 * by going through the cards in the specified tag(s).
 * If no tags are supplied, all FlashCards will be used.
 */
public class StartCommand extends Command {

    public static final String COMMAND_WORD = "start";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Starts the FlashCard test by going through the cards in the specified deck.\n"
            + "Parameters: tag(s). If no argument is supplied, all FlashCards will be used.\n"
            + "Example: " + COMMAND_WORD + " physics";

    public static final String MESSAGE_NO_FLASHCARDS = "No FlashCards to test!";

    private static final String MESSAGE_START_TEST_SUCCESS = "Starting test...";

    private static Logger logger = Logger.getLogger("Foo");

    private final KeyboardFlashCardsParser keyboardFlashCardsParser;

    private final String tagName;

    public StartCommand(KeyboardFlashCardsParser keyboardFlashCardsParser, String tagName) {
        requireNonNull(keyboardFlashCardsParser);
        this.keyboardFlashCardsParser = keyboardFlashCardsParser;
        this.tagName = tagName;
        logger.log(Level.INFO, String.format("StartCommand created with the following tags: %s", tagName));
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<FlashCard> testList = searchTag(model);
        model.initializeTestModel(testList);
        if (!model.hasTestFlashCard()) {
            logger.log(Level.WARNING, String.format("No flashcards found with the following tag(s):\n%s", tagName));
            return new CommandResult(MESSAGE_NO_FLASHCARDS);
        }
        keyboardFlashCardsParser.startTestMode();
        model.setTestFlashCard();
        keyboardFlashCardsParser.setAwaitingAnswer(true);
        logger.log(Level.INFO, "Initialising test mode in ModelManager and KeyboardFlashCardParser");

        CommandResult result = new CommandResult(
                MESSAGE_START_TEST_SUCCESS,
                model.getTestFlashCardPanel());
        result.setTestMode(true, false);
        return result;

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartCommand // instanceof handles nulls
                && tagName.equals(((StartCommand) other).tagName)
                && keyboardFlashCardsParser.equals(((StartCommand) other).keyboardFlashCardsParser)); // state check
    }

    /** Searches the list of flashcard to fetch the relevant tags. */
    private List<FlashCard> searchTag(Model model) {
        assert model != null;
        if (tagName.isEmpty()) {
            return new LinkedList<>(model.getFlashCardList());
        }
        CategoryContainsAnyKeywordsPredicate predicate = getSearchTermPredicate();
        logger.log(Level.INFO, "Getting a list of flashcards to test");
        return new LinkedList<>(model.getFilteredFlashCardListNoCommit(predicate));
    }

    /** Converts tagName to a CategoryContainsAnyKeywordsPredicate for searchTag(). */
    private CategoryContainsAnyKeywordsPredicate getSearchTermPredicate() {
        String[] tagList = tagName.split("\\s+");
        return new CategoryContainsAnyKeywordsPredicate(Arrays.asList(tagList));
    }
}
