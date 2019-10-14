package seedu.address.model.util;

import java.util.ArrayList;

import seedu.address.model.flashcard.FlashCard;

//@@author keiteo
/**
 * Creates a test model to contain relevant flashcards to test users.
 */
public class FlashCardTestModel {

    private ArrayList<FlashCard> testList;
    private FlashCard currentFlashCard;

    public FlashCardTestModel(ArrayList<FlashCard> testList) {
        this.testList = testList;
    }

    public boolean isEmpty() {
        return testList.isEmpty();
    }

    public String getQuestion() {
        assert(!testList.isEmpty());
        currentFlashCard = testList.remove(0);
        return currentFlashCard.getQuestion().toString();
    }

    public String getAnswer() {
        return currentFlashCard.getAnswer().toString();
    }
}
