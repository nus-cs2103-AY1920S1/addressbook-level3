package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.LinkedList;
import java.util.List;

import seedu.address.model.flashcard.FlashCard;
import seedu.address.ui.TestFlashCardPanel;

//@@author keiteo
/**
 * Creates a test model to contain relevant flashcards to test users.
 */
public class FlashCardTestModel {

    private FlashCard currentFlashCard;

    private List<FlashCard> testList;
    private List<FlashCard> testListOld = new LinkedList<>(); // placeholder for previous function
    private TestFlashCardPanel testFlashCardPanel;

    public FlashCardTestModel(List<FlashCard> testList) {
        this.testList = testList;
    }

    public boolean isEmpty() {
        return testList.isEmpty();
    }

    public void setFlashcard() {
        assert !testList.isEmpty();
        currentFlashCard = testList.remove(0);
        testListOld.add(currentFlashCard);
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
    public String getQuestion() {
        requireNonNull(currentFlashCard);
        return currentFlashCard.getQuestion().toString();
    }

    public String getAnswer() {
        requireNonNull(currentFlashCard);
        return currentFlashCard.getAnswer().toString();
    }

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
