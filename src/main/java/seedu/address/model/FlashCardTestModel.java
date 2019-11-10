package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.model.flashcard.FlashCard;
import seedu.address.ui.TestFlashCardPanel;

//@@author keiteo
/**
 * Instantiates a test model to contain relevant flashcards to test users.
 */
public class FlashCardTestModel {

    private FlashCard currentFlashCard;
    private List<FlashCard> testList;
    private TestFlashCardPanel testFlashCardPanel;

    public FlashCardTestModel(List<FlashCard> testList) {
        this.testList = testList;
    }

    /**
     * Checks if the test list is empty.
     */
    public boolean isEmpty() {
        return testList.isEmpty();
    }

    /**
     * Removes a FlashCard from the head of the test list and sets it as the current FlashCard.
     */
    public void setFlashcard() {
        assert !testList.isEmpty();
        currentFlashCard = testList.remove(0);
    }

    //@@author shutingy
    private void setTestFlashCardPanel() {
        requireNonNull(currentFlashCard);
        testFlashCardPanel = new TestFlashCardPanel(currentFlashCard);
    }

    public TestFlashCardPanel getTestFlashCardPanel() {
        requireNonNull(currentFlashCard);
        setTestFlashCardPanel();
        return testFlashCardPanel;
    }

    /**
     * link to the gui to display the answer during test mode.
     */
    public void showAnswer() {
        requireNonNull(testFlashCardPanel);
        testFlashCardPanel.showAnswer();
    }

    //@@author keiteo

    /**
     * Gets the question of the current FlashCard.
     */
    public String getQuestion() {
        requireNonNull(currentFlashCard);
        return currentFlashCard.getQuestion().toString();
    }

    /**
     * Gets the answer of the current FlashCard.
     */
    public String getAnswer() {
        requireNonNull(currentFlashCard);
        return currentFlashCard.getAnswer().toString();
    }

    /**
     * Gets the current FlashCard.
     */
    FlashCard getCurrentFlashCard() {
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
