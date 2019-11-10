package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.KeyboardFlashCardsParser;
import seedu.address.model.Model;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.Rating;

//@@author keiteo
/**
 * Allows users to rate the flashcard question and processes the next question.
 */
public class RateQuestionCommand extends NextQuestionCommand {

    public static final String COMMAND_WORD = "rate";
    public static final String MESSAGE_SUCCESS = "Rated successfully! Here's the next question";
    public static final String ERROR_MESSAGE = "You can only rate after answering the question!\n"
            + "Next available command: ans, skip, end";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Rates a FlashCard and gets the next question.\n"
            + "Parameter: rate easy/good/hard";

    private final KeyboardFlashCardsParser keyboardFlashCardsParser;
    private final Rating rating;

    public RateQuestionCommand(KeyboardFlashCardsParser keyboardFlashCardsParser, Rating rating) {
        super(keyboardFlashCardsParser, MESSAGE_SUCCESS);

        requireNonNull(rating);

        this.keyboardFlashCardsParser = keyboardFlashCardsParser;
        this.rating = rating;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        updateModelStatistics(model);
        updateFlashCardRating(model);
        return super.execute(model);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RateQuestionCommand // instanceof handles nulls
                && rating.equals(((RateQuestionCommand) other).rating)
                && keyboardFlashCardsParser
                        .equals(((RateQuestionCommand) other).keyboardFlashCardsParser)); // state check
    }

    /** Updates statistics in the model. */
    private void updateModelStatistics(Model model) {
        assert model != null;

        String rating = this.rating.toString();
        assert rating.equals("good") || rating.equals("hard") || rating.equals("easy");

        switch (rating) {
        case "good":
            model.editStats(0);
            break;
        case "hard":
            model.editStats(1);
            break;
        case "easy":
            model.editStats(2);
            break;
        default:
            // should not reach this stage
        }
    }

    /** Updates the rating of a flashcard. */
    private void updateFlashCardRating(Model model) {
        assert model != null;

        FlashCard flashCardToUpdate = model.getCurrentTestFlashCard();
        assert flashCardToUpdate != null;

        model.setFlashCard(flashCardToUpdate, createUpdatedFlashCard(flashCardToUpdate));
    }

    /** Creates a new immutable flashcard with the updated rating. */
    private FlashCard createUpdatedFlashCard(FlashCard flashCardToUpdate) {
        assert flashCardToUpdate != null;

        return new FlashCard(flashCardToUpdate.getQuestion(),
                flashCardToUpdate.getAnswer(),
                rating, flashCardToUpdate.getCategories());
    }
}
