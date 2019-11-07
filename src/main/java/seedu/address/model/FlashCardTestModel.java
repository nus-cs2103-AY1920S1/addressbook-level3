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

    public String getQuestion() {
        assert !testList.isEmpty();
        currentFlashCard = testList.remove(0);
        return currentFlashCard.getQuestion().toString();
    }

    public String getAnswer() {
        requireNonNull(currentFlashCard);
        return currentFlashCard.getAnswer().toString();
    }

    public FlashCard getCurrentFlashCard() {
        return currentFlashCard;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof FlashCardTestModel)) {
            return false;
        }

        if (currentFlashCard == null && ((FlashCardTestModel) obj).currentFlashCard == null) {
            return testList.equals(((FlashCardTestModel) obj).testList);
        }

        if (currentFlashCard != null && ((FlashCardTestModel) obj).currentFlashCard != null) {
            return testList.equals(((FlashCardTestModel) obj).testList)
                    && currentFlashCard.equals(((FlashCardTestModel) obj).currentFlashCard);
        }
        return false;
    }
}
