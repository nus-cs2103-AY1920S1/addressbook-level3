package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.model.flashcard.FlashCard;

//@@author keiteo
/**
 * Creates a test model to contain relevant flashcards to test users.
 */
public class FlashCardTestModel {

    private List<FlashCard> testList;
    private FlashCard currentFlashCard;

    public FlashCardTestModel(List<FlashCard> testList) {
        this.testList = testList;
    }

    public boolean isEmpty() {
        return testList.isEmpty();
    }

    public String getQuestion(Model model) {
        String rating;

        assert !testList.isEmpty();
        currentFlashCard = testList.remove(0);

        //@@author LeonardTay748
        rating = currentFlashCard.getRating().toString();

        if (rating.equals("good")) {
            model.editStats(0);
        }
        if (rating.equals("hard")) {
            model.editStats(1);
        }
        if (rating.equals("easy")) {
            model.editStats(2);
        }

        //@@author keiteo
        return currentFlashCard.getQuestion().toString();
    }

    public String getAnswer() {
        requireNonNull(currentFlashCard);
        return currentFlashCard.getAnswer().toString();
    }
}
