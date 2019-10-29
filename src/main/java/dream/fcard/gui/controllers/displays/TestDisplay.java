package dream.fcard.gui.controllers.displays;

import java.io.IOException;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.cards.BasicFrontBackCard;
import dream.fcard.gui.controllers.cards.JsCard;
import dream.fcard.gui.controllers.cards.McqCard;
import dream.fcard.gui.controllers.cards.McqCardBack;
import dream.fcard.gui.controllers.cards.SimpleCardBack;
import dream.fcard.gui.controllers.windows.MainWindow;
import dream.fcard.logic.respond.ConsumerSchema;
import dream.fcard.model.Deck;
import dream.fcard.model.State;
import dream.fcard.model.cards.FlashCard;
import dream.fcard.model.cards.JavascriptCard;
import dream.fcard.model.cards.MultipleChoiceCard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
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

    private FlashCard cardOnDisplay;
    private Deck deck;
    private Consumer<Integer> seeFrontOfMcqCard = this::seeFrontOfMcqCard;
    private Consumer<Integer> seeBackOfMcqCard = this::seeBackOfMcqCard;
    private Consumer<Boolean> seeFrontOfFrontBackCard = b -> seeFrontOfFrontBackCard();
    private Consumer<Boolean> seeBackofFrontBackCard = b -> seeBackOfFrontBackCard();
    @SuppressWarnings("unchecked")
    private Consumer<Boolean> displayDecks = State.getState().getConsumer(ConsumerSchema.DISPLAY_DECKS);
    @SuppressWarnings("unchecked")
    private Consumer<Boolean> clearMessage = State.getState().getConsumer(ConsumerSchema.CLEAR_MESSAGE);
    private int nowShowing;

    TestDisplay(Deck deck) {
        try {
            clearMessage.accept(true);
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/Displays"
                    + "/TestDisplay.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            this.deck = deck;
            nowShowing = 0;
            this.cardOnDisplay = deck.getCards().get(nowShowing); //show the first card - fails if no cards are present
            seeFront();
            prevButton.setOnAction(e -> onShowPrevious());
            endSessionButton.setOnAction(e -> displayDecks.accept(true));
            nextButton.setOnAction(e -> onShowNext());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A generic seeFront method that renders the default front view of all cards.
     */
    private void seeFront() {
        FlashCard card = cardOnDisplay;
        cardDisplay.getChildren().clear();
        String typeOfCard = card.getClass().getSimpleName();
        if (typeOfCard.equals("FrontBackCard")) {
            seeFrontOfFrontBackCard();
        } else if (typeOfCard.equals("MultipleChoiceCard")) {
            seeFrontOfMcqCard(-1);
        } else if (typeOfCard.equals("JavascriptCard")) {
            cardDisplay.getChildren().clear();
            JsCard jsCard = new JsCard((JavascriptCard) cardOnDisplay);
            cardDisplay.getChildren().add(jsCard);
        }
    }

    private void seeFrontOfFrontBackCard() {
        cardDisplay.getChildren().clear();
        BasicFrontBackCard frontBackCard = new BasicFrontBackCard(cardOnDisplay, seeBackofFrontBackCard);
        cardDisplay.getChildren().add(frontBackCard);
    }

    /**
     * Specifically displays the reverse side of a front back card.
     */
    private void seeBackOfFrontBackCard() {
        cardDisplay.getChildren().clear();
        String back = cardOnDisplay.getBack();
        SimpleCardBack backOfCard = new SimpleCardBack(back, seeFrontOfFrontBackCard);
        cardDisplay.getChildren().add(backOfCard);
    }

    /**
     * Specifically displays the front of the multiple choice card.
     * @param previouslySelectedAnswer remembers what the user chose so that if the user sees the back of the card
     *                                 and returns to the front again, the choice persists.
     */
    private void seeFrontOfMcqCard(int previouslySelectedAnswer) {
        //-1 indicates the user is doing this card the first time
        cardDisplay.getChildren().clear();
        McqCard mcqCard = new McqCard((MultipleChoiceCard) cardOnDisplay, seeBackOfMcqCard, previouslySelectedAnswer);
        cardDisplay.getChildren().add(mcqCard);
    }

    private void seeBackOfMcqCard(int selectedAnswer) { // 1-based
        cardDisplay.getChildren().clear();
        McqCardBack backCard = new McqCardBack((MultipleChoiceCard) cardOnDisplay, selectedAnswer, seeFrontOfMcqCard);
        cardDisplay.getChildren().add(backCard);
    }

    /**
     * The handler to render the previous card.
     */
    private void onShowPrevious() {
        if (nowShowing != 0) {
            nowShowing--;
            cardOnDisplay = deck.getCards().get(nowShowing);
            seeFront(); // will forget MCQ choices
        }
    }

    /**
     * The handler to render the next card.
     */
    private void onShowNext() {
        if (nowShowing != deck.getCards().size() - 1) {
            nowShowing++;
            cardOnDisplay = deck.getCards().get(nowShowing);
            seeFront(); //will forget MCQ choices
        }
    }
}
