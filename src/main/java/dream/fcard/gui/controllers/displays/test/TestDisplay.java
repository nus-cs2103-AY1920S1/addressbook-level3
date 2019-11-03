package dream.fcard.gui.controllers.displays.test;

import java.io.IOException;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.cards.backview.McqCardBack;
import dream.fcard.gui.controllers.cards.backview.SimpleCardBack;
import dream.fcard.gui.controllers.cards.frontview.BasicFrontBackCard;
import dream.fcard.gui.controllers.cards.frontview.JavaFront;
import dream.fcard.gui.controllers.cards.frontview.JsCard;
import dream.fcard.gui.controllers.cards.frontview.McqCard;
import dream.fcard.gui.controllers.windows.MainWindow;
import dream.fcard.logic.exam.Exam;
import dream.fcard.logic.respond.ConsumerSchema;
import dream.fcard.logic.respond.Consumers;
import dream.fcard.model.cards.FlashCard;
import dream.fcard.model.cards.JavaCard;
import dream.fcard.model.cards.JavascriptCard;
import dream.fcard.model.cards.MultipleChoiceCard;
import dream.fcard.model.exceptions.IndexNotFoundException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * The display for a user to review cards.
 */
public class TestDisplay extends AnchorPane {
    @FXML
    private AnchorPane cardDisplay;
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
     * Consumers used to allow the front view of any card (which is a child component of TestDisplay)
     * to trigger TestDisplay to render a back view of the same card.
     */
    private Consumer<Boolean> seeFrontOfCurrentCard = b -> seeFront();
    private Consumer<Boolean> seeBackOfCurrentCard = b -> seeBack();

    /**
     * Consumer for cards to update the score attained for each card by the user. This consumer
     * updates the currentScore in TestDisplay.
     *
     * For Shawn
     */
    private Consumer<Boolean> getScore = score -> {
        updateStatDeckWithScore(score);
        renderCurrentScore();
    };

    private Consumer<Integer> updateMcqUserAttempt = input -> {
        MultipleChoiceCard card = (MultipleChoiceCard) exam.getCurrentCard();
        card.setUserAttempt(input);
    };

    private Consumer<String> updateJsUserAttempt = input -> {
        JavascriptCard card = (JavascriptCard) exam.getCurrentCard();
        card.setAttempt(input);
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
            seeFront();
            prevButton.setOnAction(e -> onShowPrevious());
            endSessionButton.setOnAction(e -> Consumers.doTask(ConsumerSchema.DISPLAY_DECKS, true));
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
        FlashCard card = cardOnDisplay;
        cardDisplay.getChildren().clear();
        String typeOfCard = card.getClass().getSimpleName();
        if (typeOfCard.equals("FrontBackCard")) {
            BasicFrontBackCard frontBackCard = new BasicFrontBackCard(cardOnDisplay, seeBackOfCurrentCard);
            cardDisplay.getChildren().add(frontBackCard);
        } else if (typeOfCard.equals("MultipleChoiceCard")) {
            McqCard mcqCard = new McqCard((MultipleChoiceCard) cardOnDisplay, seeBackOfCurrentCard);
            cardDisplay.getChildren().add(mcqCard);
        } else if (typeOfCard.equals("JavascriptCard")) {
            cardDisplay.getChildren().clear();
            JsCard jsCard = new JsCard((JavascriptCard) cardOnDisplay, updateJsUserAttempt, getScore);
            cardDisplay.getChildren().add(jsCard);
        } else if (typeOfCard.equals("JavaCard")) {
            cardDisplay.getChildren().clear();
            JavaFront javaFront = new JavaFront((JavaCard) cardOnDisplay, updateJsUserAttempt, getScore);
            cardDisplay.getChildren().add(javaFront);
        }

    }

    /**
     * A method to render the back of the current card on display.
     */
    private void seeBack() {
        FlashCard card = cardOnDisplay;
        cardDisplay.getChildren().clear();
        String typeOfCard = card.getClass().getSimpleName();
        if (typeOfCard.equals("FrontBackCard")) {
            String back = cardOnDisplay.getBack();
            SimpleCardBack backOfCard = new SimpleCardBack(back, seeFrontOfCurrentCard, getScore);
            cardDisplay.getChildren().add(backOfCard);
        } else if (typeOfCard.equals("MultipleChoiceCard")) {
            McqCardBack backCard = new McqCardBack((MultipleChoiceCard) cardOnDisplay,
                    seeFrontOfCurrentCard, getScore, updateMcqUserAttempt);
            cardDisplay.getChildren().add(backCard);
        }
    }



    /**
     * The handler to render the previous card.
     */
    private void onShowPrevious() {
        exam.downIndex();
        cardOnDisplay = exam.getCurrentCard();
        seeFront();
    }

    /**
     * The handler to render the next card.
     */
    private void onShowNext() {
        try {
            exam.upIndex();
            cardOnDisplay = exam.getCurrentCard();
            seeFront();
        } catch (IndexOutOfBoundsException e) {
            //code for a result popup
            Consumers.doTask(ConsumerSchema.DISPLAY_MESSAGE, "You've ran out of cards in this test!");
            EndOfTestAlert.display("Results", "Final Score" + exam.getResult());
            Consumers.doTask(ConsumerSchema.DISPLAY_DECKS, true);
            Consumers.doTask(ConsumerSchema.CLEAR_MESSAGE, true);
        }
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
            Boolean successfulScore = exam.parseUserInputAndGrade(isCorrect);
            FlashCard currCard = exam.getCurrentCard();
            currCard.updateScore(successfulScore);
            //checkif this method works for MCQ and JS card
        } catch (IndexNotFoundException e) {
            e.printStackTrace();
        }
    }
}
