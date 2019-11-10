package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.KeyboardFlashCardsParser;
import seedu.address.model.KeyboardFlashCards;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.Rating;
import seedu.address.model.flashcard.exceptions.FlashCardNotFoundException;
import seedu.address.testutil.FlashCardTestListBuilder;

//@@author keiteo
public class RateQuestionCommandTest {

    private KeyboardFlashCardsParser keyboardFlashCardsParser = new KeyboardFlashCardsParser();

    @Test
    public void execute_flashCardDoesNotExist_exceptionThrown() {
        String[] ratings = {"good", "hard", "easy"};
        for (String rate : ratings) {
            Model model = initModel(new ModelManager());
            Command cmd = new RateQuestionCommand(keyboardFlashCardsParser, new Rating(rate));
            assertThrows(FlashCardNotFoundException.class, () -> cmd.execute(model));
        }
    }

    @Test
    public void execute_goodRatingFlashCardExists_success() {
        KeyboardFlashCards keyboardFlashCards = new KeyboardFlashCards();
        FlashCard fc = new FlashCardTestListBuilder().buildOne().get(0);
        keyboardFlashCards.addFlashcard(fc);
        Model model = initModel(new ModelManager(keyboardFlashCards, new UserPrefs()));
        try {
            Command cmd = new RateQuestionCommand(keyboardFlashCardsParser, new Rating("good"));
            cmd.execute(model);
        } catch (Exception e) {
            fail();
        }
    }

    /** Loads the model with a prebuilt list of test FlashCards. */
    private Model initModel(Model model) {
        model.initializeTestModel(new FlashCardTestListBuilder().buildOne());
        model.setTestFlashCard();
        return model;
    }
}
