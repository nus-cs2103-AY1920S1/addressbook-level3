package dream.fcard.gui.controllers.displays.test;

import java.io.IOException;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.windows.MainWindow;
import dream.fcard.logic.exam.Exam;
import dream.fcard.logic.exam.ExamRunner;
import dream.fcard.logic.respond.ConsumerSchema;
import dream.fcard.logic.respond.Consumers;
import dream.fcard.model.StateEnum;
import dream.fcard.model.StateHolder;
import dream.fcard.model.cards.FlashCard;
import dream.fcard.model.cards.JavascriptCard;
import dream.fcard.model.cards.MultipleChoiceCard;
import dream.fcard.model.exceptions.IndexNotFoundException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * The display for a user to review cards.
 */
public class TestDisplay extends VBox {
    @FXML
    private VBox cardDisplay;
    @FXML
    private Button prevButton;
    @FXML
    private Button endSessionButton;
    @FXML
    private Button nextButton;
    @FXML
    private Label scoreLabel; // for Shawn

    /**
     * The flashcard that is currently on display in test mode.
     */
    private FlashCard cardOnDisplay;

    /**
     * The deck in use for the test.
     */
    private Exam exam;

    /**
     * The index of the card in the deck that is currently on display.
     */
    private int nowShowing;

    /**
     * The user's current score. For Shawn
     */
    private int currentScore = 0;

    /**
     * Consumer for cards to update the score attained for each card by the user. This consumer
     * updates the currentScore in TestDisplay.
     *
     * For Shawn
     */
    private Consumer<Boolean> getScore = score -> {
        if (ExamRunner.isExamOngoing()) {
            updateStatDeckWithScore(score);
            renderCurrentScore();
        }
    };

    private Consumer<Integer> updateMcqUserAttempt = input -> {
        MultipleChoiceCard card = (MultipleChoiceCard) exam.getCurrentCard();
        card.setUserAttempt(input);
    };

    private Consumer<String> updateStringUserAttempt = input -> {
        JavascriptCard card = (JavascriptCard) exam.getCurrentCard();
        card.setAttempt(input);
    };


    @SuppressWarnings("unchecked")
    private Consumer<Boolean> nextCard = onNext -> {
        onShowNext();
    };

    @SuppressWarnings("unchecked")
    private Consumer<FlashCard> changeTestState = currCard -> {
        if (currCard.getClass().getSimpleName().equals("FrontBackCard")) {
            StateHolder.getState().setCurrState(StateEnum.TEST_FBCARD);
        } else if (currCard.getClass().getSimpleName().equals("MultipleChoiceCard")) {
            StateHolder.getState().setCurrState(StateEnum.TEST_MCQ);
        } else if (currCard.getClass().getSimpleName().equals("JavaCard")
                || currCard.getClass().getSimpleName().equals("JavascriptCard")) {
            StateHolder.getState().setCurrState(StateEnum.TEST_JSJAVA);
        } else {
            StateHolder.getState().setCurrState(StateEnum.DEFAULT);
        }
    };

    @SuppressWarnings("unchecked")
    private Consumer<Boolean> seeBack = bool -> {
        seeBack();
    };

    @SuppressWarnings("unchecked")
    private Consumer<Boolean> seeFront = bool -> {
        seeFront();
    };

    @SuppressWarnings("unchecked")
    private Consumer<Boolean> clearCardDisplay = bool -> {
        cardDisplay.getChildren().clear();
    };

    @SuppressWarnings("unchecked")
    private Consumer<Pane> swapCardDisplay = pane -> {
        cardDisplay.getChildren().clear();
        cardDisplay.getChildren().add(pane);
    };

    public TestDisplay(Exam exam) {
        try {
            Consumers.doTask(ConsumerSchema.CLEAR_MESSAGE, true);
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/Displays"
                    + "/TestDisplay.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            //show the first card - fails if no cards are present
            this.exam = exam;
            this.cardOnDisplay = exam.getCurrentCard();
            Consumers.addConsumer("GET_SCORE", getScore);
            Consumers.addConsumer("UPDATE_MCQ_ATTEMPT", updateMcqUserAttempt);
            Consumers.addConsumer("UPDATE_STRING_ATTEMPT", updateStringUserAttempt);
            Consumers.addConsumer("SHOW_NEXT", nextCard);
            Consumers.addConsumer("UPDATE_TEST_STATE", changeTestState);
            Consumers.addConsumer("SEE_FRONT", seeFront);
            Consumers.addConsumer("SEE_BACK", seeBack);
            Consumers.addConsumer("SWAP_CARD_DISPLAY", swapCardDisplay);
            Consumers.addConsumer("CLEAR_CARD_DISPLAY", clearCardDisplay);
            Consumers.doTask("UPDATE_TEST_STATE", cardOnDisplay);
            Consumers.doTask("SWAP_CARD_DISPLAY", exam.getCardDisplayFront());
            prevButton.setOnAction(e -> onShowPrevious());
            endSessionButton.setOnAction(e -> onEndSession());
            nextButton.setOnAction(e -> onShowNext());
        } catch (IOException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    /**
     * A method that renders the front view of all cards.
     * If MCQ card or code cards have already been attempted (i.e. scored), their constructors will repopulate
     * the mcq options / code editors with the user's attempt.
     */
    private void seeFront() {
        Consumers.doTask("CLEAR_CARD_DISPLAY", true);
        Pane currCardFront = exam.getCardDisplayFront();
        Consumers.doTask("SWAP_CARD_DISPLAY", currCardFront);
    }

    /**
     * A method to render the back of the current card on display.
     */
    private void seeBack() {
        Consumers.doTask("CLEAR_CARD_DISPLAY", true);
        Pane currCardBack = exam.getCardDisplayBack();
        Consumers.doTask("SWAP_CARD_DISPLAY", currCardBack);
    }



    /**
     * The handler to render the previous card.
     */
    private void onShowPrevious() {
        exam.downIndex();
        Pane newCard = exam.getCardDisplayFront();
        Consumers.doTask("SWAP_CARD_DISPLAY", newCard);
        Consumers.doTask("UPDATE_TEST_STATE", exam.getCurrentCard());
        Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "Previous Question");
    }

    /**
     * The handler to render the next card.
     */
    private void onShowNext() {
        boolean isEndOfDeck = exam.upIndex();
        if (isEndOfDeck) {
            Consumers.doTask("STOP_TIMELINE", true);
            if (ExamRunner.getCurrentExam() != null) {
                ExamRunner.terminateExam();
            }
        }
        Pane newCard = exam.getCardDisplayFront();
        Consumers.doTask("SWAP_CARD_DISPLAY", newCard);
        Consumers.doTask("UPDATE_TEST_STATE", exam.getCurrentCard());
        Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "Next Question");
    }
    //sample renderer for Shawn
    private void renderCurrentScore() {
        scoreLabel.setText("Current Score: " + exam.getResult());
    }

    /**
     * helper method that updates the exam deck with scores for each card.
     * @param isCorrect boolean on whether the answer is correct.
     */
    private void updateStatDeckWithScore(Boolean isCorrect) {
        try {
            FlashCard currCard = exam.getCurrentCard();
            if (currCard.getCardResult() == -1) {
                exam.gradeQuestion(isCorrect);
                currCard.updateScore(isCorrect);
            }
        } catch (IndexNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Event handler for ending session.
     * Terminates exam if there is an active exam and brings user to deck display.
     */
    private void onEndSession() {
        if (ExamRunner.getCurrentExam() != null) {
            ExamRunner.terminateExam();
        }
        Consumers.doTask(ConsumerSchema.DISPLAY_DECKS, true);
        Consumers.doTask(ConsumerSchema.CLEAR_MESSAGE, true);
        Consumers.doTask("TOGGLE_LIST_VIEW_ON", true);
        StateHolder.getState().setCurrState(StateEnum.DEFAULT);
        Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "You have exited test mode.");

    }
}
